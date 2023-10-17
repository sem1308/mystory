package uos.mystory.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.exception.DuplicateUserIdException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void 유저_회원가입() throws Exception {
        //given
        Long id = userService.saveUser(new InsertUserDTO("sem1308", "1308", "ddory", "01000000000"));

        //when
        User user = userService.getUser(id);

        //then
        assertEquals(user.getId(), id);
        System.out.println(user.getUserPw()); // 비밀번호 암호화 확인
    }

    @Test
    public void 유저_회원가입_중복체크() throws Exception {
        //given
        Long id = userService.saveUser(new InsertUserDTO("sem1308", "1308", "ddory", "01000000000"));

        //when
        InsertUserDTO userDTO2 = new InsertUserDTO("sem1308", "1308", "ddory", "01000000000");

        //then
        assertThrows(DuplicateUserIdException.class, () -> {
            userService.saveUser(userDTO2);
        });
    }
    
    @Test
    public void 유저_로그인() throws Exception {
        //given
        
        //when
        
        //then
    }
}
