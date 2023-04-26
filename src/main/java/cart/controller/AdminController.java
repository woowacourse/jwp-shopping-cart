package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductDto;
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
    @ResponseBody
    public ResponseEntity<Void> postProducts(@RequestBody ProductCreationRequest request) {
        managementService.save(ProductDto.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
