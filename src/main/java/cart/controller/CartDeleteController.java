package cart.controller;

import cart.service.CartDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartDeleteController {

    private final CartDeleteService cartDeleteService;

    public CartDeleteController(final CartDeleteService cartDeleteService) {
        this.cartDeleteService = cartDeleteService;
    }

    @DeleteMapping("carts/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable final long id) {
        cartDeleteService.delete(id);
    }
}
