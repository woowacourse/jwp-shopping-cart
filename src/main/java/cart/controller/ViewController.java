package cart.controller;

import cart.dto.response.MemberResponse;
import cart.dto.response.ProductResponse;
import cart.service.MemberManagementService;
import cart.service.ProductManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    private final ProductManagementService productManagementService;
    private final MemberManagementService memberManagementService;

    public ViewController(final ProductManagementService productManagementService, final MemberManagementService memberManagementService) {
        this.productManagementService = productManagementService;
        this.memberManagementService = memberManagementService;
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponse.from(productManagementService.findAll()));
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponse.from(productManagementService.findAll()));
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/settings")
    public ModelAndView settings(ModelAndView modelAndView) {
        modelAndView.addObject("members", MemberResponse.from(memberManagementService.findAll()));
        modelAndView.setViewName("settings");
        return modelAndView;
    }

    @GetMapping("/cart")
    public ModelAndView cart(ModelAndView modelAndView) {
        //Todo: 장바구니 목록 가져오는 코드 작성
        modelAndView.setViewName("cart");
        return modelAndView;
    }
}
