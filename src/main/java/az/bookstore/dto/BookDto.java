package az.bookstore.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String name;
    private String author_name;
    private int studentId;
}
