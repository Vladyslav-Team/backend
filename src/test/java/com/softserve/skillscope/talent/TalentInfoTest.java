package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TalentInfoTest {
    private TalentInfo talentInfo;

    @BeforeEach
    public void createTalent() {
        talentInfo = TalentInfo.builder()
                .talent(new Talent())
                .image("image-path/1")
                .experience("no experience")
                .location("Kharkiv")
                .phone("+380999479826")
                .birthday(LocalDate.now())
                .education("no education")
                .about("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sollicitudin.")
                .build();
    }

    @Test
    void testGettersAndSetters() {
        talentInfo.setId(1L);
        assertNotNull(talentInfo.getId());
        assertNotNull(talentInfo.getTalent());
        assertNotNull(talentInfo.getImage());
        assertNotNull(talentInfo.getExperience());
        assertNotNull(talentInfo.getLocation());
        assertNotNull(talentInfo.getPhone());
        assertNotNull(talentInfo.getBirthday());
        assertNotNull(talentInfo.getEducation());
        assertNotNull(talentInfo.getAbout());
    }

    @Test
    void testNoArgsConstructor() {
        TalentInfo info = new TalentInfo();
        assertNotNull(info);
    }
}

