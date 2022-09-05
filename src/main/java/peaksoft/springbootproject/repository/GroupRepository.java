package peaksoft.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.springbootproject.entity.Course;
import peaksoft.springbootproject.entity.Group;


public interface GroupRepository extends JpaRepository<Group, Long> {

//
//    @Query("select c from  Course c join c.groups g where g.id=?1")
//    Course getCourseByGroup(Long courseId);

}
