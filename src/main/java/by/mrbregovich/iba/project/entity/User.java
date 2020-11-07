package by.mrbregovich.iba.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "login", nullable = false, unique = true, length = 100)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @JsonManagedReference
    @OneToMany(mappedBy = "manager", targetEntity = Request.class)
    private List<Request> requests;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", targetEntity = Company.class)
    private List<Company> companies;

    @JsonBackReference

    @ManyToMany(mappedBy = "participants", targetEntity = Company.class)
    private List<Company> joinedCompanies;

    public User() {
        this.roles = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.joinedCompanies = new ArrayList<>();
    }

    public User(String login, String password, String email) {
        this();
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(String login, String password, String email, Contact contact) {
        this();
        this.login = login;
        this.password = password;
        this.email = email;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", contact_id=" + contact.getId() +
                ", createdAt=" + createdAt +
                '}';
    }

    public void addRequest(Request request) {
        this.requests.add(request);
        request.setManager(this);
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    @JsonIgnore
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Transient
    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return userStatus != UserStatus.DELETED && userStatus != UserStatus.NOT_ACTIVE;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isEnabled() {
        return userStatus != UserStatus.DELETED && userStatus != UserStatus.NOT_ACTIVE;
    }

    @JsonIgnore
    @Transient
    public List<Company> getActiveJoinedCompanies() {
        return this.joinedCompanies.stream()
                .filter(company -> company.getCompanyStatus() == CompanyStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
