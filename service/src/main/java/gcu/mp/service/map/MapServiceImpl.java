package gcu.mp.service.map;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.orderPost.domain.OrderPost;
import gcu.mp.redis.RedisUtil;
import gcu.mp.service.deliveryPost.DeliveryPostService;
import gcu.mp.service.map.dto.GetMapInformationDto;
import gcu.mp.service.map.dto.MapPointDto;
import gcu.mp.service.map.dto.PostMapPointDto;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.orderPost.OrderPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MapServiceImpl implements MapService {
    private final RedisUtil redisUtil;
    private final OrderPostService orderPostService;
    private final DeliveryPostService deliveryPostService;

    @Override
    @Transactional
    public void postMapPoint(PostMapPointDto postMapPointDto) {
        if (redisUtil.existData("naverMap " + postMapPointDto.getMemberId())) {
            redisUtil.deleteData("naverMap " + postMapPointDto.getMemberId());
        }
        redisUtil.setDataExpire("naverMap " + postMapPointDto.getMemberId(), postMapPointDto.getLatitude() + " " + postMapPointDto.getLongitude(), 60 * 10L);

    }

    @Override
    public GetMapInformationDto getMapInformation(Long memberId) {
        List<MapPointDto> mapPointDtoList = new ArrayList<>();
        String purpose;
        Long postId;
        postId = orderPostService.getOrderPostProgressOrderIdByMemberId(memberId);
        List<Member> memberList = orderPostService.getOrderPostProgressMemberIdByMemberId(memberId);
        purpose = "order";
        if (memberList.isEmpty()) {
            postId = deliveryPostService.getDeliveryPostProgressDeliveryIdByMemberId(memberId);
            memberList = deliveryPostService.getDeliveryPostProgressMemberIdByMemberId(memberId);
            purpose = "delivery";
        }
        if (postId == null) {
            return GetMapInformationDto.builder()
                    .postId(0L)
                    .purpose("진행 중인 거래가 없습니다.")
                    .mapPointDtoList(mapPointDtoList).build();
        }
        for (Member member : memberList) {
            if(Objects.equals(member.getId(), memberId)){
                continue;
            }
            String data = redisUtil.getData("naverMap " + member.getId());
            MapPointDto mapPointDto;
            if (data==null) {
                continue;
            } else {
                String[] splitData = data.split(" ");
                mapPointDto = MapPointDto.builder()
                        .nickname(member.getNickname())
                        .latitude(Double.parseDouble(splitData[0]))
                        .longitude(Double.parseDouble(splitData[1])).build();
            }
            mapPointDtoList.add(mapPointDto);
        }
        return GetMapInformationDto.builder()
                .postId(postId)
                .purpose(purpose)
                .mapPointDtoList(mapPointDtoList).build();
    }
}