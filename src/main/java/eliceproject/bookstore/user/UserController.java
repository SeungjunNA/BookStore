package eliceproject.bookstore.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        log.info(userDto.toString());
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forget-username")
    public ResponseEntity<String> findUsername(@RequestBody UserDto userDto){
        try {
            String username = userService.findUsername(userDto);
            return new ResponseEntity<>(username, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> findPassword(@RequestBody UserDto userDto){
        try {
            String password = userService.findPassword(userDto);
            return new ResponseEntity<>(password, HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}