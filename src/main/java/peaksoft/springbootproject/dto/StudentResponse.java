package peaksoft.springbootproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.springbootproject.entity.Group;
import peaksoft.springbootproject.entity.Role;
import peaksoft.springbootproject.entity.StudyFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class StudentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private StudyFormat studyFormat;
    private Boolean isActive;
    private LocalDate created;
    private List<Role> role;
    private Group groups;


}
