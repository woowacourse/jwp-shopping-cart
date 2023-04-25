package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductRequest;
import cart.service.ProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<String> productAdd(@RequestBody ProductRequest productRequest) {
        // TODO: dto 내부 값 validation
        productService.save(productRequest);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ModelAndView productList() {
        ModelAndView mav = new ModelAndView("/admin");
        List<ProductDto> products = productService.findAll();
        System.out.println(products);
        mav.addObject("products", products);
        return mav;
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<String> productModify(@RequestBody ProductRequest productRequest, @PathVariable int id) {
        productService.update(productRequest, id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> productRemove(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
