package eliceproject.bookstore.user;

import eliceproject.bookstore.security.CustomUserDetails;
import eliceproject.bookstore.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


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
            userService.save(userDto);
            return new ResponseEntity<>("user register successfully", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            errors.put("error", e.getMessage());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/username-finder")
    public ResponseEntity<String> findUsername(@RequestBody UserDto userDto){
        try {
            String username = userService.findUsername(userDto);
            return new ResponseEntity<>(username, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/password-finder")
    public ResponseEntity<String> findPassword(@RequestBody UserDto userDto){
        try {
            String password = userService.findPassword(userDto);
            return new ResponseEntity<>(password, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        log.info(username + " / " + password);
        if(userService.login(username, password)){
            long expireTime = 1000 * 60 * 60;
            String jwtToken = JwtUtil.createToken(username, expireTime);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .body("로그인 성공");
        };
        return new ResponseEntity<>("아이디와 비밀번호를 다시 입력해줏요.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/userPage")
    public String userInfo(Authentication authentication){
        return "유저페이지";
    }

    @GetMapping("/edit-user")
    public ResponseEntity<?> editUserForm(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = (String) authentication.getPrincipal();
        User user = userService.findByUsername(username);
        System.out.println("user.toString() = " + user.toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}