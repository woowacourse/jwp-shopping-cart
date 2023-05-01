package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    @GetMapping("/carts")
    public void findAll() {

    }

    @PostMapping("/carts/{productId}")
    public void add() {

    }

    @DeleteMapping("/carts/{productId}")
    public void delete() {

    }
}
