package eliceproject.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller("/")
public class HomeController {

    @GetMapping
    public String mainPage() {
        log.info("mainPage로 이동");
        return "home/home.html";
    }

}
