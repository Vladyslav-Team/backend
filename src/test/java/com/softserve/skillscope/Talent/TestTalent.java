package com.softserve.skillscope.Talent;

import com.softserve.skillscope.kudos.Kudos;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TestTalent {

    @Autowired
    TalentRepository repository;
    @Autowired
    ProofRepository proofTestRepository;


    @Test
    void createTalent() {
        Talent talent = Talent
                .builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();

        Talent savedTalent = repository.save(talent);

        assertThat(savedTalent).isNotNull();
        assertThat(savedTalent).isEqualTo(talent);
    }

    @Test
    void getTalent() {
        Talent talent = Talent
                .builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();

        Talent savedTalent = repository.save(talent);

        Talent foundTalent = repository.findById(savedTalent.getId()).get();

        assertThat(foundTalent).isNotNull();
        assertThat(foundTalent).isEqualTo(savedTalent);
    }

    @Test
    void updateTalent() {
        Talent talent = Talent
                .builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();
        Talent savedTalent = repository.save(talent);
        savedTalent.setEmail("thenewtalent@talent.com");
        Talent updatedTalent = repository.save(savedTalent);
        assertThat(updatedTalent).isNotNull();
    }

    @Test
    void deleteTalent() {
        Talent talent = Talent
                .builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();

        Talent savedTalent = repository.save(talent);

        assertThat(savedTalent).isNotNull();
        assertThat(savedTalent).isEqualTo(talent);

        repository.delete(savedTalent);

        Talent foundTalent = repository.findById(savedTalent.getId()).orElse(null);

        assertThat(foundTalent).isNull();
    }

    @Test
    void getTalentsByPage() {
        int pageNum = 3;
        int pageSize = 3;
        Page<Talent> page = repository.findAll(PageRequest.of(pageNum - 1, pageSize));
        List<Talent> talents = page.getContent();
        assertThat(talents.size()).isEqualTo(pageSize);
    }

    @Test
    void getTalentProofs(){
        Talent talent = Talent
                .builder()
                .email("talent@talent.com")
                .password("talent_password")
                .name("talent")
                .surname("talent")
                .build();
        //List<Talent> talentList = repository.findAll();
        List<Kudos> kudos = null;

        Talent savedTalent = repository.save(talent);

        Proof proof1 = new Proof(1L, talent, LocalDate.now(), "Proof 1", "Description of proof 1", ProofStatus.DRAFT, kudos);
        proofTestRepository.save(proof1);

        Proof proof2 = new Proof(2L, talent, LocalDate.now(), "Proof 2", "Description of proof 2", ProofStatus.PUBLISHED, kudos);
        proofTestRepository.save(proof2);

        Proof proof3 = new Proof(3L, talent, LocalDate.now(), "Proof 3", "Description of proof 3", ProofStatus.HIDDEN, kudos);
        proofTestRepository.save(proof3);

        List<Proof> proofList = proofTestRepository.findByTalentId(talent.getId());
        assertEquals(3, proofList.size());
        proofList.forEach(proof -> assertEquals(talent, proof.getTalent()));
    }
}

