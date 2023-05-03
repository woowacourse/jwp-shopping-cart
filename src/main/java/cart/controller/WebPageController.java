package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.controller.dto.ProductResponse;
import cart.dao.UserDao;
import cart.dao.dto.UserDto;
import cart.domain.Product;
import cart.domain.User;
import cart.repository.ProductRepository;

@Controller
public class WebPageController {

    private final ProductRepository productRepository;
    private final UserDao userDao;

    public WebPageController(final ProductRepository productRepository, final UserDao userDao) {
        this.productRepository = productRepository;
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String renderStartPage(final Model model) {
        model.addAttribute("products", mapProducts(productRepository.getAll()));
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", mapProducts(productRepository.getAll()));
        return "admin";
    }

    @GetMapping("/settings")
    public String renderSettingsPage(final Model model) {
        List<UserDto> userDtos = userDao.selectAll();
        List<User> users = userDtos.stream()
                .map(userDto -> new User(
                        userDto.getId(),
                        userDto.getEmail(),
                        userDto.getPassword())
                )
                .collect(Collectors.toList());

        model.addAttribute("members", mapUsers(users));
        return "settings";
    }

    private List<ProductResponse> mapProducts(List<Product> products) {
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getImage(),
                        product.getPrice())
                ).collect(Collectors.toList());
    }

    private List<UserResponse> mapUsers(List<User> users) {
        return users.stream()
                .map(user -> new UserResponse(
                        user.getEmail(),
                        user.getPassword())
                ).collect(Collectors.toList());
    }
}
