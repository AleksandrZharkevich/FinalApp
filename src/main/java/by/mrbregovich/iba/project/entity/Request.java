package by.mrbregovich.iba.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "placed_at", nullable = false)
    private LocalDate placedAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @NonNull
    @EqualsAndHashCode.Include
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @JsonBackReference
    @NonNull
    @ManyToOne
    @JoinTable(name = "user_requests",
            joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private User manager;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", placedAt=" + placedAt +
                ", requestStatus=" + requestStatus +
                ", contact_id=" + contact.getId() +
                ", manager_id=" + (manager != null ? manager.getId() : "null") +
                '}';
    }
}
