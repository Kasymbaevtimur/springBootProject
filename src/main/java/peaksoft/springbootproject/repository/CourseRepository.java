package peaksoft.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.springbootproject.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}