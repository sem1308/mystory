package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import uos.mystory.domain.enums.UserRole;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateUserDTO;
import uos.mystory.exception.MismatchException;
import uos.mystory.exception.massage.MessageManager;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity(name = "users") // user가 DB에서 예약어인 경우가 있어 users로 table 이름 변경
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "id", length = 100, unique = true, nullable = false)
    private String userId;

    @Column(name = "pw", length = 500, nullable = false)
    private String userPw;

    @Column(length = 100, unique = true, nullable = false)
    private String nickname;

    @Column(length = 11, unique = true, nullable = false)
    private String phoneNum;

    @Column(columnDefinition = "TINYINT default 5")
    private Integer maxNumBlog;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    //==생성 메소드==//
    public static User create(InsertUserDTO insertUserDTO){
        return new UserBuilder().userId(insertUserDTO.getUserId()).userPw(insertUserDTO.getUserPw())
                .nickname(insertUserDTO.getNickname()).phoneNum(insertUserDTO.getPhoneNum())
                .role(UserRole.M).maxNumBlog(5).createdDateTime(LocalDateTime.now()).build();
    }

    //==변경 메소드==//
    public void update(UpdateUserDTO updateUserDTO){
        this.userPw = Optional.ofNullable(updateUserDTO.getUserPw()).orElse(this.userPw);
        this.nickname = Optional.ofNullable(updateUserDTO.getNickname()).orElse(this.nickname);
        this.phoneNum = Optional.ofNullable(updateUserDTO.getPhoneNum()).orElse(this.phoneNum);
    }

    //==비즈니스 로직==//

    /**
     * 비밀번호 암호화
     */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPw = passwordEncoder.encode(this.userPw);
    }

    /**
     * 비밀번호 일치 확인
     */
    public void validatePassword(PasswordEncoder passwordEncoder, String inputPw) {
        if(!passwordEncoder.matches(inputPw,userPw)){
            throw new MismatchException(MessageManager.getMessage("error.mismatch.user.user_pw"));
        }
    }

    public String toString() {
        return "[userId] : "+userId+", [userPw] : "+userPw+", [nickname] : "+nickname+", [role] : "+role;
    }
}
