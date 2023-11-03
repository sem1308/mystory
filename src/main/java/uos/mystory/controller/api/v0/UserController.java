package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateUserDTO;
import uos.mystory.dto.request.form.SignInUserForm;
import uos.mystory.dto.request.create.CreateUserDTO;
import uos.mystory.dto.request.fix.FixUserDTO;
import uos.mystory.dto.response.ResponseUserDTO;
import uos.mystory.service.UserService;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseUserDTO> getUser(@PathVariable("user_id") Long userId){
        User user = userService.getUser(userId);
        ResponseUserDTO userResponseDTO = new ResponseUserDTO(user.getId(), user.getNickname(), user.getMaxNumBlog(), user.getRole());
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<Long> signUp(@RequestBody CreateUserDTO form){
        InsertUserDTO insertUserDTO = InsertUserDTO.builder()
                .userId(form.userId()).userPw(form.userPw()).nickname(form.nickname()).phoneNum(form.phoneNum())
                .build();
        Long id = userService.saveUser(insertUserDTO);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<String> signIn(@RequestBody SignInUserForm form){
        User user = userService.signIn(form.userId(), form.userPw());
        // TODO: JWT 토큰 반환
        String token = "token";
        return ResponseEntity.ok(token);
    }

    @PutMapping()
    public ResponseEntity<String> updateUserInfo(@RequestBody FixUserDTO userDTO){
        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder()
                .id(userDTO.id())
                .userPw(userDTO.userPw())
                .nickname(userDTO.nickname())
                .phoneNum(userDTO.phoneNum())
                .build();
        userService.updateUser(updateUserDTO);
        return ResponseEntity.ok("success");
    }

//    @DeleteMapping("/{user_id}")
//    public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long id){
//        userService.deleteUser(id);
//        return ResponseEntity.ok("success");
//    }
}
