package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductDto;
import cart.dto.ProductModificationRequest;
import cart.dto.ProductResponse;
import cart.mapper.ProductDtoMapper;
import cart.mapper.ProductResponseMapper;
import cart.service.ProductManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductManagementService managementService;

    public AdminController(final ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponseMapper.from(managementService.findAll()));
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> postProducts(@RequestBody ProductCreationRequest request) {
        managementService.save(ProductDtoMapper.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> putProducts(@PathVariable Long id,
                                            @RequestBody ProductModificationRequest request) {
        managementService.update(ProductDtoMapper.from(request));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id) {
        managementService.delete(ProductDto.from(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
