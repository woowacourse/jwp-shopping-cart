package cart.controller;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dao.entity.MemberEntity;
import cart.dao.entity.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductDao mySQLProductDao;
    private final MemberDao mySQLMemberDao;

    public PageController(ProductDao mySQLProductDao, MemberDao mySQLMemberDao,
        CartDao mySQLCartDao) {
        this.mySQLProductDao = mySQLProductDao;
        this.mySQLMemberDao = mySQLMemberDao;
    }

    @GetMapping
    public String loadHome(Model model) {
        final List<ProductEntity> productEntities = mySQLProductDao.findAll();

        List<ProductResponse> products = ProductResponse.from(productEntities);

        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String loadAdmin(Model model) {
        final List<ProductEntity> productEntities = mySQLProductDao.findAll();
        List<ProductResponse> products = ProductResponse.from(productEntities);

        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String loadSetting(Model model) {
        final List<MemberEntity> memberEntities = mySQLMemberDao.findAll();
        List<MemberResponse> members = MemberResponse.from(memberEntities);

        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    public String loadCart() {
        return "cart";
    }
}
