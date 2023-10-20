package uos.mystory.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateUserDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.MismatchException;
import uos.mystory.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    private void 유저_회원가입() throws Exception {
        //given
        Long id = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());

        //when
        User user = userService.getUser(id);

        System.out.println(user);

        //then
        assertEquals(user.getId(), id);
        System.out.println(user.getUserPw()); // 비밀번호 암호화 확인
    }

    @Test
    private void 유저_회원가입_중복체크() throws Exception {
        //given
        Long id = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());

        //when
        InsertUserDTO userDTO2 = InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build();;

        //then
        assertThrows(DuplicateException.class, () -> {
            userService.saveUser(userDTO2);
        });
    }
    
    @Test
    private void 유저_로그인() throws Exception {
        //given
        userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());

        //when
        //then
        // 정상 로그인
        userService.signIn("sem1308", "1308");
        // 아이디 존재 X
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.signIn("sem13081", "1308");
        });
        // 비밀번호 불일치
        assertThrows(MismatchException.class, () -> {
            userService.signIn("sem1308", "13081");
        });
    }
    
    @Test
    private void 유저_정보변경() throws Exception {
        //given
        String updatedNickname = "ddori";
        String updatedPhoneNum = "01011111111";
        Long id = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());

        //when
        userService.updateUser(UpdateUserDTO.builder().id(id).userPw(null).nickname(updatedNickname).phoneNum(updatedPhoneNum).build());

        //then
        User user = userService.getUser(id);
        assertEquals(user.getNickname(),updatedNickname);
        assertEquals(user.getPhoneNum(),updatedPhoneNum);

    }

}
