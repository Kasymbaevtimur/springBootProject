package peaksoft.springbootproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.springbootproject.entity.Course;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class GroupResponse {
    private Long id;
    private String groupName;
    private String dateOfStart;
    private String dateOfFinish;
    private Boolean isActive ;
    private LocalDate created;
    private List<Course> courses;

}
