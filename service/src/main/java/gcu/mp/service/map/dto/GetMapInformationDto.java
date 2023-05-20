package gcu.mp.service.map.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMapInformationDto {
    Long postId;
    String purpose;
    List<MapPointDto> mapPointDtoList = new ArrayList<>();
}
