package com.softserve.skillscope.proof;

import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.mapper.proof.ProofMapper;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.kudos.model.request.KudosAmountRequest;
import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.entity.ProofProperties;
import com.softserve.skillscope.proof.model.request.ProofRequest;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.proof.service.impl.ProofServiceImpl;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.request.AddSkillsRequest;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.sponsor.SponsorRepository;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class ProofServiceTest {

    @Mock
    private ProofRepository proofRepo;
    @Mock
    private SponsorRepository sponsorRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private ProofProperties proofProps;
    @Mock
    private ProofMapper proofMapper;

    @Mock
    private UtilService utilService;

    @InjectMocks
    private ProofServiceImpl proofServiceImpl;

    private User userTalent;
    private User userSponsor;
    private Talent talent;
    private Sponsor sponsor;
    private Proof proofPublished;
    private Proof proofDraft;
    private Kudos kudos;
    Skill java;
    Skill postman;
    Skill python;

    @BeforeEach
    void setUp() {
        java = Skill.builder()
                .id(1L)
                .title("Java")
                .build();
        postman = Skill.builder()
                .id(2L)
                .title("Postman")
                .build();
        python = Skill.builder()
                .id(3L)
                .title("Python")
                .build();

        talent = Talent.builder()
                .id(1L)
                .user(User.builder()
                        .id(1L)
                        .email("johndoe@gmail.com")
                        .password("Secret_1")
                        .name("John")
                        .surname("Doe")
                        .roles(Set.of(Role.TALENT.getAuthority()))
                        .build())
                .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                        "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                .experience("Java 2 years")
                .location("Ukraine")
                .phone("+380505555555")
                .birthday(LocalDate.of(2000, 2, 17))
                .about("Nothing interesting")
                .balance(0)
                .proofs(List.of(
                        Proof.builder()
                                .id(1L)
                                .talent(talent)
                                .title("Title1")
                                .description("Description1")
                                .publicationDate(LocalDateTime.now())
                                .status(ProofStatus.PUBLISHED)
                                .skills(Set.of(java, postman))
                                .kudos(List.of(Kudos.builder()
                                        .amount(2)
                                        .build()))
                                .build(),
                        Proof.builder()
                                .id(2L)
                                .talent(talent)
                                .title("Title2")
                                .description("Description2")
                                .publicationDate(null)
                                .status(proofProps.defaultType())
                                .skills(Set.of(java, python))
                                .build()))
                .skills(Set.of(java, postman, python))
                .build();

        userSponsor = User.builder()
                .id(2L)
                .email("sponsor@gmail.com")
                .sponsor(sponsor = Sponsor.builder()
                        .id(2L)
                        .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                                "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                        .location("Ukraine")
                        .phone("+380505555551")
                        .birthday(LocalDate.of(2001, 3, 12))
                        .balance(100)
                        .build())
                .password("Secret_1")
                .name("Spoon")
                .surname("Doe")
                .roles(Set.of(Role.SPONSOR.getAuthority()))
                .build();


        userTalent = talent.getUser();
        sponsor = userSponsor.getSponsor();
//        userSponsor = sponsor.getUser();

        proofPublished = talent.getProofs().get(0);
        proofDraft = talent.getProofs().get(1);
        kudos = proofPublished.getKudos().get(0);
    }

    @Test
    void testGetFullProof() {
        FullProof expectedFullProof = FullProof.builder()
                .id(1L)
                .talentId(talent.getId())
                .talentName(talent.getUser().getName())
                .talentSurname(talent.getUser().getSurname())
                .title("Title")
                .description("Description")
                .publicationDate(LocalDateTime.now())
                .status(ProofStatus.PUBLISHED)
                .build();

        when(utilService.findProofById(proofPublished.getId())).thenReturn(proofPublished);
        when(proofMapper.toFullProof(proofPublished)).thenReturn(expectedFullProof);

        FullProof actualFullProof = proofServiceImpl.getFullProof(proofPublished.getId());

        assertNotNull(actualFullProof);
        assertEquals(expectedFullProof, actualFullProof);
    }

    @Test
    void testAddProof() {
        ProofRequest creationRequest = new ProofRequest("Title", "Description");

        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(utilService.isNotCurrentUser(talent.getUser())).thenReturn(false);

        GeneralResponse expectedResponse = new GeneralResponse(1L, "Created successfully!");
        when(proofRepo.save(any(Proof.class))).thenAnswer(invocation -> {
            Proof savedProof = invocation.getArgument(0);
            savedProof.setId(1L);
            return savedProof;
        });

        GeneralResponse actualResponse = proofServiceImpl.addProof(talent.getId(), creationRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(utilService, times(1)).findTalentById(talent.getId());
        verify(utilService, times(1)).isNotCurrentUser(talent.getUser());
        verify(proofRepo, times(1)).save(any(Proof.class));
    }

    @Test
    void testAddProof_ForbiddenRequest() {
        ProofRequest creationRequest = new ProofRequest("Title", "Description");

        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(utilService.isNotCurrentUser(talent.getUser())).thenReturn(true);

        assertThrows(ForbiddenRequestException.class, () -> proofServiceImpl.addProof(talent.getId(), creationRequest));

        verify(utilService, times(1)).findTalentById(talent.getId());
        verify(utilService, times(1)).isNotCurrentUser(talent.getUser());
        verify(proofRepo, never()).save(any(Proof.class));
    }

    @Test
    void testDeleteProofById(){
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(utilService.findProofById(proofPublished.getId())).thenReturn(proofPublished);
        when(utilService.isNotCurrentUser(talent.getUser())).thenReturn(false);
        when(proofRepo.findByTalentId(talent.getId())).thenReturn(talent.getProofs());

        GeneralResponse expectedResponse = new GeneralResponse(proofPublished.getId(), "Successfully deleted");

        GeneralResponse actualResponse = proofServiceImpl.deleteProofById(talent.getId(), proofPublished.getId());

        assertEquals(expectedResponse, actualResponse);
        verify(utilService).findTalentById(talent.getId());
        verify(utilService).findProofById(proofPublished.getId());
        verify(utilService).isNotCurrentUser(talent.getUser());
        verify(proofRepo).findByTalentId(talent.getId());
        verify(proofRepo).deleteById(proofPublished.getId());
    }

    @Test
    void testGetAllSkills(){
        when(utilService.getSkillsByProofId(proofPublished.getId())).thenReturn(proofPublished.getSkills());

        SkillResponse expectedResponse = new SkillResponse(proofPublished.getId(), proofPublished.getSkills());

        SkillResponse actualResponse = proofServiceImpl.getAllSkillByProof(proofPublished.getId());

        assertEquals(expectedResponse, actualResponse);
        verify(utilService).getSkillsByProofId(proofPublished.getId());
    }

    @Test
    void testEditProofById(){
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(utilService.findProofById(proofDraft.getId())).thenReturn(proofDraft);
        when(utilService.isNotCurrentUser(talent.getUser())).thenReturn(false);
        when(proofRepo.findByTalentId(talent.getId())).thenReturn(talent.getProofs());
        ProofRequest proofRequest = ProofRequest.builder().build();

        when(proofRepo.save(proofDraft)).thenReturn(proofDraft);

        proofServiceImpl.editProofById(talent.getId(), proofDraft.getId(), proofRequest);

        verify(utilService).findTalentById(talent.getId());
        verify(utilService).isNotCurrentUser(userTalent);
        verify(proofRepo).save(proofDraft);
    }

    @Test
    void testPublishProofById(){
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(utilService.findProofById(proofDraft.getId())).thenReturn(proofDraft);
        when(utilService.isNotCurrentUser(talent.getUser())).thenReturn(false);
        when(proofRepo.findByTalentId(talent.getId())).thenReturn(talent.getProofs());

        GeneralResponse expectedResponse = new GeneralResponse(proofDraft.getId(), "Proof successfully published!");

        GeneralResponse actualResponse = proofServiceImpl.publishProofById(talent.getId(), proofDraft.getId());

        assertEquals(expectedResponse, actualResponse);
        verify(utilService).findTalentById(talent.getId());
        verify(utilService).isNotCurrentUser(userTalent);
    }

    @Test
    void testHideProofById(){
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(utilService.findProofById(proofDraft.getId())).thenReturn(proofDraft);
        when(utilService.isNotCurrentUser(talent.getUser())).thenReturn(false);
        when(proofRepo.findByTalentId(talent.getId())).thenReturn(talent.getProofs());

        GeneralResponse expectedResponse = new GeneralResponse(proofDraft.getId(), "Proof successfully hidden!");

        GeneralResponse actualResponse = proofServiceImpl.hideProofById(talent.getId(), proofDraft.getId());

        assertEquals(expectedResponse, actualResponse);
        verify(utilService).findTalentById(talent.getId());
        verify(utilService).isNotCurrentUser(userTalent);
    }

    @Test
    void testAddSkillOnProof(){
        AddSkillsRequest newSkillsRequest = new AddSkillsRequest(Set.of("Postman"));
        Set<Skill> newSkills = Set.of(java, python, postman);
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(utilService.findProofById(proofDraft.getId())).thenReturn(proofDraft);
        when(utilService.isNotCurrentUser(talent.getUser())).thenReturn(false);
        when(proofRepo.findByTalentId(talent.getId())).thenReturn(talent.getProofs());
        when(utilService.stringToSkills(newSkillsRequest.skills())).thenReturn(newSkills);
        proofDraft.setSkills(new HashSet<>());

        GeneralResponse expectedResponse = new GeneralResponse(proofDraft.getId(), "Skills successfully added!");

        GeneralResponse actualResponse = proofServiceImpl.addSkillsOnProof(talent.getId(), proofDraft.getId(), newSkillsRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(utilService).findTalentById(talent.getId());
        verify(utilService, times(2)).findProofById(proofDraft.getId());
        verify(proofRepo).findByTalentId(talent.getId());
        verify(utilService).isNotCurrentUser(userTalent);
        verify(utilService).stringToSkills(newSkillsRequest.skills());
    }

    @Test
    void testShowAmountOfKudosOnProof(){
        when(utilService.findProofById(proofPublished.getId())).thenReturn(proofPublished);

        KudosResponse expectedResponse = new KudosResponse(proofPublished.getId(), false, kudos.getAmount(), 0);

        KudosResponse actualResponse = proofServiceImpl.showAmountKudosOfProof(proofPublished.getId());

        assertEquals(expectedResponse, actualResponse);
        verify(utilService).findProofById(proofPublished.getId());
    }

    @Test
    void testAddKudosToProofBySponsor() {

        int kudosAmount = 1;
        KudosAmountRequest kudosAmountRequest = new KudosAmountRequest(kudosAmount);



        when(utilService.getCurrentUser()).thenReturn(userSponsor);
        when(utilService.findProofById(proofPublished.getId())).thenReturn(proofPublished);
        when(sponsorRepo.save(any())).thenReturn(sponsor);

        GeneralResponse expectedResponse = new GeneralResponse(proofPublished.getId(), kudosAmount  * proofPublished.getSkills().size() + " kudos were added successfully!");

        GeneralResponse actualResponse = proofServiceImpl.addKudosToProofBySponsor(proofPublished.getId(), kudosAmountRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(utilService).getCurrentUser();
        verify(utilService).findProofById(proofPublished.getId());
    }

}