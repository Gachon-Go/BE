package gcu.mp.service.deliveryPost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPostCommentDto {
    Long commentId;
    String commentWriter;
    String content;
    String commentWriterImage;
}
