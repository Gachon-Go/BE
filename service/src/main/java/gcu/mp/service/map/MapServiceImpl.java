package gcu.mp.service.map;

import gcu.mp.redis.RedisUtil;
import gcu.mp.service.map.dto.GetMapPointDto;
import gcu.mp.service.map.dto.PostMapPointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MapServiceImpl implements MapService {
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public void postMapPoint(PostMapPointDto postMapPointDto) {
        if (redisUtil.existData("kakaoMap " + postMapPointDto.getPostId())) {
            String data = redisUtil.getData("kakaoMap " + postMapPointDto.getPostId());
            StringBuilder newData = new StringBuilder();
            redisUtil.deleteData("kakaoMap " + postMapPointDto.getPostId());
            String[] splitData = data.split(",");
            for (String splitDatum : splitData) {
                String[] split2Data = splitDatum.split(" ");
                if (!split2Data[0].equals(String.valueOf(postMapPointDto.getMemberId()))) {
                    newData.append(splitDatum);
                }
            }
            redisUtil.setDataExpire("kakaoMap " + postMapPointDto.getPostId(), newData.toString(), 60 * 10L);

        } else {
            redisUtil.setDataExpire("kakaoMap " + postMapPointDto.getPostId(), postMapPointDto.getMemberId() + " " + postMapPointDto.getLatitude() + " " + postMapPointDto.getLongitude(), 60 * 10L);
        }
    }

    @Override
    public List<GetMapPointDto> getMapPointList(Long postId) {
        String data = redisUtil.getData("kakaoMap " + postId);
        String[] splitData = data.split(",");
        List<GetMapPointDto> getMapPointDtoList = new ArrayList<>();
        for (String splitDatum : splitData) {
            String[] a = splitDatum.split(" ");
            getMapPointDtoList.add(GetMapPointDto.builder()
                    .memberId(Long.parseLong(a[0]))
                    .latitude(Double.parseDouble(a[1]))
                    .longitude(Double.parseDouble(a[2])).build());
        }
        return getMapPointDtoList;
    }
}
