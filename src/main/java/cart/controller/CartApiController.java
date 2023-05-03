package cart.controller;

import cart.dto.auth.AuthInfo;
import cart.dto.request.RequestCreateProductDto;
import cart.dto.request.RequestUpdateProductDto;
import cart.dto.response.ResponseProductDto;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final RequestCreateProductDto requestCreateProductDto) {
        cartService.insert(requestCreateProductDto);
        return ResponseEntity.created(URI.create("/product")).build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id, @RequestBody @Valid final RequestUpdateProductDto requestUpdateProductDto) {
        cartService.update(id, requestUpdateProductDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Valid final Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ResponseProductDto>> getProducts(final HttpServletRequest request) {
        BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo authIfo = basicAuthorizationExtractor.extract(request);
        List<ResponseProductDto> cartProductsByMember = cartService.findCartProductsByMember(authIfo);
        return ResponseEntity.ok().body(cartProductsByMember);
    }
}
