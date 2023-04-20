package com.softserve.skillscope.talent;

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

import java.time.LocalDate;
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
        int pageNum = 3;
        int pageSize = 3;
        Page<Talent> page = talentRepo.findAll(PageRequest.of(pageNum - 1, pageSize));
        List<Talent> talents = page.getContent();
        assertThat(talents.size()).isEqualTo(pageSize);
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

        Proof proof1 = new Proof(1L, talent, LocalDate.now(),
                "Proof 1", "Description of proof 1", ProofStatus.DRAFT);
        proofRepo.save(proof1);

        Proof proof2 = new Proof(2L, talent, LocalDate.now(),
                "Proof 2", "Description of proof 2", ProofStatus.PUBLISHED);
        proofRepo.save(proof2);

        Proof proof3 = new Proof(3L, talent, LocalDate.now(),
                "Proof 3", "Description of proof 3", ProofStatus.HIDDEN);
        proofRepo.save(proof3);

        List<Proof> proofList = proofRepo.findByTalentId(talent.getId());
        assertEquals(3, proofList.size());
        proofList.forEach(proof -> assertEquals(talent, proof.getTalent()));
    }
}

