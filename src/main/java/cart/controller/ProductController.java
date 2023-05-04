package cart.controller;

import cart.controller.dto.ModifyRequest;
import cart.dao.ProductDao;
import cart.domain.product.Product;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody final ModifyRequest modifyRequest) {
        Product product = Product.createWithoutId(
                modifyRequest.getName(),
                modifyRequest.getPrice(),
                modifyRequest.getImageUrl()
        );
        productDao.save(product);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody final ModifyRequest modifyRequest,
                                              @PathVariable Long id,
                                              final HttpServletResponse response) {
        final Product product = Product.create(id, modifyRequest.getName(), modifyRequest.getPrice(),
                modifyRequest.getImageUrl());
        productDao.update(product);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productDao.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
