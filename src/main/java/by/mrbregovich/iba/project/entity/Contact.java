package by.mrbregovich.iba.project.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstname;

    @NonNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastname;

    @NonNull
    @Column(name = "region", nullable = false, length = 50)
    private String region;

    @NonNull
    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @NonNull
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @NonNull
    @Column(name = "street_address", nullable = false, length = 200)
    private String streetAddress;

    @NonNull
    @Column(name = "phone", nullable = false, length = 20)
    private String phoneNumber;
}