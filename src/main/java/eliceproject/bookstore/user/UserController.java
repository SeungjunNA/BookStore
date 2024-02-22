package eliceproject.bookstore.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/register")
    public String registerForm(){
        return "register/register.html";
    }
}
