package az.bookstore.service;

import az.bookstore.dto.AuthorDto;
import az.bookstore.model.Author;
import az.bookstore.model.Role;
import az.bookstore.repositroy.AuthorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Author register(AuthorDto authorDto){
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setAge(authorDto.getAge());
        author.setEmail(authorDto.getEmail());
        author.setPassword(passwordEncoder.encode(authorDto.getPassword()));
        author.setRole(Role.ADMIN);
        return authorRepository.save(author);

    }
    public Author login(AuthorDto authorDto) throws Exception {
        Author author = authorRepository.findByEmail(authorDto.getEmail());
        if (author == null || !passwordEncoder.matches(authorDto.getPassword(), author.getPassword())) {
            throw new Exception("Invalid username or password.");
        }

        return author;
    }

}
