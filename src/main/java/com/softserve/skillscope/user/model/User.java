package com.softserve.skillscope.user.model;

import com.softserve.skillscope.sponsor.model.entity.SponsorInfo;
import com.softserve.skillscope.talent.model.entity.TalentInfo;
import com.softserve.skillscope.user.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TalentInfo talentInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private SponsorInfo sponsorInfo;
    @NotEmpty
    @Size(min = 5, max = 254)
    private String email;

    @NotEmpty
    @Size(min = 5, max = 64)
    private String password;

    @NotEmpty
    @Size(min = 1, max = 64)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 64)
    private String surname;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
