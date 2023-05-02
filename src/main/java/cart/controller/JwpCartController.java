package cart.controller;

import cart.dto.ProductDto;
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

import static java.util.stream.Collectors.toList;

@Controller
public class JwpCartController {

    private final JwpCartService jwpCartService;

    public JwpCartController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductDto> productDtos = jwpCartService.findAll();
        List<ProductResponseDto> response = productDtos.stream()
                .map(ProductResponseDto::fromProductDto)
                .collect(toList());
        model.addAttribute("products", response);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> productDtos = jwpCartService.findAll();
        List<ProductResponseDto> response = productDtos.stream()
                .map(ProductResponseDto::fromProductDto)
                .collect(toList());
        model.addAttribute("products", response);
        return "admin";
    }

    @PostMapping("/admin/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDto productdto = jwpCartService.add(productRequestDto);
        ProductResponseDto response = ProductResponseDto.fromProductDto(productdto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/admin/products/" + response.getId()))
                .body(response);
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDto productDto = jwpCartService.updateById(productRequestDto, id);
        ProductResponseDto response = ProductResponseDto.fromProductDto(productDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        jwpCartService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
