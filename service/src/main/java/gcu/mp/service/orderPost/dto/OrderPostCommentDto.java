package gcu.mp.service.orderPost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPostCommentDto {
    Long commentId;
    String commentWriter;
    String content;
    String commentWriterImage;
    Boolean isAccept;
}
