package com.softserve.skillscope.talentInfo.model.entity;

import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty
    private String image;

    @Size(max = 254)
    private String experience;

    @NotEmpty
    @Size(max = 32)
    private String location;

    @Size(max = 16)
    private String phone;

    private LocalDate age;

    @Size(max = 64)
    private String education;

    @Size(max = 1000)
    private String about;
}
