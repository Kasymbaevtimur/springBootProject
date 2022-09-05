package peaksoft.springbootproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupRequest {
    private Long id;
    private String groupName;
    private String dateOfStart;
    private String dateOfFinish;
    private Long courseId;
}
