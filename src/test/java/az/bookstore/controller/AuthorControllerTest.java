package az.bookstore.controller;

import az.bookstore.controller.AuthorController;
import az.bookstore.dto.AuthorDto;
import az.bookstore.dto.BookDto;
import az.bookstore.model.Author;
import az.bookstore.model.BookStatus;
import az.bookstore.model.Student;
import az.bookstore.service.AuthorService;
import az.bookstore.service.BookService;
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
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private BookService bookService;

    private AuthorController authorController;

    @BeforeEach
    public void setUp() {
        authorController = new AuthorController(bookService, authorService, null, null, null);
    }

    @Test
    public void testLogin() throws Exception {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("admin");
        authorDto.setPassword("password");
        when(authorService.login(authorDto)).thenReturn(new Author());
        ResponseEntity<Object> response = authorController.login(authorDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String jwtToken = response.getBody().toString();
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith("Bearer "));
    }

    @Test
    public void testRegister() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("admin");
        authorDto.setPassword("password");
        when(authorService.register(authorDto)).thenReturn(new Author());
        ResponseEntity<String> response = authorController.register(authorDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        String jwtToken = response.getBody();
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith("Bearer "));
    }

    @Test
    public void testAddBook() {
        BookDto bookDto = new BookDto();
        bookDto.setName("The Lord of the Rings");
        bookDto.setAuthor_name("J.R.R. Tolkien");
        doNothing().when(bookService).addBook(bookDto);
        ResponseEntity<BookDto> response = authorController.addBook(bookDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BookDto bookDtoResponse = response.getBody();
        assertEquals(bookDto.getName(), bookDtoResponse.getName());
        assertEquals(bookDto.getAuthor_name(), bookDtoResponse.getAuthor_name());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        doNothing().when(bookService).deleteBook(bookId);
        String response = authorController.deleteBook(bookId);
        assertTrue(response.contains("Delete Successfully"));
    }

    @Test
    public void testFindBookOfStudent() {
        Long bookId = 1L;
        BookStatus bookStatus = new BookStatus();
        when(bookStatus.getBook_id()).thenReturn(bookId);
        List<Student> students = new ArrayList<>();

    }
}