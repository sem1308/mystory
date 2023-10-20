package uos.mystory.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateUserDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    public void 유저_회원가입() throws Exception {
        //given
        User user = User.create(getInsertUserDTO());

        //when
        userRepository.save(user);

        User saved = userRepository.getReferenceById(user.getId());

        //then
        assertEquals(user, saved);
    }
    
    @Test
    public void 유저_정보변경() throws Exception {
        //given
        User user = save_user();

        //when
        String updatedNickname = "ddori";
        String updatedPhoneNum = "01011111111";

        user.update(UpdateUserDTO.builder().id(user.getId()).userPw(null).nickname(updatedNickname).phoneNum(updatedPhoneNum).build());

        User updatedUser = userRepository.getReferenceById(user.getId());

        //then
        assertEquals(updatedUser.getUserPw(), "1308");
        assertEquals(updatedUser.getNickname(), updatedNickname);
        assertEquals(updatedUser.getPhoneNum(), updatedPhoneNum);
    }

    private User save_user(){
        User user = User.create(getInsertUserDTO());

        return userRepository.save(user);
    }

    private InsertUserDTO getInsertUserDTO() {
        return InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build();
    }

    
}
