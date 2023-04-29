package cart.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.product.service.ProductService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = {"/", "/admin"})
    public String displayHome(Model model, HttpServletRequest request) {
        model.addAttribute("products", productService.findAll());
        if (request.getRequestURI().equals("/admin")) {
            return "admin";
        }
        return "index";
    }
}
