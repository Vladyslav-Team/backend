package com.softserve.skillscope.talent;

import com.softserve.skillscope.kudos.KudosRepository;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TalentRepoTest {

    @Autowired
    private TalentRepository talentRepo;
    @Autowired
    private ProofRepository proofRepo;
    @Autowired
    private KudosRepository kudosRepository;
    private Talent talent;

    @BeforeEach
    public void setUp() {
        talent = Talent.builder()
                .email("test@example.com")
                .password("password")
                .name("John")
                .surname("Doe")
                .build();
    }

    @Test
    void createTalent() {
        Talent savedTalent = talentRepo.save(talent);

        assertThat(savedTalent).isNotNull();
        assertThat(savedTalent).isEqualTo(talent);
    }

    @Test
    void getTalent() {
        Talent savedTalent = talentRepo.save(talent);

        Talent foundTalent = talentRepo.findById(savedTalent.getId()).get();

        assertThat(foundTalent).isNotNull();
        assertThat(foundTalent).isEqualTo(savedTalent);
    }

    @Test
    void updateTalent() {
        talentRepo.save(talent);

        talent.setEmail("newemail@example.com");
        talent.setName("Jane");
        talent.setSurname("Doe");
        talentRepo.save(talent);

        Optional<Talent> result = talentRepo.findById(talent.getId());
        assertTrue(result.isPresent());
        Talent updatedTalent = result.get();
        assertEquals("newemail@example.com", updatedTalent.getEmail());
        assertEquals("Jane", updatedTalent.getName());
        assertEquals("Doe", updatedTalent.getSurname());
    }

    @Test
    void deleteTalent() {
        Talent savedTalent = talentRepo.save(talent);

        assertThat(savedTalent).isNotNull();
        assertThat(savedTalent).isEqualTo(talent);

        talentRepo.delete(savedTalent);

        Talent foundTalent = talentRepo.findById(savedTalent.getId()).orElse(null);

        assertThat(foundTalent).isNull();
    }

    @Test
    void getTalentsByPage() {
        int pageSize = 3;
        talentRepo.save(talent);
        Page<Talent> talents = talentRepo.findAllByOrderByIdDesc(PageRequest.of(0, pageSize));
        assertNotNull(talents);
        assertEquals(talent.getEmail(), talents.getContent().get(0).getEmail());
    }

    @Test
    void getTalentProofs() {
        Talent talent = Talent
                .builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();

        talentRepo.save(talent);
        Proof proof1 = Proof.builder()
                .talent(talent)
                .publicationDate(LocalDateTime.now())
                .title("Proof 1")
                .description("Desc 1")
                .status(ProofStatus.DRAFT)
                .build();
        proofRepo.save(proof1);

        Proof proof2 = Proof.builder()
                .talent(talent)
                .publicationDate(LocalDateTime.now())
                .title("Proof 2")
                .description("Desc 2")
                .status(ProofStatus.DRAFT)
                .build();

        proofRepo.save(proof2);

        Kudos kudos1 = Kudos.builder()
                .talent(talent)
                .proof(proof1)
                .build();
        Kudos kudos2 = Kudos.builder()
                .talent(talent)
                .proof(proof2)
                .build();
        kudosRepository.saveAll(Arrays.asList(kudos1, kudos2));

        talent.setProofs(Arrays.asList(proof1, proof2));

        List<Proof> proofList = proofRepo.findByTalentId(talent.getId());
        assertEquals(2, proofList.size());
        proofList.forEach(proof -> assertEquals(talent, proof.getTalent()));
    }
}

