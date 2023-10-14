package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import uos.mystory.domain.enums.UserRole;

import java.time.LocalDateTime;

@Entity
@Getter
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

    @Column(columnDefinition = "TINYINT")
    private Integer maxNumBlog;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdAt;
}
