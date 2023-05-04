package cart.controller;

import cart.controller.dto.ProductSaveRequest;
import cart.controller.dto.ProductUpdateRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody final ProductSaveRequest productSaveRequest) {
        Product product = Product.createWithoutId(
                productSaveRequest.getName(),
                productSaveRequest.getPrice(),
                productSaveRequest.getImageUrl()
        );
        productDao.save(product);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody final ProductUpdateRequest productUpdateRequest,
                                              @PathVariable Long id,
                                              final HttpServletResponse response) {
        final Product product = Product.create(id, productUpdateRequest.getName(), productUpdateRequest.getPrice(),
                productUpdateRequest.getImageUrl());
        productDao.update(product);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productDao.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
