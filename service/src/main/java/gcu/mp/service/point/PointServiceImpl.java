package gcu.mp.service.point;

import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.deliveryPost.domain.DeliveryPostProgress;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.orderPost.domain.OrderPost;
import gcu.mp.domain.orderPost.domain.OrderPostProgress;
import gcu.mp.domain.orderPost.vo.ProgressState;
import gcu.mp.domain.point.domin.PointHistory;
import gcu.mp.domain.point.repository.PointHistoryRepository;
import gcu.mp.domain.point.vo.State;
import gcu.mp.redis.RedisUtil;
import gcu.mp.service.deliveryPost.DeliveryPostService;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.notification.Dto.NotificationEventDto;
import gcu.mp.service.notification.NotificationService;
import gcu.mp.service.orderPost.OrderPostService;
import gcu.mp.service.point.dto.GetPointDto;
import gcu.mp.service.point.dto.PaysuccessPointDto;
import gcu.mp.service.point.dto.PointHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static gcu.mp.domain.deliveryPost.vo.ProgressState.DONE;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PointServiceImpl implements PointService {
    private final MemberService memberService;
    private final OrderPostService orderPostService;
    private final DeliveryPostService deliveryPostService;
    private final PointHistoryRepository pointHistoryRepository;
    private final RedisUtil redisUtil;
    private final NotificationService notificationService;

    @Override
    public GetPointDto getPoint(Long memberId) {
        Member member = memberService.getMember(memberId);
        return GetPointDto.builder()
                .point(member.getPoint()).build();
    }

    @Transactional
    @Override
    public void paySuccess(PaysuccessPointDto paysuccessPointDto) {
        Member member = memberService.getMember(paysuccessPointDto.getMemberId());
        PointHistory pointHistory = PointHistory.builder()
                .point(paysuccessPointDto.getPoint())
                .state(State.A)
                .flag("+")
                .content("포인트 충전")
                .build();
        pointHistory.setMember(member);
        pointHistoryRepository.save(pointHistory);
    }

    @Override
    public List<PointHistoryDto> getPointHistory(Long memberId, int page, int size) {
        memberService.getMember(memberId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByMemberIdAndState(memberId, State.A, pageRequest);
        return pointHistoryList.stream()
                .map(pointHistory -> PointHistoryDto.builder()
                        .content(pointHistory.getContent())
                        .point(pointHistory.getFlag() + pointHistory.getPoint() + "P")
                        .time(pointHistory.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                        .build()).collect(Collectors.toList());
    }

    @Override
    public String getPointTransactionId(Long memberId) {
        String uniqueId = createdCode();
        String key = "point " + uniqueId;
        String value = memberId.toString();
        redisUtil.setDataExpire(key, value, 60 * 10L);
        return uniqueId;
    }

    @Override
    @Transactional
    public void TransactionPoint(Long memberId, String transactionId,Long point) {
        String key = "point " + transactionId;
        if (redisUtil.existData(key)) {
            String value = redisUtil.getData(key);
            Long receivePointMemberId = Long.valueOf(value);
            Member member = memberService.getMember(memberId);
            Member receivePointMember = memberService.getMember(receivePointMemberId);
            member.addPoint(-point);
            receivePointMember.addPoint(point);
            String purpose;
            String title;
            Optional<OrderPostProgress> orderPostProgressOptionalByMember = orderPostService.existProgressingOrderPostProgressByMemberId(memberId);
            Optional<DeliveryPostProgress> deliveryPostProgressByMember = deliveryPostService.existProgressingDeliveryPostProgressByMemberId(memberId);
            if (orderPostProgressOptionalByMember.isPresent()) {
                Optional<OrderPostProgress> orderPostProgressOptionalByReceivePointMember = orderPostService.existProgressingOrderPostProgressByMemberId(receivePointMember.getId());
                if (orderPostProgressOptionalByReceivePointMember.isEmpty()) {
                    throw new BaseException(BaseResponseStatus.NOT_EXIST_PROGRESS);
                }
                if (!orderPostProgressOptionalByReceivePointMember.get().getOrderPost().getId().equals(orderPostProgressOptionalByMember.get().getOrderPost().getId())) {
                    throw new BaseException(BaseResponseStatus.NOT_EQUALS_POST);
                }
                purpose = "order";
                OrderPost orderPost = orderPostService.getOrderPost(orderPostProgressOptionalByReceivePointMember.get().getOrderPost().getId());
                title = orderPost.getTitle();
                Long postOwnerId = orderPost.getMember().getId();
                if (postOwnerId == orderPostProgressOptionalByMember.get().getId()) {
                    List<OrderPostProgress> orderPostProgressList = orderPostService.getOrderPostProgressListByPostId(orderPost.getId());
                    if (orderPostProgressList.size() <= 2) {
                        orderPostProgressOptionalByMember.get().updateProgressState(ProgressState.DONE);
                    }
                } else {
                    orderPostProgressOptionalByMember.get().updateProgressState(ProgressState.DONE);
                }
                if (postOwnerId == orderPostProgressOptionalByReceivePointMember.get().getId()) {
                    List<OrderPostProgress> orderPostProgressList = orderPostService.getOrderPostProgressListByPostId(orderPost.getId());
                    if (orderPostProgressList.isEmpty()) {
                        orderPostProgressOptionalByReceivePointMember.get().updateProgressState(ProgressState.DONE);
                    }
                } else {
                    orderPostProgressOptionalByReceivePointMember.get().updateProgressState(ProgressState.DONE);
                }
            } else if (deliveryPostProgressByMember.isPresent()) {
                Optional<DeliveryPostProgress> deliveryPostProgressByReceivePointMember = deliveryPostService.existProgressingDeliveryPostProgressByMemberId(memberId);
                if (deliveryPostProgressByReceivePointMember.isEmpty()) {
                    throw new BaseException(BaseResponseStatus.NOT_EXIST_PROGRESS);
                }
                if (!deliveryPostProgressByReceivePointMember.get().getDeliveryPost().getId().equals(deliveryPostProgressByMember.get().getDeliveryPost().getId())) {
                    throw new BaseException(BaseResponseStatus.NOT_EQUALS_POST);
                }
                purpose = "delivery";
                DeliveryPost deliveryPost = deliveryPostService.getDeliveryPost(deliveryPostProgressByReceivePointMember.get().getDeliveryPost().getId());
                title = deliveryPost.getTitle();
                Long postOwnerId = deliveryPost.getMember().getId();
                if (postOwnerId == deliveryPostProgressByMember.get().getId()) {
                    List<DeliveryPostProgress> deliveryPostProgressList = deliveryPostService.getDeliveryPostProgressListByPostId(deliveryPost.getId());
                    if (deliveryPostProgressList.size() <= 2) {
                        deliveryPostProgressByMember.get().updateProgressState(DONE);
                    }
                } else {
                    deliveryPostProgressByMember.get().updateProgressState(DONE);
                }
                if (postOwnerId == deliveryPostProgressByReceivePointMember.get().getId()) {
                    List<DeliveryPostProgress> deliveryPostProgressList = deliveryPostService.getDeliveryPostProgressListByPostId(deliveryPost.getId());
                    if (deliveryPostProgressList.isEmpty()) {
                        deliveryPostProgressByReceivePointMember.get().updateProgressState(DONE);
                    }
                } else {
                    deliveryPostProgressByReceivePointMember.get().updateProgressState(DONE);
                }
            } else {
                throw new BaseException(BaseResponseStatus.NOT_EXIST_PROGRESS);
            }
            PointHistory memberPointHistory = PointHistory.builder()
                    .point(point)
                    .state(State.A)
                    .flag("-")
                    .content(title)
                    .build();
            memberPointHistory.setMember(member);
            PointHistory receivePointMemberPointHistory = PointHistory.builder()
                    .point(point)
                    .state(State.A)
                    .flag("+")
                    .content(title)
                    .build();
            receivePointMemberPointHistory.setMember(member);
            pointHistoryRepository.save(memberPointHistory);
            pointHistoryRepository.save(receivePointMemberPointHistory);
            NotificationEventDto notificationEventDto = NotificationEventDto.builder()
                    .memberId(member.getId())
                    .flag(2)
                    .content(title + "에 대한 포인트 " + point + "가 전송되었습니다.")
                    .build();
            notificationService.notificationEvent(notificationEventDto);
            notificationEventDto = NotificationEventDto.builder()
                    .memberId(member.getId())
                    .flag(1)
                    .content(title + "배달이 완료되었습니다. 지금 확인해 보세요!")
                    .build();
            notificationService.notificationEvent(notificationEventDto);
            notificationEventDto = NotificationEventDto.builder()
                    .memberId(receivePointMember.getId())
                    .flag(2)
                    .content(title + "에 대한 포인트 " + point + "를 받았습니다.")
                    .build();
            notificationService.notificationEvent(notificationEventDto);
            notificationEventDto = NotificationEventDto.builder()
                    .memberId(receivePointMember.getId())
                    .flag(1)
                    .content(title + "배달이 완료되었습니다. 지금 확인해 보세요!")
                    .build();
            notificationService.notificationEvent(notificationEventDto);
        }
    }

    private String createdCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
