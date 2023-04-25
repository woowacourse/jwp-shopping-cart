package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    @GetMapping("/hello")
    public String greeting(Model model) { // 웹 페이지를 렌더링한다.
        model.addAttribute("name", "땡칠");
        return "asd"; // HTML 파일 이름
    }
}
