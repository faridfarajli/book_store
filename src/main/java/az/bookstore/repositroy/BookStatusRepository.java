package az.bookstore.repositroy;

import az.bookstore.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {
}