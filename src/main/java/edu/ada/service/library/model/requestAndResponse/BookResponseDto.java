package edu.ada.service.library.model.requestAndResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {
    private Long id;
    private String name;
    private String author;
    private String pickerEmail;
    private LocalDate publishDate;
    private CategoryDto category;
    private boolean isAvailable;
    private List<edu.ada.service.library.model.requestAndResponse.CommentResponseDto> comments;
}
