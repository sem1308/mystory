package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.User;
import uos.mystory.repository.UserRepository;
import uos.mystory.utils.jwt.JwtUserDetail;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String userId) {
        return createUser(userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다.")));
    }

    private JwtUserDetail createUser(User user) {
        //TODO: 회원탈퇴된 유저 처리

        // 권한 생성
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        //TODO: 권한이 많은 경우 처리
//        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                .collect(Collectors.toList());

        return new JwtUserDetail(user.getId(), user.getUserId(), user.getUserPw(), user.getNickname(), user.getMaxNumBlog(), grantedAuthorities);
    }
}
