package peaksoft.springbootproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.springbootproject.entity.Company;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CourseResponse {
    private Long id;
    private String courseName;
    private String durationMonth;
    private Boolean isActive;
    private LocalDate created;
    private Company company;

}
