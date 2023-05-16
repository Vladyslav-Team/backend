package com.softserve.skillscope.proof;

import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.skill.model.Skill;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProofTest {

    private Proof proof;

    @BeforeEach
    public void createProof() {
        proof = Proof.builder()
                .talent(new Talent())
                .publicationDate(LocalDateTime.now())
                .title("Test Title")
                .description("Test Description")
                .status(ProofStatus.DRAFT)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        proof.setId(1L);
        assertNotNull(proof.getId());
        assertNotNull(proof.getTalent());
        assertNotNull(proof.getPublicationDate());
        assertNotNull(proof.getTitle());
        assertNotNull(proof.getDescription());
        assertNotNull(proof.getStatus());
    }

    @Test
    void testNoArgsConstructor() {
        Proof proof1 = new Proof();
        assertNotNull(proof1);
    }

    @Test
    void testAllArgsConstructor() {
        Proof proof1 = new Proof(2L, new Talent(), LocalDateTime.now(),
                "Title", "Description", ProofStatus.DRAFT, List.of(new Kudos()), List.of(new Skill()));
        assertNotNull(proof1);
    }
}
