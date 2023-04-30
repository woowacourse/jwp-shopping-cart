package cart.controller;

import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.JwpCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @PostMapping("/admin/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = jwpCartService.add(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/admin/products/" + productResponseDto.getId()))
                .body(productResponseDto);
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = jwpCartService.updateById(productRequestDto, id);
        return ResponseEntity.ok().body(productResponseDto);
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        jwpCartService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
