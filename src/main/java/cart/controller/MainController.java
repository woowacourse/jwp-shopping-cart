package cart.controller;


import cart.dto.UserResponseDto;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import cart.service.ProductService;
import cart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public MainController(final ProductService productService, final UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String rootPage(Model model) {
        final List<ProductEntity> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "index";
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
