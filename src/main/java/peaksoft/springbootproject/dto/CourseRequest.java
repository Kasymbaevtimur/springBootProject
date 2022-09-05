package peaksoft.springbootproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {


    private Long id;
    private String courseName;
    private String durationMonth;
    private Long companyId;

}
