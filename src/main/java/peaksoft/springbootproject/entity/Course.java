package peaksoft.springbootproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @SequenceGenerator(
            name = "company_sequence",
            sequenceName = "company_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_sequence"
    )
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "duration_month")
    private String durationMonth;
    @Column(name = "is_active")
    private Boolean isActive = true;
    private Boolean delete = false;
    @CreatedDate
    private LocalDate created;


    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "companies_id")
    @JsonIgnore
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "course_group",
            joinColumns = @JoinColumn(name = "courses_id"),
            inverseJoinColumns = @JoinColumn(name = "groups_id"))
    @JsonIgnore
    private List<Group> groups;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "course")
    @JsonIgnore
    private User teacher;


}