package uos.mystory.utils.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JwtUserDetail implements UserDetails{
        @Getter
        private final Long id;

        @Getter
        private final String nickname;

        @Getter
        private final Integer maxNumBlog;

        private final String userId;

        @Setter
        private String userPw;

        private final Set<GrantedAuthority> authorities;

        private final boolean accountNonExpired;

        private final boolean accountNonLocked;

        private final boolean credentialsNonExpired;

        private final boolean enabled;

        public JwtUserDetail(Long id, String userId, String userPw, String nickname, Integer maxNumBlog, Collection<? extends GrantedAuthority> authorities) {
                this(id, userId, userPw, nickname, maxNumBlog, true, true, true, true, authorities);
        }

        public JwtUserDetail(Long id, String userId, String userPw, String nickname, Integer maxNumBlog, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
                Assert.isTrue(userId != null && !userId.isEmpty() && userPw != null,
                        "Cannot pass null or empty values to constructor");
                this.id = id;
                this.userId = userId;
                this.userPw = userPw;
                this.nickname = nickname;
                this.maxNumBlog = maxNumBlog;
                this.enabled = enabled;
                this.accountNonExpired = accountNonExpired;
                this.credentialsNonExpired = credentialsNonExpired;
                this.accountNonLocked = accountNonLocked;
                this.authorities = toSet(authorities);
        }

        private static HashSet<GrantedAuthority> toSet(Collection<? extends GrantedAuthority> authorities) {
                Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
                HashSet<GrantedAuthority> authoritiesSet = new HashSet<>();
                for (GrantedAuthority grantedAuthority : authorities) {
                        Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
                        authoritiesSet.add(grantedAuthority);
                }
                return authoritiesSet;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return this.authorities;
        }

        @Override
        public String getPassword() {
                return this.userPw;
        }

        @Override
        public String getUsername() {
                return this.userId;
        }

        @Override
        public boolean isAccountNonExpired() {
                return this.accountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
                return this.accountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return this.credentialsNonExpired;
        }

        @Override
        public boolean isEnabled() {
                return this.enabled;
        }
}
