package cart.controller;


import cart.dto.UserResponseDto;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import cart.service.CartService;
import cart.service.ProductService;
import cart.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;

    public MainController(final ProductService productService,
                          final UserService userService, final CartService cartService) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String rootPage(final Model model) {
        final List<ProductEntity> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "index";
    }

    @GetMapping(value = "/cart", produces = MediaType.TEXT_HTML_VALUE)
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/settings")
    public String settingsPage(final Model model) {
        final List<UserResponseDto> userResponseDto = convertUsersToDto(userService.findAll());
        model.addAttribute("members", userResponseDto);
        return "settings";
    }

    private List<UserResponseDto> convertUsersToDto(final List<UserEntity> users) {
        return users.stream()
                .map(userEntity ->
                        new UserResponseDto(userEntity.getEmail(), userEntity.getPassword(), userEntity.getName()))
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/admin")
    public String adminPage(final Model model) {
        final List<ProductEntity> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "admin";
    }
}
