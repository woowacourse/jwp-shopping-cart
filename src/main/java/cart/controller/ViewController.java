package cart.controller;

import cart.dto.response.CategoryResponseDto;
import cart.dto.response.MemberResponseDto;
import cart.dto.response.ProductResponseDto;
import cart.service.CategoryService;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public final class ViewController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    public ViewController(
            final ProductService productService,
            final CategoryService categoryService,
            final MemberService memberService
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.memberService = memberService;
    }

    @GetMapping
    public ModelAndView indexPage(final ModelAndView modelAndView) {
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        modelAndView.addObject("products", productResponseDtos);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView adminPage(final ModelAndView modelAndView) {
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        final List<CategoryResponseDto> categories = categoryService.findCategories();
        modelAndView.addObject("products", productResponseDtos);
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/settings")
    public ModelAndView settingPage(final ModelAndView modelAndView) {
        final List<MemberResponseDto> members = memberService.findMembers();
        modelAndView.addObject("members", members);
        modelAndView.setViewName("settings");
        return modelAndView;
    }

    @GetMapping("/cart")
    public ModelAndView cartPage(final ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        return modelAndView;
    }
}
