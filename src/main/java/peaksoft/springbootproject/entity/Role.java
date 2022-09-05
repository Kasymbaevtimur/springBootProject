package peaksoft.springbootproject.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(generator = "role_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_gen", sequenceName = "role_seq", allocationSize = 1)
    private Long id;
    private String roleName;


}
