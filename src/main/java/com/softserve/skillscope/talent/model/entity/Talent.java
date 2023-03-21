package com.softserve.skillscope.talent.model.entity;

import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Talent {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    @OneToOne(mappedBy = "talent", cascade = CascadeType.ALL)
    private TalentInfo talentInfo;
}
