package com.softserve.skillscope.talent.entity;

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
                .password("password")
                .name("John")
                .surname("Doe")
                .proofs(Arrays.asList(new Proof(), new Proof()))
                .build();
    }

    @Test
    void testGettersAndSetters() {
        talent.setId(1L);
        assertNotNull(talent.getId());
        //FIXME TalentInfoIs null
        assertNotNull(talent.getTalentInfo());
        assertNotNull(talent.getEmail());
        assertNotNull(talent.getPassword());
        assertNotNull(talent.getName());
        assertNotNull(talent.getSurname());
        assertNotNull(talent.getProofs());
    }

    @Test
    void testToString() {
        assertNotNull(talent.toString());
    }

    @Test
    void testNoArgsConstructor() {
        Talent talent = new Talent();
        assertNotNull(talent);
    }

    @Test
    void testAllArgsConstructor() {
        Talent talent = new Talent(1L, new TalentInfo(), "test@example.com",
                "password", "John", "Doe", Arrays.asList(new Proof(), new Proof()));
        assertNotNull(talent);
    }

    //FIXME Wrong test logic
    @Test
    void testConstraints() {
        talent.setEmail("testEmail@gmail.com");
        talent.setPassword("a");
        talent.setName("");
        talent.setSurname("");
        assertEquals("Size must be between 5 and 254", talent.getEmail());
        assertEquals("Size must be between 5 and 64", talent.getPassword());
        assertEquals("Size must be between 1 and 64", talent.getName());
        assertEquals("Size must be between 1 and 64", talent.getSurname());
    }

    @Test
    void testOneToManyRelationship() {
        List<Proof> proofs = talent.getProofs();
        assertNotNull(proofs);
        assertEquals(2, proofs.size());
    }
}

