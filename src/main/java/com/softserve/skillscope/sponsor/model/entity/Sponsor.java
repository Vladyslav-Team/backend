//package com.softserve.skillscope.sponsor.model.entity;
//
//import com.softserve.skillscope.kudos.model.enity.Kudos;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//
//import java.util.List;
//
//@Setter
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Sponsor {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @OneToOne(mappedBy = "sponsor", cascade = CascadeType.ALL)
//    private SponsorInfo sponsorInfo;
//    @NotEmpty
//    @Size(min = 5, max = 254)
//    private String email;
//
//    @NotEmpty
//    @Size(min = 5, max = 64)
//    private String password;
//
//    @NotEmpty
//    @Size(min = 1, max = 64)
//    private String name;
//
//    @NotEmpty
//    @Size(min = 1, max = 64)
//    private String surname;
//
//    //Set null key to kudos in sponsor_id when sponsor is deleted.
//    @OneToMany(mappedBy = "sponsor")
//    private List<Kudos> kudos;
//
//    //Set nulls to kudos table to sponsor_id key before deleting the sponsor
//    @PreRemove
//    private void removeKudos() {
//        if (kudos != null) {
//            kudos.forEach(k -> k.setSponsor(null));
//            kudos.clear();
//        }
//    }
//}
