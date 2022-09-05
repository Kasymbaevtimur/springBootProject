package peaksoft.springbootproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TeacherRequest{
    private Long id;
    private String firstName;
    private String email;
    private String lastName;
    private String password;
    private String roleName;
    private Long courseId;

}
