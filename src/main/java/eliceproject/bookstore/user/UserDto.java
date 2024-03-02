package eliceproject.bookstore.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String passwordConfirm;

    @NotBlank(message = "이름을 입력해세요.")
    @Pattern(regexp = "[가-힣]*$", message = "이름을 정확히 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$",
            message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Pattern(regexp = "^\\d{11}$", message = "휴대폰 번호는 11자리를 입력해주세요.")
    private String mobileNumber;

    @Pattern(regexp = "^\\d{8}$", message = "생년월일 8자리를 입력해주세요.")
    private String birthday;

    public User toEntity(){
        return new User(username, password, name, email, mobileNumber, birthday);
    }
}
