package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateUserDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @title 유저 회원가입
     * @param insertUserDTO
     * @return 유저 번호
     */
    public Long saveUser(@NotNull InsertUserDTO insertUserDTO) {
        // 아이디 중복 체크
        validateUserIdDuplication(insertUserDTO.getUserId());

        // entity 생성
        User user = User.create(insertUserDTO);
        // 비밀번호 암호화
        user.encodePassword(passwordEncoder);

        return userRepository.save(user).getId();
    }

    private void validateUserIdDuplication(String userId) {
        Long numOfUser = userRepository.countByUserId(userId);
        if (numOfUser != 0) {
            throw new DuplicateException(MessageManager.getMessage("error.duplicate.user.user_id"));
        }
    }

    /**
     * @title 유저 로그인
     * @param userId, userPw
     * @return 유저 엔티티
     */
    @Deprecated(since = "JWT 인증방식에 의해 사용 중지")
    @Transactional(readOnly = true)
    public User signIn(String userId, String userPw) {
        // 유저 아이디로 유저 엔티티 가져오기
        User user = getUserByUserId(userId);

        // 비밀번호 일치 확인
        user.validatePassword(passwordEncoder, userPw);

        return user;
    }

    /**
     * @title 유저 정보 변경
     * @param updateUserDTO
     * @return 유저 엔티티
     */
    public void updateUser(@NotNull UpdateUserDTO updateUserDTO) {
        User user = getUser(updateUserDTO.getId());
        user.update(updateUserDTO);
    }

    /**
     * @title 유저 번호로 유저 불러오기
     * @param id
     * @return 유저
     */
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(MessageManager.getMessage("error.notfound.user")));
    }

    /**
     * @title 유저 아이디로 유저 불러오기
     * @param userId
     * @return 유저
     */
    @Transactional(readOnly = true)
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException(MessageManager.getMessage("error.notfound.user")));
    }

    /**
     * @title 유저 번호로 유저 불러오기
     * @param pageable
     * @return 페이징된 유저 목록
     */
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * @title 유저 번호로 유저 삭제하기
     * @param userId
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
