package peaksoft.springbootproject.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String studyFormat;
    private int age;
    private String password;
    private Long groupId;
    private String roleName;
}
