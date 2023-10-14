package uos.mystory.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    A("관리자"),
    M("회원");

    private String exp;

    UserRole(String exp) {
        this.exp = exp;
    }
}
