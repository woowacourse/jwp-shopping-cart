package cart.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import cart.dto.AuthInfo;
import cart.dto.CartRequest;
import cart.dto.ProductResponse;
import cart.exception.ExceptionCode;
import cart.infra.AuthorizationExtractor;
import cart.infra.CartRequestExtractor;
import cart.service.JwpCartService;

@Controller
public class HomeController {
    private final JwpCartService jwpCartService;

    public HomeController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> all = jwpCartService.findAllProducts();
        model.addAttribute("products", all);
        return "index";
    }

    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addToCart(HttpServletRequest httpServletRequest) throws IOException {
        AuthInfo authInfo = AuthorizationExtractor.extract(httpServletRequest);
        if (authInfo == null) {
            return ResponseEntity.badRequest().body(ExceptionCode.NO_AUTHORIZATION_HEADER);
        }

        CartRequest cartRequest = CartRequestExtractor.extract(httpServletRequest);

        jwpCartService.addProductToCart(authInfo, cartRequest);
        return ResponseEntity.created(URI.create("/carts")).build();
    }
}
