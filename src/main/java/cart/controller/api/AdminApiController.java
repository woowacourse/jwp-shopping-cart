package cart.controller.api;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD:src/main/java/cart/controller/api/AdminApiController.java
import org.springframework.http.ResponseEntity;
=======
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
>>>>>>> 640c9e43 (refactor: 상품을 생성한다는 의미를 url에 포함):src/main/java/cart/controller/AdminController.java
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD:src/main/java/cart/controller/api/AdminApiController.java
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.ResponseStatus;
>>>>>>> 640c9e43 (refactor: 상품을 생성한다는 의미를 url에 포함):src/main/java/cart/controller/AdminController.java

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminApiController {

    private final ProductService productService;

    public AdminApiController(final ProductService productService) {
        this.productService = productService;
    }

<<<<<<< HEAD:src/main/java/cart/controller/api/AdminApiController.java
    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductCreateRequest request) {
=======
    @GetMapping("/products")
    public String admin(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody @Valid final ProductCreateRequest request) {
>>>>>>> 640c9e43 (refactor: 상품을 생성한다는 의미를 url에 포함):src/main/java/cart/controller/AdminController.java
        productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

<<<<<<< HEAD:src/main/java/cart/controller/api/AdminApiController.java
    @PutMapping("/product/{id}")
    public ResponseEntity<Void> update(
=======
    @PutMapping("/{id}")
    public String update(
>>>>>>> 640c9e43 (refactor: 상품을 생성한다는 의미를 url에 포함):src/main/java/cart/controller/AdminController.java
            @PathVariable final Long id,
            @RequestBody @Valid final ProductUpdateRequest request
    ) {
        productService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id
    ) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}
