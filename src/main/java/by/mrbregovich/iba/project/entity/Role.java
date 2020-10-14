package by.mrbregovich.iba.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @JsonBackReference
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles", targetEntity = User.class)
    private List<User> users = new ArrayList<>();

    @Override
    @Transient
    public String getAuthority() {
        return name;
    }
}