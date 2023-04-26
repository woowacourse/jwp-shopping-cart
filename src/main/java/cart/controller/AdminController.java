package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductDto;
import cart.dto.ProductModificationRequest;
import cart.dto.ProductResponse;
import cart.service.ProductManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        modelAndView.addObject("products", ProductResponse.from(managementService.findAll()));
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> postProducts(@RequestBody ProductCreationRequest request) {
        managementService.save(ProductDto.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/products")
    public ResponseEntity<Void> putProducts(@RequestBody ProductModificationRequest request) {
        System.out.println("request.getId() = " + request.getId());
        System.out.println("request.getName() = " + request.getName());

        managementService.update(ProductDto.from(request));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
