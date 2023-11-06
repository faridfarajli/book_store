package az.bookstore.controller;

import az.bookstore.controller.StudentController;
import az.bookstore.dto.AuthorFavDto;
import az.bookstore.dto.StudentDto;
import az.bookstore.model.*;
import az.bookstore.repositroy.AuthorRepository;
import az.bookstore.repositroy.BookRepository;
import az.bookstore.repositroy.BookStatusRepository;
import az.bookstore.repositroy.SubscriptionRepository;
import az.bookstore.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentControllerTest {

    @Mock
    private StudentService studentService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private BookStatusRepository bookStatusRepository;
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        studentController = new StudentController(studentService,
                bookRepository,
                authorRepository,
                subscriptionRepository,
                bookStatusRepository);
    }

    @Test
    public void testLogin() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("student");
        studentDto.setPassword("password");
        when(studentService.login(studentDto)).thenReturn(new Student());
        ResponseEntity<Object> response = studentController.login(studentDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String jwtToken = response.getBody().toString();
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith("Bearer "));
    }

    @Test
    public void testRegister() {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("student");
        studentDto.setPassword("password");
        when(studentService.register(studentDto)).thenReturn(new Student());
        ResponseEntity<String> response = studentController.register(studentDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        String jwtToken = response.getBody();
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith("Bearer "));
    }

    @Test
    public void testGetAuthors() {
        List<Author> authors = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorFavDto> authorFavDtoList = studentController.authors();
        assertEquals(authors.size(), authorFavDtoList.size());
    }

    @Test
    public void testSubscribeAuthor() {
        Long authorId = 1L;

        doNothing().when(subscriptionRepository).save(any(Subscription.class));
        String response = studentController.subscribeAuthor(authorId);
        assertTrue(response.contains("Successfully"));
    }

    @Test
    public void testGetReadBooks() {
        List<Book> books = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> books1;
    }
}
