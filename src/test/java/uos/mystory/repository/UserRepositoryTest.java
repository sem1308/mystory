package uos.mystory.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    public void 유저_회원가입() throws Exception {
        //given
        User user = User.create("sem1308", "1308", "ddory", "01000000000");

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
        String updatedPw = "13081";
        String updatedNickname = "ddori";
        String updatedPhoneNum = "01011111111";

        user.update(null, updatedPw, updatedNickname, updatedPhoneNum);

        User updatedUser = userRepository.getReferenceById(user.getId());

        //then
        assertEquals(updatedUser.getUserId(), "sem1308");
        assertEquals(updatedUser.getUserPw(), updatedPw);
        assertEquals(updatedUser.getNickname(), updatedNickname);
        assertEquals(updatedUser.getPhoneNum(), updatedPhoneNum);
    }

    private User save_user(){
        User user = User.create("sem1308", "1308", "ddory", "01000000000");

        return userRepository.save(user);
    }

    
}
