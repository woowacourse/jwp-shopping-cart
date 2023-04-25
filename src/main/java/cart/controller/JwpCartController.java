package cart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.JwpCartService;

@Controller
public class JwpCartController {

    private final JwpCartService jwpCartService;

    public JwpCartController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponseDto> all = jwpCartService.findAll();
        model.addAttribute("products", all);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponseDto> all = jwpCartService.findAll();
        model.addAttribute("products", all);
        return "admin";
    }

    @PostMapping("/admin/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addProduct(@RequestBody ProductRequestDto productRequestDto) {
        jwpCartService.add(productRequestDto);
        return "redirect:/admin";
    }

    @PutMapping("/admin/add/{id}")
    public String updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDto productRequestDto) {
        jwpCartService.updateById(productRequestDto, id);
        return "/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        jwpCartService.deleteById(id);
        return "/admin";
    }

}
