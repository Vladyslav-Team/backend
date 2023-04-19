package com.softserve.skillscope.talentInfo;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talentInfo.TalentInfoRepository;
import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
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
class TestTalentInfo {
    @Autowired
    TalentInfoRepository repository;

    @Test
    void createTalentInfo() {
        TalentInfo talentInfo = TalentInfo.builder()
                .talent(Talent.builder()
                        .email("talent@talent.com")
                        .password("talent_password")
                        .name("talent")
                        .surname("talent").build())
                .image("image-path/1")
                .experience("no experience")
                .location("Kharkiv")
                .phone("+380999479826")
                .birthday(LocalDate.now())
                .education("no education")
                .about("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sollicitudin.")
                .build();

        TalentInfo savedTalentInfo = repository.save(talentInfo);

        assertThat(savedTalentInfo).isNotNull();
        assertThat(savedTalentInfo).isEqualTo(talentInfo);
    }

    @Test
    void getTalentInfoById() {
        TalentInfo talentInfo = TalentInfo.builder()
                .talent(Talent.builder()
                        .email("talent@talent.com")
                        .password("talent_password")
                        .name("talent")
                        .surname("talent").build())
                .image("image-path/1")
                .experience("no experience")
                .location("Kharkiv")
                .phone("+380999479826")
                .birthday(LocalDate.now())
                .education("no education")
                .about("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sollicitudin.")
                .build();

        TalentInfo savedTalentInfo = repository.save(talentInfo);

        assertThat(savedTalentInfo).isNotNull();
        assertThat(savedTalentInfo).isEqualTo(talentInfo);

        TalentInfo foundTalentInfo = repository.findById(savedTalentInfo.getId()).get();

        assertThat(foundTalentInfo).isNotNull();
        assertThat(foundTalentInfo).isEqualTo(savedTalentInfo);
    }

    @Test
    void updateTalentInfo() {
        TalentInfo talentInfo = TalentInfo.builder()
                .talent(Talent.builder()
                        .email("talent@talent.com")
                        .password("talent_password")
                        .name("talent")
                        .surname("talent").build())
                .image("image-path/1")
                .experience("no experience")
                .location("Kharkiv")
                .phone("+380999479826")
                .birthday(LocalDate.now())
                .education("no education")
                .about("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sollicitudin.")
                .build();

        TalentInfo savedTalentInfo = repository.save(talentInfo);

        savedTalentInfo.setPhone("+380979453144");
        TalentInfo updated = repository.save(savedTalentInfo);

        assertThat(updated).isNotNull();
    }

    @Test
    void deleteTalentInfo() {
        TalentInfo talentInfo = TalentInfo.builder()
                .talent(Talent.builder()
                        .email("talent@talent.com")
                        .password("talent_password")
                        .name("talent")
                        .surname("talent").build())
                .image("image-path/1")
                .experience("no experience")
                .location("Kharkiv")
                .phone("+380999479826")
                .birthday(LocalDate.now())
                .education("no education")
                .about("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sollicitudin.")
                .build();

        TalentInfo savedTalentInfo = repository.save(talentInfo);

        assertThat(savedTalentInfo).isNotNull();
        assertThat(savedTalentInfo).isEqualTo(talentInfo);

        repository.delete(savedTalentInfo);

        TalentInfo foundTalentInfo = repository.findById(savedTalentInfo.getId()).orElse(null);

        assertThat(foundTalentInfo).isNull();
    }
}