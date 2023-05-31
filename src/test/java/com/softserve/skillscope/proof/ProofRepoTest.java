//package com.softserve.skillscope.proof;
//
//import com.softserve.skillscope.proof.model.entity.Proof;
//import com.softserve.skillscope.proof.model.response.ProofStatus;
//import com.softserve.skillscope.talent.TalentRepository;
//import com.softserve.skillscope.talent.model.entity.Talent;
//import com.softserve.skillscope.user.model.User;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
//class ProofRepoTest {
//
//    @Autowired
//    ProofRepository proofTestRepository;
//    @Autowired
//    TalentRepository talentRepo;
//    private Proof proof;
//    private Talent talent;
//    private User user;
//
//    @BeforeEach
//    public void setUp() {
//        user = User.builder()
//                .email("talent@talent.com")
//                .password("talent_password")
//                .name("talent")
//                .surname("talent")
//                .build();
//        talent = Talent.builder().
//                image("testImage")
//                .experience("Java - 2 years")
//                .build();
//        user.setTalent(talent);
//        proof = Proof.builder()
//                .talent(talent)
//                .publicationDate(LocalDateTime.now())
//                .title("Test Title")
//                .description("Test Description")
//                .status(ProofStatus.DRAFT)
//                //.proofStatus(ProofStatus.HIDDEN)
//                .build();
//    }
//
//    @Test
//    void createProof() {
//        talentRepo.save(talent);
//        Proof savedProof = proofTestRepository.save(proof);
//
//        assertThat(savedProof).isNotNull();
//        assertThat(savedProof).isEqualTo(proof);
//    }
//
//    @Test
//    void getProofById() {
//        // Arrange
//        talentRepo.save(talent);
//        Proof savedProof = proofTestRepository.save(proof);
//
//        Optional<Proof> foundProof = proofTestRepository.findById(savedProof.getId());
//
//        // Assert
//        assertThat(savedProof).isNotNull();
//        assertThat(foundProof).isPresent();
//        assertThat(foundProof).contains(savedProof);
//    }
//
//    @Test
//    void updateProof() {
//        talentRepo.save(talent);
//        Proof savedProof = proofTestRepository.save(proof);
//
//        savedProof.setTitle("New title");
//        Proof updated = proofTestRepository.save(savedProof);
//
//        assertThat(updated).isNotNull();
//    }
//
//    @Test
//    void deleteProof() {
//        talentRepo.save(talent);
//        Proof savedProof = proofTestRepository.save(proof);
//
//        assertThat(savedProof).isNotNull();
//        assertThat(savedProof).isEqualTo(proof);
//
//        proofTestRepository.delete(savedProof);
//
//        Optional<Proof> foundProofOptional = proofTestRepository.findById(savedProof.getId());
//        assertThat(foundProofOptional).isEmpty();
//    }
//}
