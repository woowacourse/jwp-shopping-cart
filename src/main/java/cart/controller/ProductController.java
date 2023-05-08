package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "등록된 모든 상품 조회")
    @GetMapping(value = {"/products", "/"})
    public String indexPage(final Model model) {
        final List<ProductResponse> products = productService.findAll();

        model.addAttribute("products", products);

        return "index";
    }
}
