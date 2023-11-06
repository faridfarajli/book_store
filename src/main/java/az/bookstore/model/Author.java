package az.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private int age;
    @NotNull

    @Enumerated(EnumType.STRING)
    private Role role;
    public Author(String name) {
        this.name = name;
    }

}
