package br.com.alura.repository;

import br.com.alura.entity.Course;
import br.com.alura.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    Page<Course> findByStatus(CourseStatus status, Pageable pageable);

    boolean existsByCode(String code);

    Optional<Course> findByCode(String code);

    @Query(value = "SELECT e.course FROM Enrollment e GROUP BY e.course HAVING COUNT(e.course) > 4", nativeQuery = true)
    List<Course> findCoursesWithEnoughEnrollments();
}
