package az.bookstore.service;

import az.bookstore.model.Author;
import az.bookstore.model.Student;
import az.bookstore.repositroy.AuthorRepository;
import az.bookstore.repositroy.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final AuthorRepository authorRepository;

    public CustomUserService(StudentRepository studentRepository, AuthorRepository authorRepository) {
        this.studentRepository = studentRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Student student = studentRepository.findByEmail(username);
            Author author =authorRepository.findByEmail(username);
        if (student != null) {
            return new org.springframework.security.core.userdetails.User(student.getEmail()
                    , student.getPassword(),
                    student.getRole().getAuthorities());
        } else if (author!=null){
            return new User(author.getEmail(),author.getPassword(),author.getRole().getAuthorities());

        }
        throw new UsernameNotFoundException("Invalid email or password");

    }


}
