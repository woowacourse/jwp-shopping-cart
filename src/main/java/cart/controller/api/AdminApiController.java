package cart.controller.api;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD:src/main/java/cart/controller/api/AdminApiController.java
import org.springframework.http.ResponseEntity;
=======
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
>>>>>>> 640c9e43 (refactor: 상품을 생성한다는 의미를 url에 포함):src/main/java/cart/controller/AdminController.java
=======
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
=======
import org.springframework.http.ResponseEntity;
>>>>>>> 365da9ae (refactor: 반환 값에 상태코드 포함)
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD:src/main/java/cart/controller/api/AdminApiController.java
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.ResponseStatus;
>>>>>>> 640c9e43 (refactor: 상품을 생성한다는 의미를 url에 포함):src/main/java/cart/controller/AdminController.java
=======
import org.springframework.web.bind.annotation.ResponseStatus;
=======
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
import org.springframework.web.bind.annotation.RestController;
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminApiController {

    private final ProductService productService;

    public AdminApiController(final ProductService productService) {
        this.productService = productService;
    }

<<<<<<< HEAD
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
=======
    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductCreateRequest request) {
        productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/product/{id}")
<<<<<<< HEAD
    public void update(
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
=======
    public ResponseEntity<Void> update(
>>>>>>> 365da9ae (refactor: 반환 값에 상태코드 포함)
            @PathVariable final Long id,
            @RequestBody @Valid final ProductUpdateRequest request
    ) {
        productService.update(id, request);
<<<<<<< HEAD
<<<<<<< HEAD
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id
    ) {
<<<<<<< HEAD
        productService.delete(id);
        return ResponseEntity.ok().build();
=======
=======
        return ResponseEntity.ok().build();
>>>>>>> 365da9ae (refactor: 반환 값에 상태코드 포함)
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
=======
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
        productService.delete(id);
<<<<<<< HEAD
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
=======
        return ResponseEntity.ok().build();
>>>>>>> 365da9ae (refactor: 반환 값에 상태코드 포함)
    }

}
