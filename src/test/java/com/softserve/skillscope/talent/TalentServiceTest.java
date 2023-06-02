package com.softserve.skillscope.talent;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.handler.exception.skillException.SkillNotFoundException;
import com.softserve.skillscope.general.mapper.talent.TalentMapper;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.skill.model.dto.SkillWithVerification;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.request.AddSkillsRequest;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.TalentStatsResponse;
import com.softserve.skillscope.talent.service.impl.TalentServiceImpl;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class TalentServiceTest {
    @Mock
    private TalentRepository talentRepo;
    @Mock
    private TalentMapper talentMapper;
    @Mock
    private UtilService utilService;
    @InjectMocks
    private TalentServiceImpl talentService;
    private User user;
    private Talent talent;

    @BeforeEach
    public void createTalent() {
        user = User.builder()
                .id(1L)
                .email("vladkharchenko@gmail.com")
                .password("vladkharchenko_123")
                .name("Vlad")
                .surname("Kharchenko")
                .roles(Set.of(Role.TALENT.getAuthority()))
                .build();
        Skill skill = Skill.builder()
                .title("Java")
                .build();
        Skill skill2 = Skill.builder()
                .title("Python")
                .build();
        Skill skill3 = Skill.builder()
                .title("C++")
                .build();
        Set<Skill> skills = Stream.of(skill, skill2, skill3).collect(Collectors.toSet());

        talent = Talent.builder()
                .id(1L)
                .user(user)
                .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                        "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                .experience("Java 2 years")
                .location("Poland")
                .phone("+380937771122")
                .birthday(LocalDate.of(2003, 2, 17))
                .about("Nothing interesting")
                .balance(100)
                .proofs(List.of(new Proof()))
                .skills(skills)
                .build();
    }

    @Test
    @DisplayName("Get talent profile")
    @Order(1)
    void given_Talent_When_GetTalentProfile_Then_ReturnTalentProfile() {
        //talentMapper.toTalentProfile(talent); is pacifier, so you do this
        // TalentProfile talentProfile1 = talentMapper.toTalentProfile(talent);
        TalentProfile talentProfile = TalentProfile.builder()
                .id(1L)
                .name("Vlad")
                .surname("Kharchenko")
                .age(20)
                .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                        "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                .experience("Java 2 years")
                .location("Poland")
                .phone("+380937771122")
                .about("Nothing interesting")
                .balance(100)
                .skills(Set.of(SkillWithVerification.builder().build()))
                .build();
        log.info("talentProfile={}", talentProfile);
        //when -> then (setup for my service), for every mock, every when
        //when receives only mocks
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(talentMapper.toTalentProfile(talent)).thenReturn(talentProfile);
        // Act (pacifier into service, so we use injection)
        talentService.getTalentProfile(talent.getId());
        // Assert
        assertThat(talentProfile).isNotNull();
    }

    @Test
    @DisplayName("Get talent image")
    @Order(2)
    void given_Talent_When_GetTalentImage_Then_ReturnImageResponse() {
        ImageResponse imageResponse = new ImageResponse("https://static.wikia.nocookie.net/bleach/images/f/f1" +
                "/Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en");
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(talentMapper.toTalentImage(talent)).thenReturn(imageResponse);
        talentService.getTalentImage(talent.getId());
        assertThat(imageResponse).isNotNull();
    }

    @Test
    @DisplayName("Check if not Current Talent")
    @Order(3)
    void given_Talent_When_IsNotCurrentUser_Then_ThrowForbiddenRequestException() {
        when(utilService.isNotCurrentUser(talent.getUser())).thenThrow(ForbiddenRequestException.class);
        assertThrows(ForbiddenRequestException.class, () -> {
            utilService.isNotCurrentUser(talent.getUser());
        });
    }

    @Test
    @DisplayName("Edit talent profile")
    @Order(4)
    void given_TalentIdAndEditRequest_When_EditTalentProfile_Then_ReturnResponse() {
        Talent newTalent = mock(Talent.class);
        Talent saveTalent = mock(Talent.class);
        User mockedUser = mock(User.class);
        TalentEditRequest talentEditRequest = TalentEditRequest.builder().build();

        when(utilService.findTalentById(5L)).thenReturn(newTalent);
        when(newTalent.getUser()).thenReturn(mockedUser);
        //we don't face the exception
        when(utilService.isNotCurrentUser(mockedUser)).thenReturn(false);
        when(talentRepo.save(newTalent)).thenReturn(saveTalent);
        when(saveTalent.getId()).thenReturn(5L);
        talentService.editTalentProfile(5L, talentEditRequest);
        //Check if methods were called
        verify(utilService).findTalentById(5L);
        verify(utilService).isNotCurrentUser(mockedUser);
        verify(talentRepo).save(newTalent);
        verify(saveTalent).getId();
    }

    @Test
    @DisplayName("Add new skill and save")
    @Order(5)
    void given_Talent_When_addNewSkill_Then_Save() {
        User newUser = mock(User.class);
        // Mock dependencies and create test data
        Long talentId = 1L;
        AddSkillsRequest newSkillsRequest = AddSkillsRequest.builder()
                .skills(Set.of("Java", " Python", "C++"))
                .build();
        Set<Skill> newSkills = utilService.stringToSkills(newSkillsRequest.skills());
        Talent newTalent = Talent.builder()
                .id(talentId)
                .user(newUser)
                .skills(newSkills)
                .build();

        // Mock the behavior of dependencies
        when(utilService.findTalentById(talentId)).thenReturn(newTalent);
        when(utilService.isNotCurrentUser(newUser)).thenReturn(false);
        when(utilService.stringToSkills(newSkillsRequest.skills())).thenReturn(newSkills);
        when(talentRepo.save(newTalent)).thenReturn(newTalent);

        // Invoke the method
        talentService.addSkillsOnTalentProfile(talentId, newSkillsRequest);

        // Verify the interactions and assertions
        verify(utilService).findTalentById(talentId);

        verify(utilService).isNotCurrentUser(newUser);
        //if you call the method more than 2 times, write times
        verify(utilService, times(2)).stringToSkills(newSkillsRequest.skills());
        verify(talentRepo).save(newTalent);
    }

    @Test
    @DisplayName("Delete skill from talent profile")
    @Order(6)
    void given_Skill_When_DeleteSkillFromTalentProfile_Then_Save() {
        // Mocked data
        Long talentId = 1L;
        Long skillId = 1L;
        Skill skill = Skill.builder().id(skillId).title("Java").build();
        Talent talent = Talent.builder().id(talentId).user(user).skills(new HashSet<>(Arrays.asList(skill))).build();

        // Mocking the behavior of utilService and talentRepo
        when(utilService.findSkillById(skillId)).thenReturn(skill);
        when(utilService.findTalentById(talentId)).thenReturn(talent);
        when(utilService.isNotCurrentUser(user)).thenReturn(false);
        when(talentRepo.save(talent)).thenReturn(talent);

        // Call the method
        GeneralResponse response = talentService.deleteSkillFromTalentProfile(talentId, skillId);

        // Verify the interactions and assertions
        verify(utilService).findSkillById(skillId);
        verify(utilService).findTalentById(talentId);
        verify(utilService).isNotCurrentUser(user);
        verify(talentRepo).save(talent);

        // Assertions
        assertThat(response).isNotNull();
        // Verify that the skill is removed from the talent's skill set
        assertThat(talent.getSkills()).isNotEqualTo(skill);
        assertThat(response.id()).isEqualTo(talentId);
    }

    @Test
    @DisplayName("Delete skill from talent profile - Talent with no skills")
    @Order(7)
    void given_TalentWithNoSkills_When_DeleteSkillFromTalentProfile_Then_ThrowBadRequestException() {
        // Mocked data
        Long talentId = 1L;
        Long skillId = 1L;
        Talent talent = Talent.builder().id(talentId).user(user).skills(new HashSet<>()).build();

        // Mocking the behavior of utilService
        when(utilService.findTalentById(talentId)).thenReturn(talent);
        when(utilService.isNotCurrentUser(user)).thenReturn(false);

        // Call the method and assert the exception
        assertThrows(BadRequestException.class,
                () -> talentService.deleteSkillFromTalentProfile(talentId, skillId));

        // Verify the interactions
        verify(utilService).findTalentById(talentId);
        verify(utilService).isNotCurrentUser(user);
    }

    @Test
    @DisplayName("Delete skill from talent profile - Skill not found in talent's skills")
    @Order(8)
    void given_SkillNotInTalentSkills_When_DeleteSkillFromTalentProfile_Then_ThrowSkillNotFoundException() {
        // Mocked data
        Long talentId = 1L;
        Long skillId = 2L;
        Skill skillToDelete = Skill.builder().id(skillId).title("Python").build();
        Skill existingSkill = Skill.builder().id(1L).title("Java").build();
        Talent talent = Talent.builder().id(talentId).user(user).skills(Set.of(existingSkill)).build();

        // Mocking the behavior of utilService
        when(utilService.findSkillById(skillId)).thenReturn(skillToDelete);
        when(utilService.findTalentById(talentId)).thenReturn(talent);
        when(utilService.isNotCurrentUser(user)).thenReturn(false);

        // Call the method and assert the exception
        assertThrows(SkillNotFoundException.class,
                () -> talentService.deleteSkillFromTalentProfile(talentId, skillId));

        // Verify the interactions
        verify(utilService).findSkillById(skillId);
        verify(utilService).findTalentById(talentId);
        verify(utilService).isNotCurrentUser(user);
        verify(talentRepo, never()).save(any(Talent.class));
    }

//    @Test
//    @DisplayName("Show own most kudos proofs")
//    @Order(9)
//    void given_Talent_When_ShowOwnMostKudosProofs_Then_ReturnAmount() {
//        // Mocked data
//        Long talentId = 1L;
//        Talent talent = Talent.builder().id(talentId).user(user).proofs(List.of(
//                Proof.builder().id(1L).status(ProofStatus.PUBLISHED).kudos(List.of(
//                        Kudos.builder().amount(5).build(),
//                        Kudos.builder().amount(3).build()
//                )).build(),
//                Proof.builder().id(2L).status(ProofStatus.PUBLISHED).kudos(List.of(
//                        Kudos.builder().amount(2).build(),
//                        Kudos.builder().amount(4).build()
//                )).build(),
//                Proof.builder().id(3L).status(ProofStatus.PUBLISHED).kudos(List.of(
//                        Kudos.builder().amount(1).build(),
//                        Kudos.builder().amount(5).build()
//                )).build()
//        )).build();
//
//        // Mocking the behavior of utilService
//        when(utilService.findTalentById(talentId)).thenReturn(talent);
//        when(utilService.isNotCurrentUser(user)).thenReturn(false);
//
//        // Call the method
//        TalentStatsResponse result = talentService.showOwnMostKudosProofs(talentId);
//
//        // Verify the interactions
//        verify(utilService).findTalentById(talentId);
//        verify(utilService).isNotCurrentUser(user);
//
//        // Perform the assertions
//        assertThat(result).isNotNull();
//        assertEquals(new HashSet<>(result.mostKudosedList()), new HashSet<>(Set.of(1L)));
//    }

    @Test
    @DisplayName("Show own most kudosed skills")
    @Order(10)
    void given_Talent_When_GetOwnMostKudosedSkills_Then_ReturnAmount() {
        // Mocked data
        Long talentId = 1L;

        // Create the skills with kudos
        Skill skill1 = Skill.builder().id(1L).build();
        Skill skill2 = Skill.builder().id(2L).build();
        Skill skill3 = Skill.builder().id(3L).build();


        // Create the proofs with skills
        Proof proof1 = Proof.builder().id(1L)
                .status(ProofStatus.PUBLISHED)
                .skills(Set.of(skill1, skill2))
                .build();

        Proof proof2 = Proof.builder().id(2L)
                .status(ProofStatus.PUBLISHED)
                .skills(Set.of(skill2, skill3))
                .build();

        Proof proof3 = Proof.builder().id(3L)
                .status(ProofStatus.PUBLISHED)
                .skills(Set.of(skill1, skill3))
                .build();
        // Create the kudos for each skill
        Kudos kudos1a = Kudos.builder().amount(2).skill(skill1).proof(proof1).build();
        Kudos kudos1b = Kudos.builder().amount(3).skill(skill1).proof(proof1).build();

        Kudos kudos2a = Kudos.builder().amount(1).skill(skill2).proof(proof2).build();
        Kudos kudos2b = Kudos.builder().amount(5).skill(skill2).proof(proof2).build();

        Kudos kudos3a = Kudos.builder().amount(2).skill(skill3).proof(proof3).build();
        Kudos kudos3b = Kudos.builder().amount(4).skill(skill3).proof(proof3).build();

        // Set the kudos for each skill
        skill1.setKudos(List.of(kudos1a, kudos1b));
        skill2.setKudos(List.of(kudos2a, kudos2b));
        skill3.setKudos(List.of(kudos3a, kudos3b));

        proof1.setKudos(List.of(kudos1a, kudos1b));
        proof2.setKudos(List.of(kudos2a, kudos2b));
        proof3.setKudos(List.of(kudos3a, kudos3b));
        // Set the proofs for the talent
        Talent talent = Talent.builder().id(talentId).user(user)
                .proofs(List.of(proof1, proof2, proof3))
                .skills(Set.of(skill1, skill2, skill3))
                .build();

        // Initialize the proofs field in the talent entity
        talent.setProofs(List.of(proof1, proof2, proof3));

        // Mocking the behavior of utilService
        when(utilService.findTalentById(talentId)).thenReturn(talent);
        when(utilService.isNotCurrentUser(user)).thenReturn(false);

        // Call the method
        TalentStatsResponse result = talentService.getOwnMostKudosedSkills(talentId);

        // Verify the interactions
        verify(utilService).findTalentById(talentId);
        verify(utilService).isNotCurrentUser(user);

        // Perform the assertions
        assertThat(result).isNotNull();
        assertEquals(new HashSet<>(result.mostKudosedList()), new HashSet<>(Set.of(2L, 3L)));
    }
}
