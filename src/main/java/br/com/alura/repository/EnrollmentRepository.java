package br.com.alura.repository;

import br.com.alura.entity.Course;
import br.com.alura.entity.Enrollment;
import br.com.alura.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserAndCourse(User user, Course course);
}
