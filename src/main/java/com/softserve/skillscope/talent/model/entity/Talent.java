//package com.softserve.skillscope.talent.model.entity;
//
//import com.softserve.skillscope.proof.model.entity.Proof;
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
//public class Talent {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @OneToOne(mappedBy = "talent", cascade = CascadeType.ALL)
//    private TalentInfo talentInfo;
//
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
//    //Delete all proofs that are connected with this talent.
//    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL)
//    private List<Proof> proofs;
//
//    //TODO @SEM remove the code
////    //Set null key to kudos in talent_id when talent is deleted.
////    @OneToMany(mappedBy = "talent")
////    private List<Kudos> kudos;
////
////    //Set nulls to kudos table to talent_id key before deleting the talent
////    @PreRemove
////    private void removeKudos() {
////        if (kudos != null) {
////            kudos.forEach(k -> k.setTalent(null));
////            kudos.clear();
////        }
////    }
//}
