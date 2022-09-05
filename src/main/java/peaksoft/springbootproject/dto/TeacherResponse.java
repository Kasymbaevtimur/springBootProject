package peaksoft.springbootproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.springbootproject.entity.Course;
import peaksoft.springbootproject.entity.Role;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class TeacherResponse{
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate created;
    private List<Role> role;
    private  Course course;

}
