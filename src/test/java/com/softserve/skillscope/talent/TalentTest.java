package com.softserve.skillscope.talent;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TalentTest {
    private Talent talent;

    @BeforeEach
    public void createTalent() {
        talent = Talent.builder()
                .email("test@example.com")
                .password("password1234")
                .name("John")
                .surname("Doe")
                .talentInfo(new TalentInfo())
                .proofs(Arrays.asList(new Proof(), new Proof()))
                .build();
    }

    @Test
    void testGettersAndSetters() {
        talent.setId(1L);
        assertNotNull(talent.getId());
        assertNotNull(talent.getTalentInfo());
        assertNotNull(talent.getEmail());
        assertNotNull(talent.getPassword());
        assertNotNull(talent.getName());
        assertNotNull(talent.getSurname());
        assertNotNull(talent.getProofs());
    }

    @Test
    void testNoArgsConstructor() {
        Talent talent = new Talent();
        assertNotNull(talent);
    }

    @Test
    void testAllArgsConstructor() {
        Talent talent = new Talent(2L, new TalentInfo(), "test@example.com",
                "password", "John", "Doe", Arrays.asList(new Proof(), new Proof()));
        assertNotNull(talent);
    }

    @Test
    void testOneToManyRelationship() {
        List<Proof> proofs = talent.getProofs();
        assertNotNull(proofs);
        assertEquals(2, proofs.size());
    }
}

