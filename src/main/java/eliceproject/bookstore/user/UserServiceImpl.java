package eliceproject.bookstore.user;

import eliceproject.bookstore.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.Math.random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public User save(UserDto userDto) {
        validateDuplicateUserName(userDto);
        validateEqualsPassword(userDto);
        validateDuplicateEmail(userDto);
        validateDuplicateMobileNumber(userDto);

        User user = userDto.toEntity();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);

        return userRepository.save(user);
    }

    public String findUsername(UserDto userDto) {
        User user = userRepository.findByNameAndEmail(userDto.getName(), userDto.getEmail());
        if(user == null || user.isDeleted()){
            throw new IllegalStateException("이름과 이메일을 정확히 입력해주세요.");
        }
        return user.getUsername();
    }

    public String findPassword(UserDto userDto) {
        User user = userRepository.findByNameAndUsernameAndEmail(userDto.getName(), userDto.getUsername(), userDto.getEmail());
        if(user == null || user.isDeleted()){
            throw new IllegalStateException("이름과 아이디와 이메일을 정확히 입력해주세요.");
        }
        Random random = new Random();
        int start = 97;
        int end = 122;
        String random1 = random.ints(start, end+1).limit(3)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        int random2 = (int) ((random()+1)*10000-10000);
        String newPassword = random1 + random2 + "*";
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return newPassword;
    }

    public String login(String username, String password){
        User user = userRepository.findByUsername(username);
        if (user == null || user.isDeleted()){
            throw new IllegalStateException("존재하지 않는 아이디 입니다.");
        }
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        return jwtUtil.createToken(authentication);
    }

    @Override
    public void update(UserDto userDto) {
        validateEqualsPassword(userDto);
        User user = userRepository.findByUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public Long findUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username); // 주어진 username으로 사용자를 조회
        if (user != null) {
            return user.getId(); // 사용자가 존재하면 해당 사용자의 userid를 반환
        } else {
            return null; // 사용자가 존재하지 않으면 null 반환하거나 적절한 예외 처리를 수행할 수 있습니다.
        }
    }

    public User findByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null || user.isDeleted()){
            throw new IllegalArgumentException("유저를 찾을 수 없습니다.");
        }
        return user;
    }

    private void validateDuplicateMobileNumber(UserDto userDto) {
        User user = userRepository.findByMobileNumber(userDto.getMobileNumber());
        if(user != null){
            throw new IllegalStateException("동일한 핸드폰 번호로 가입한 계정이 있습니다.");
        }
    }

    private void validateDuplicateEmail(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw new IllegalStateException("동일한 이메일로 가입한 계정이 있습니다.");
        }
    }

    public void validateEqualsPassword(UserDto userDto) {
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
}
