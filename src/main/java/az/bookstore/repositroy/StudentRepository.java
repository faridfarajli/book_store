package az.bookstore.repositroy;
import az.bookstore.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
}
