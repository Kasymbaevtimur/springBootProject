package peaksoft.springbootproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "date_of_start")
    private String dateOfStart;
    @Column(name = "date_of_finish")
    private String dateOfFinish;
    @Column(name="is_active")
    private Boolean isActive=true;
    private Boolean delete=false;
    @CreatedDate
    private LocalDate created;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "course_group",
            joinColumns = @JoinColumn(name = "groups_id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id"))
     @JsonIgnore
    List<Course> courses;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "group",cascade = CascadeType.MERGE)
            @JsonIgnore
    List<User> students;

}
