package az.bookstore.controller;
import az.bookstore.dto.AuthorFavDto;
import az.bookstore.dto.StudentDto;
import az.bookstore.model.*;
import az.bookstore.repositroy.AuthorRepository;
import az.bookstore.repositroy.BookRepository;
import az.bookstore.repositroy.BookStatusRepository;
import az.bookstore.repositroy.SubscriptionRepository;
import az.bookstore.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static az.bookstore.util.JwtUtil.generateToken;
@Validated
@RequestMapping("/user")
@RestController
public class StudentController {
    private final StudentService studentService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BookStatusRepository bookStatusRepository;

    public StudentController(StudentService studentService,
                             BookRepository bookRepository,
                             AuthorRepository authorRepository,
                             SubscriptionRepository subscriptionRepository,
                             BookStatusRepository bookStatusRepository) {
        this.studentService = studentService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.bookStatusRepository = bookStatusRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody StudentDto studentDto) throws Exception {
        if (studentDto != null) {
            Student student = studentService.login(studentDto);
            String jwtToken = generateToken(String.valueOf(student));
            return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register/student")
    public ResponseEntity<String> register(@RequestBody StudentDto studentDto) {
        Student student = studentService.register(studentDto);
        String jwtToken = generateToken(String.valueOf(student));
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/get/author")
    public List<AuthorFavDto> authors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorFavDto> authorFavDto1= new ArrayList<>();
        for (Author author : authors) {
            AuthorFavDto authorFavDto = new AuthorFavDto();
            authorFavDto.setId(author.getId());
            authorFavDto.setName(author.getName());
            authorFavDto1.add(authorFavDto);
        }
        return authorFavDto1;
    }

    @PostMapping("/favorite/author/{id}")
    public String subscribeAuthor(@PathVariable Long id) {
        Subscription subscription = new Subscription();
        subscription.setSubscription_id(id);
        Student student = new Student();
        subscription.setStudent_id(student.getId());
        subscriptionRepository.save(subscription);
        return "Successfully";

    }

    @GetMapping("/books/read")
    public List<Book> readBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/books/read/{bookId}")
    public ResponseEntity<BookStatus> readBook(@PathVariable Long bookId){
        BookStatus bookStatus = new BookStatus();
        bookStatus.setBook_id(bookId);
        Student student = new Student();
        bookStatus.setStudent_id(student.getId());
        bookStatusRepository.save(bookStatus);
        return ResponseEntity.ok(bookStatus);

    }

}


