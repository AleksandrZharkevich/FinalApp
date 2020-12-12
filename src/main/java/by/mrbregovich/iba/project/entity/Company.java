package by.mrbregovich.iba.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @Column(name = "image_url", nullable = false)
    private String imgUrl;

    @Column(name = "donate", nullable = false)
    private Integer donate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @JsonManagedReference
    @OneToMany(mappedBy = "company", targetEntity = Request.class)
    private List<Request> requests;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "company_participants",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> participants = new ArrayList<>();

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

    @Transient
    public int daysLeft() {
        return Period.between(LocalDate.now(), endDate).getDays();
    }

    @JsonIgnore
    @Transient
    public boolean isClosed() {
        return this.companyStatus == CompanyStatus.CLOSED;
    }

    @Transient
    public long doneRequests() {
        return this.requests.stream()
                .filter(request -> request.getRequestStatus() == RequestStatus.DONE)
                .count();
    }
}
