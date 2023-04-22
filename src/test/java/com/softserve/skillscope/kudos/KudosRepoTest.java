package com.softserve.skillscope.kudos;

import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class KudosRepoTest {
    @Autowired
    private KudosRepository kudosRepo;
    @Autowired
    private ProofRepository proofRepository;
    @Autowired
    private TalentRepository talentRepository;
    private Kudos kudos;
    Talent talent;
    Proof proof;

    @BeforeEach
    void setUp() {
        talent = Talent
                .builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();
        proof = Proof.builder()
                .talent(talent)
                .publicationDate(LocalDateTime.now())
                .title("Test test test")
                .description("tes test ")
                .status(ProofStatus.PUBLISHED)
                .build();
        kudos = Kudos.builder()
                .talent(talent)
                .proof(proof)
                .build();
    }

    @Test
    void createKudos() {
        talentRepository.save(talent);
        proofRepository.save(proof);
        Kudos saveKudos = kudosRepo.save(kudos);

        assertThat(saveKudos).isNotNull();
        assertThat(saveKudos).isEqualTo(kudos);
        assertThat(saveKudos.getId()).isNotNull();
    }

    @Test
    void getKudos() {
        talentRepository.save(talent);
        proofRepository.save(proof);
        Kudos saveKudos = kudosRepo.save(kudos);

        var foundKudos = kudosRepo.findById(saveKudos.getId());

        assertThat(foundKudos).isPresent();
        foundKudos.ifPresent(k -> assertThat(k).isEqualTo(saveKudos));
    }

    @Test
    void updateKudos() {
        talentRepository.save(talent);
        proofRepository.save(proof);
        Talent newTalent = new Talent();
        kudosRepo.save(kudos);

        kudos.setTalent(newTalent);
        kudosRepo.save(kudos);

        Optional<Kudos> result = kudosRepo.findById(kudos.getId());
        assertTrue(result.isPresent());
        Kudos updatedKudos = result.get();
        assertEquals(newTalent, updatedKudos.getTalent());
    }

    @Test
    void deleteKudos() {
        talentRepository.save(talent);
        proofRepository.save(proof);
        Kudos savedKudos = kudosRepo.save(kudos);

        assertThat(kudosRepo.findById(savedKudos.getId())).isPresent();
        assertThat(savedKudos).isNotNull();
        assertThat(savedKudos).isEqualTo(kudos);

        kudosRepo.delete(savedKudos);

        Optional<Kudos> foundKudos = kudosRepo.findById(savedKudos.getId());
        assertThat(foundKudos).isEmpty();
        Kudos result = foundKudos.orElse(null);
        assertThat(result).isNull();
    }

    @Test
    void checkCascade() {
        talentRepository.save(talent);
        proof.setKudos(List.of(kudos));

        Proof save = proofRepository.save(proof);
        Kudos savedKudos = kudosRepo.save(kudos);

        assertThat(kudosRepo.findById(savedKudos.getId())).isPresent();
        assertThat(savedKudos).isNotNull();
        assertThat(savedKudos).isEqualTo(kudos);
        proofRepository.delete(save);

        Optional<Kudos> foundKudos = kudosRepo.findById(savedKudos.getId());
        assertThat(foundKudos).isEmpty();
        Kudos result = foundKudos.orElse(null);
        assertThat(result).isNull();
    }
}
