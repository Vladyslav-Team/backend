package com.softserve.skillscope.talentInfo.model.entity;

import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TalentInfo {
    @Id
    @GeneratedValue
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "talent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Talent talent;

    private String image;

    private String experience;

    private String location;

    private String phone;

    private LocalDate age;

    private String education;

    private String aboutMe;
}
