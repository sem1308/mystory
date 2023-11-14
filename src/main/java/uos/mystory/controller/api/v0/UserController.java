package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateUserDTO;
import uos.mystory.dto.request.form.SignInUserForm;
import uos.mystory.dto.request.create.CreateUserDTO;
import uos.mystory.dto.request.fix.FixUserDTO;
import uos.mystory.dto.response.ResponseUserDTO;
import uos.mystory.service.UserService;
import uos.mystory.utils.jwt.JwtFilter;
import uos.mystory.utils.jwt.JwtTokenProvider;

@RestController()
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
        //==인증되지 않은 Authentication 객체 생성==//
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(form.userId(), form.userPw());

        //==인증된 Authentication 객체 받아오기==//
        // 1. AuthenticationManager에서 authenticate 메소드 실행
        // 2. 루프를 돌며 AuthenticationProvier 호출
        // 3. AuthenticationProvier가 UsernamePasswordAuthenticationToken를 support한다면 인증 진행
        // JWT 토큰 인증시 UsernamePasswordAuthenticationToken 인증 처리하는 AbstractUserDetailsAuthenticationProvider가 호출
        // (정확히는 AbstractUserDetailsAuthenticationProvider를 상속하고 있는 DaoAuthenticationProvider 호출)
        // 이 Provider는 다음과 같은 UsernamePasswordAuthenticationToken를 지원한다는 supports함수를 가지고 있음
        //        @Override
        //        public boolean supports(Class<?> authentication) {
        //            return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
        //        }
        // == 인증 ==
        // 4. CustomUserDetailsService class의 loadUserByUsername 메소드가 실행되어 DB에 저장된 user entity를 불러옴
        // 5. user entity의 userPW와 입력받은 form의 userPW를 비교
        //    a. 비밀번호가 다르다면 BadCredentialsException 발생
        //    b. 비밀번호가 맞으면 인증된 Authentication 객체 반환
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // jwt를 response body에 넣어서 리턴
        return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
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
