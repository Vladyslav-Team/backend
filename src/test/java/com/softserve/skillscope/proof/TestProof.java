package com.softserve.skillscope.proof;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TestProof {

    @Autowired
    ProofRepository proofTestRepository;
    @Autowired
    TalentRepository talentTestRepository;

    @Test
    void createProof(){
        Talent talent = Talent.builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();
        Proof proof = Proof.builder()
                .talent(talent)
                .publicationDate(LocalDate.now())
                .title("Test Title")
                .description("Test Description")
                .status(ProofStatus.DRAFT)
                //.proofStatus(ProofStatus.HIDDEN)
                .build();

        Talent savedTalent = talentTestRepository.save(talent);
        Proof savedProof = proofTestRepository.save(proof);

        assertThat(savedProof).isNotNull();
        assertThat(savedProof).isEqualTo(proof);
    }

    @Test
    void getProofById() {
        Talent talent = Talent.builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();
        Proof proof = Proof.builder()
                .talent(talent)
                .publicationDate(LocalDate.now())
                .title("Test Title")
                .description("Test Description")
                .status(ProofStatus.DRAFT)
                .build();

        Talent savedTalent = talentTestRepository.save(talent);
        Proof savedProof = proofTestRepository.save(proof);

        assertThat(savedProof).isNotNull();
        assertThat(savedProof).isEqualTo(proof);

        Proof foundProof = proofTestRepository.findById(savedProof.getId()).get();

        assertThat(savedProof).isNotNull();
        assertThat(foundProof).isEqualTo(savedProof);
    }

    @Test
    void updateProof() {
        Talent talent = Talent.builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();
        Proof proof = Proof.builder()
                .talent(talent)
                .publicationDate(LocalDate.now())
                .title("Test Title")
                .description("Test Description")
                .status(ProofStatus.DRAFT)
                .build();

        Talent savedTalent = talentTestRepository.save(talent);
        Proof savedProof = proofTestRepository.save(proof);

        savedProof.setTitle("New title");
        Proof updated = proofTestRepository.save(savedProof);

        assertThat(updated).isNotNull();
    }

    @Test
    void deleteProof() {
        Talent talent = Talent.builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();
        Proof proof = Proof.builder()
                .talent(talent)
                .publicationDate(LocalDate.now())
                .title("Test Title")
                .description("Test Description")
                .status(ProofStatus.DRAFT)
                .build();

        Talent savedTalent = talentTestRepository.save(talent);
        Proof savedProof = proofTestRepository.save(proof);

        assertThat(savedProof).isNotNull();
        assertThat(savedProof).isEqualTo(proof);

        proofTestRepository.delete(savedProof);

        Proof foundProof = proofTestRepository.findById(savedProof.getId()).orElse(null);

        assertThat(foundProof).isNull();
    }
}
