package eliceproject.bookstore.user;

import eliceproject.bookstore.security.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError)error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            userServiceImpl.save(userDto);
            return new ResponseEntity<>("user register successfully", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            errors.put("error", e.getMessage());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forget-username")
    public ResponseEntity<String> findUsername(@RequestBody UserDto userDto){
        try {
            String username = userServiceImpl.findUsername(userDto);
            return new ResponseEntity<>(username, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> findPassword(@RequestBody UserDto userDto){
        try {
            String password = userServiceImpl.findPassword(userDto);
            return new ResponseEntity<>(password, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if(userServiceImpl.login(username, password)){
            long expireTime = 1000 * 60 * 60;
            String jwtToken = JwtUtil.createToken(username, expireTime);

            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        };
        return new ResponseEntity<>("아이디와 비밀번호를 다시 입력해줏요.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/userPage")
    public String userInfo(Authentication authentication){
        User user = userServiceImpl.getLoginUserByUsername(authentication);

        System.out.println("authentication = " + authentication);

        return "유저페이지";
    }
}