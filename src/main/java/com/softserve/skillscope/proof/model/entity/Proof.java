package com.softserve.skillscope.proof.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Proof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "talent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Talent talent;

    @Column(name = "publication_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate publicationDate;

    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private ProofStatus status;


    public void setProofStatus(ProofStatus status) {
        if (status != ProofStatus.DRAFT && status != ProofStatus.HIDDEN && status != ProofStatus.PUBLISHED) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }


}