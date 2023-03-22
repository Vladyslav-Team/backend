package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentFlashcard;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {

    @Autowired
    TalentRepository talentRepo;

    @Override
    public List<TalentFlashcard> showAllTalents(){
        List<TalentFlashcard> talentFlashcardList = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection ("jdbc:h2:mem:SkillScopeDB", "sa", "");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM TALENT JOIN TALENT_INFO ON TALENT.ID = TALENT_INFO.TALENT_ID;;");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String image = rs.getString("image");
                String location = rs.getString("location");

                TalentFlashcard talentData = new TalentFlashcard(id, name, surname, image, location);
                talentFlashcardList.add(talentData);
            }
            con.close();
        }
        catch(Exception e)  {System.out.println("Отримано виняток "+e);}

        return talentFlashcardList;
    }

    @Override
    public void createTalent(Talent talent) {
        talentRepo.save(talent);
    }

    @Override
    public void updateTalent(Talent talent) {
        talentRepo.save(talent);
    }

    @Override
    public void deleteTalent(Long id) {
        talentRepo.deleteById(id);
    }
}
