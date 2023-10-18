package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateUserDTO;
import uos.mystory.exception.DuplicateUserIdException;
import uos.mystory.exception.PasswordMismatchException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param userDTO
     * @return 유저 번호
     * @title 유저 회원가입
     */
    public Long saveUser(@NotNull InsertUserDTO userDTO) {
        // 아이디 중복 체크
        validateUserIdDuplication(userDTO.getUserId());

        // 비밀번호 암호화
        String userPw = passwordEncoder.encode(userDTO.getUserPw());

        // entity 생성
        User user = User.create(userDTO.getUserId(), userPw, userDTO.getNickname(), userDTO.getPhoneNum());

        return userRepository.save(user).getId();
    }

    private void validateUserIdDuplication(String userId) {
        Long numOfUser = userRepository.countByUserId(userId);
        if (numOfUser != 0) {
            throw new DuplicateUserIdException();
        }
    }

    /**
     * @param userId, userPw
     * @return 유저 엔티티
     * @title 유저 로그인
     */
    public User signIn(String userId, String userPw) {
        // 유저 아이디로 유저 엔티티 가져오기
        User user = getUserByUserId(userId);

        // 비밀번호 일치 확인
        if(!passwordEncoder.matches(userPw,user.getUserPw())){
            throw new PasswordMismatchException();
        }

        return user;
    }

    /**
     * @param updateUserDTO
     * @return 유저 엔티티
     * @title 유저 정보 변경
     */
    public void update(@NotNull UpdateUserDTO updateUserDTO) {
        User user = getUser(updateUserDTO.getId());
        user.update(updateUserDTO.getUserPw(),updateUserDTO.getNickname(), updateUserDTO.getPhoneNum());
    }

    /**
     * @param id
     * @return 유저
     * @title 유저 번호로 유저 불러오기
     */
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @param userId
     * @return 유저
     * @title 유저 아이디로 유저 불러오기
     */
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @param pageable
     * @return 페이징된 유저 목록
     * @title 유저 번호로 유저 불러오기
     */
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
