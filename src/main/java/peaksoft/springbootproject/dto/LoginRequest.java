package peaksoft.springbootproject.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private Long id;
    private String email;
    private String password;

}
