package az.bookstore.dto;
import lombok.Data;
@Data
public class AuthorDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private int age;


}