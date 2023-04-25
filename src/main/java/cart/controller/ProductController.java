package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductRequest;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("/index");

        ProductDto chicken = new ProductDto(1,
                "치킨",
                "https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg",
                10000);
        ProductDto tteokbokki = new ProductDto(2,
                "떡볶이",
                "https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg",
                20000);

        List<ProductDto> products = List.of(chicken, tteokbokki);

        mav.addObject("products", products);

        return mav;
    }

    @PostMapping("/admin")
    public void productSave(@RequestBody ProductRequest productRequest) {
        // TODO: dto 내부 값 validation
        productService.save(productRequest);
    }
}
