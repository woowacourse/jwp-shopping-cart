package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.Member;
import cart.domain.Product;

@Controller
public class PageController {

    private final ProductDao productDao;
    private final MemberDao memberDao;

    public PageController(final ProductDao productDao, final MemberDao memberDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @GetMapping("/")
    public String index(final Model model) {
        List<Product> products = productDao.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", productResponses);

        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        List<Product> products = productDao.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", productResponses);

        return "admin";
    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        List<Member> members = memberDao.findAll();
        List<MemberResponse> memberResponse = members.stream()
                .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
        model.addAttribute("members", memberResponse);

        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
