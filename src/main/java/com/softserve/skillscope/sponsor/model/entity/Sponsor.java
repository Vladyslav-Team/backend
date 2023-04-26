package com.softserve.skillscope.sponsor.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sponsor /*extends User*/{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sponsor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @NotEmpty
    @Size(max = 32)
    private String location;
    @Size(max = 16)
    private String phone;
    @NotEmpty
    @URL
    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;


    //Set null key to kudos in sponsor_id when sponsor is deleted.
    @OneToMany(mappedBy = "sponsor")
    private List<Kudos> kudos;

    //Set nulls to kudos table to sponsor_id key before deleting the sponsor
    @PreRemove
    private void removeKudos() {
        if (kudos != null) {
            kudos.forEach(k -> k.setSponsor(null));
            kudos.clear();
        }
    }
}
