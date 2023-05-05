package cart.controller;


import cart.dao.MemeberDao;
import cart.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SettingsController {

    private final MemeberDao memeberDao;

    public SettingsController(MemeberDao memeberDao) {
        this.memeberDao = memeberDao;
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        List<Member> members = memeberDao.findAll();
        model.addAttribute("members", members);
        return "settings";
    }


}
