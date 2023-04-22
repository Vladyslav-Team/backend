/*
package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.service.TalentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TalentServiceTest {
    @Autowired
    private TalentService talentService;
    @Autowired
    private TalentRepository talentRepo;

    private List<Talent> talents;

    @BeforeEach
    void setUp() {
        talents = new ArrayList<>();

        talents.add(Talent.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .name("John")
                .surname("Doe")
                .build());
        talents.add(Talent.builder()
                .id(2L)
                .email("Dima@example.com")
                .password("password2")
                .name("Dima")
                .surname("Prydk")
                .build());
        talents.add(Talent.builder()
                .id(3L)
                .email("Vika@example.com")
                .password("password44")
                .name("Vika")
                .surname("Vol")
                .build());
    }

    @Test
    void testGetAllTalentsByPage() {
        talentRepo.saveAll(talents);
        int page = 1;

        GeneralTalentResponse response = talentService.getAllTalentsByPage(page);

        assertEquals(27, response.totalItems());
        assertEquals(4, response.totalPage());
        assertEquals(page, response.currentPage());
        assertEquals(8, response.talents().size());
    }

//    @Test
//    void testDelete() {
//        // Arrange
//        Long talentId = 3L;
//        Talent talent = new Talent();
//        talent.setId(talentId);
//        // Act
//        GeneralResponse result = talentService.delete(talentId);
//        // Assert
//        assertEquals(talentId, result.id());
//        assertEquals("Deleted successfully!", result.status());
//        verify(talentRepo).delete(talent);
//    }

}
*/
