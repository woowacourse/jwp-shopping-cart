//package cart.controller;
//
//import cart.controller.auth.BasicAuthentication;
//import cart.persistance.dao.CartDao;
//import cart.domain.user.Member;
//import org.springframework.http.MediaType;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.awt.*;
//
//@RestController
//public class CartController {
//
//    private final CartDao cartDao;
//
//    public CartController(final CartDao cartDao) {
//        this.cartDao = cartDao;
//    }
//
//    @GetMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<> cartProducts(
//            @RequestBody final long productId,
//            @BasicAuthentication final Member member,
//            final Model model
//    ) {
//        return "cart";
//    }
//}
