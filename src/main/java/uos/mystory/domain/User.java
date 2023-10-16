package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import uos.mystory.domain.enums.UserRole;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "id", length = 100)
    private String userId;

    @Column(name = "pw", length = 500)
    private String userPw;

    @Column(length = 100)
    private String nickname;

    @Column(length = 11)
    private String phoneNum;

    @Column(columnDefinition = "TINYINT default 5")
    private Integer maxNumBlog;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdAt;

    //==생성 메소드==//
    public static User createUser(String userId, String userPw, String nickname, String phoneNum){
        // TODO: 비밀번호 암호화 or 암호화된 비밀번호 받기
        return new UserBuilder().userId(userId).userPw(userPw).nickname(nickname).phoneNum(phoneNum)
                .role(UserRole.M).maxNumBlog(5).createdAt(LocalDateTime.now()).build();
    }

    //==변경 메소드==//
    public void update(String userId, String userPw, String nickname, String phoneNum){
        this.userId = userId == null ? this.userId : userId;
        this.userPw = userPw == null ? this.userPw : userPw;
        this.nickname = nickname == null ? this.nickname : nickname;
        this.phoneNum = phoneNum == null ? this.phoneNum : phoneNum;
    }
}
