package cart.controller;

import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.service.AdminService;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    private final AdminService adminService;

    private final MemberService memberService;

    public PageController(AdminService adminService, MemberService memberService) {
        this.adminService = adminService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    String allProducts(Model model) {
        return userAllProducts(model);
    }

    @GetMapping("/products")
    String userAllProducts(Model model) {
        List<ProductEntity> productEntities = adminService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "index";
    }

    @GetMapping("/admin")
    String adminAllProducts(Model model) {
        List<ProductEntity> productEntities = adminService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "admin";
    }

    @GetMapping("/settings")
    String allUsers(Model model) {
        List<MemberEntity> memberEntities = memberService.selectAllMembers();
        model.addAttribute("members", memberEntities);
        return "settings";
    }
}
