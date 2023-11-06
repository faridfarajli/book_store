package az.bookstore.controller;
import az.bookstore.dto.*;
import az.bookstore.model.*;
import az.bookstore.repositroy.BookRepository;
import az.bookstore.repositroy.StudentRepository;
import az.bookstore.service.AuthorService;
import az.bookstore.service.BookService;
import az.bookstore.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static az.bookstore.util.JwtUtil.generateToken;
@Validated
@RequestMapping("/admin")
@RestController
public class AuthorController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    public AuthorController(BookService bookService,
                            AuthorService authorService,
                            EmailService emailService,
                            StudentRepository studentRepository,
                            BookRepository bookRepository) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.emailService = emailService;
        this.studentRepository = studentRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthorDto authorDto) throws Exception {
        if (authorDto != null) {
            Author author = authorService.login(authorDto);
            String jwtToken = generateToken(String.valueOf(author));
            return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register/author")
    public ResponseEntity<String> register(@RequestBody AuthorDto authorDto) {
        Author author = authorService.register(authorDto);
        String jwtToken = generateToken(String.valueOf(author));
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/create/book")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        Author author = new Author();
        bookService.addBook(bookDto);
        Subscription subscription = new Subscription();
        Student student = new Student();
        if (student.getId().equals(subscription.getStudent_id()) ) {
            String to = student.getEmail();
            String subject = author.getName() + "Published New Book !";
            String text = bookDto.getName() + ", has been published!";
            emailService.sendEmail(to, subject, text);
        }
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/delete/book/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "Delete Successfully: Id " + bookId;
    }

    @PostMapping("/readers/student/{id}")
    public List<StudentDtoSecond> findBookOfStudent(@PathVariable Long id) {

        BookStatus bookStatus = new BookStatus();
        List<StudentDtoSecond> studentDtoSeconds = null;
        if (bookStatus.getBook_id().equals(id))  {
            List<Student> students = studentRepository.findAll();
            studentDtoSeconds = new ArrayList<>();
            for (Student student : students) {
                StudentDtoSecond studentDtoSecond = new StudentDtoSecond();
                studentDtoSecond.setId(student.getId());
                studentDtoSecond.setName(student.getName());
                studentDtoSeconds.add(studentDtoSecond);
            }

   }
        return studentDtoSeconds;
    }

    @PostMapping("/readers/book/{id}")
    public List<Book> findStudentOfBook(@PathVariable Long id) {
        BookStatus bookStatus = new BookStatus();
        if (bookStatus.getStudent_id().equals(id)) {
            return bookRepository.findAll();
        } else {
            throw new RuntimeException("Not contain a list of books.");
        }
    }
}







