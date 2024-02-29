package eliceproject.bookstore.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(UserDto userDto) {
        validateDuplicateUserName(userDto);
        validateEqualsPassword(userDto);
        validateDuplicateEmail(userDto);
        validateDuplicateMobileNumber(userDto);

        User user = userDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);

        return userRepository.save(user);
    }

    private void validateDuplicateMobileNumber(UserDto userDto) {
        User finduser = userRepository.findByMobileNumber(userDto.getMobileNumber());
        if(finduser != null){
            throw new IllegalStateException("동일한 핸드폰 번호로 가입한 계정이 있습니다.");
        }
    }

    private void validateDuplicateEmail(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw new IllegalStateException("동일한 이메일로 가입한 계정이 있습니다.");
        }
    }

    private void validateEqualsPassword(UserDto userDto) {
        if(!userDto.getPassword().equals(userDto.getPasswordConfirm())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateDuplicateUserName(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if(user != null){
            throw new IllegalStateException("중복되는 아이디가 있습니다.");
        }
    }

    public String findUsername(UserDto userDto) {
        User user = userRepository.findByNameAndEmail(userDto.getName(), userDto.getEmail());
        if(user == null){
            throw new IllegalStateException("이름과 이메일을 정확히 입력해주세요.");
        }
        return user.getUsername();
    }

    public String findPassword(UserDto userDto) {
        User user = userRepository.findByNameAndUsernameAndEmail(userDto.getName(), userDto.getUsername(), userDto.getEmail());
        if(user == null){
            throw new IllegalStateException("이름과 아이디와 이메일을 정확히 입력해주세요.");
        }
        return user.getPassword();
    }

}
