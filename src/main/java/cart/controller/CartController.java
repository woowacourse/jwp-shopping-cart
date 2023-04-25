package cart.controller;


import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {

    @GetMapping("/")
    public String showProductsList(Model model){
        model.addAttribute("products", List.of(new Product(1L,"name","https://blog.kakaocdn.net/dn/bezjux/btqCX8fuOPX/6uq138en4osoKRq9rtbEG0/img.jpg",1000)));
        return "index";
    }
}
