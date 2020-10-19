package by.mrbregovich.iba.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "duration", nullable = false)
    private int durationInDays;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyStatus=" + companyStatus +
                ", createdAt=" + createdAt +
                ", owner_id=" + owner.getId() +
                '}';
    }
}
