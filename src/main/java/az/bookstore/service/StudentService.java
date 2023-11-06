package az.bookstore.service;


import az.bookstore.dto.StudentDto;
import az.bookstore.model.Role;
import az.bookstore.model.Student;
import az.bookstore.repositroy.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService( StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;

    }
    public Student register(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());
        student.setEmail(studentDto.getEmail());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        student.setRole(Role.USER);
        studentRepository.save(student);

        return student;
    }

    public Student login(StudentDto studentDto) throws Exception {
        Student student = studentRepository.findByEmail(studentDto.getEmail());
        if (student == null || !passwordEncoder.matches(studentDto.getPassword(), student.getPassword())) {
            throw new Exception("Invalid username or password.");
        }

        return student;
    }

}