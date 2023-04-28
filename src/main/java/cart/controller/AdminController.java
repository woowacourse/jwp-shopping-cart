package cart.controller;

import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CartService cartService;

    public AdminController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String admin(Model model) {
        List<ProductResponse> productsResponse = cartService.readAll();
        model.addAttribute("products", productsResponse);
        return "admin";
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = cartService.create(productRequest);

        URI createdUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productResponse.getId())
                .toUri();

        return ResponseEntity.created(createdUri).body(productResponse);
    }

    @PutMapping("/product")
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        ProductResponse productResponse = cartService.update(productUpdateRequest);

        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("/product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam @NotNull Long id) {
        cartService.delete(id);
    }
}
