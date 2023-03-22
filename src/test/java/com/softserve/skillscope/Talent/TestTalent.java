package com.softserve.skillscope.Talent;

import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TestTalent {

    @Autowired
    TalentRepository repository;
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

    //For correct result you should disable the pre-population of database
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
        repository.save(savedTalent);
        assertThat(savedTalent).isNotNull();
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
}

