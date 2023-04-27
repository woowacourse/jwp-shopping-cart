package cart.controller;

import cart.dto.request.RequestCreateProductDto;
import cart.dto.request.RequestUpdateProductDto;
import cart.dto.response.ResponseProductDto;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CartService cartService;

    @Autowired
    public AdminController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String readProducts(final Model model) {
        final List<ResponseProductDto> responseProductDtos = cartService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final RequestCreateProductDto requestCreateProductDto) {
        cartService.insert(requestCreateProductDto);
        return ResponseEntity.created(URI.create("/product")).build();
    }

    @PutMapping("/product")
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid final RequestUpdateProductDto requestUpdateProductDto) {
        cartService.update(requestUpdateProductDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
