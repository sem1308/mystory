package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import uos.mystory.domain.enums.UserRole;

import java.time.LocalDateTime;

@Entity(name = "users") // user가 DB에서 예약어인 경우가 있어 users로 table 이름 변경
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "id", length = 100, unique = true)
    private String userId;

    @Column(name = "pw", length = 500)
    private String userPw;

    @Column(length = 100, unique = true)
    private String nickname;

    @Column(length = 11, unique = true)
    private String phoneNum;

    @Column(columnDefinition = "TINYINT default 5")
    private Integer maxNumBlog;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdDateTime;

    //==생성 메소드==//
    public static User create(String userId, String userPw, String nickname, String phoneNum){
        return new UserBuilder().userId(userId).userPw(userPw).nickname(nickname).phoneNum(phoneNum)
                .role(UserRole.M).maxNumBlog(5).createdDateTime(LocalDateTime.now()).build();
    }

    //==변경 메소드==//
    public void update(String userPw, String nickname, String phoneNum){
        this.userPw = userPw == null ? this.userPw : userPw;
        this.nickname = nickname == null ? this.nickname : nickname;
        this.phoneNum = phoneNum == null ? this.phoneNum : phoneNum;
    }


    public String toString() {
        return "[userId] : "+userId+", [userPw] : "+userPw+", [nickname] : "+nickname+", [role] : "+role;
    }
}
