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
@Table(name = "requests")
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
    @JoinColumn(name = "request_manager", referencedColumnName = "id")
    private User manager;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", placedAt=" + placedAt +
                ", requestStatus=" + requestStatus +
                ", contact_id=" + contact.getId() +
                ", company_id=" + company.getId() +
                ", manager_id=" + (manager != null ? manager.getId() : "null") +
                '}';
    }
}
