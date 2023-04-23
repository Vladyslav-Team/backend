package com.softserve.skillscope.talent.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TalentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "talent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Talent talent;

    @NotEmpty
    @URL
    private String image;

    @Size(max = 254)
    private String experience;

    @NotEmpty
    @Size(max = 32)
    private String location;

    @Size(max = 16)
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    @Size(max = 64)
    private String education;

    @Size(max = 1000)
    private String about;
}
