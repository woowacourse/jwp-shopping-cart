package cart.controller;

import cart.controller.dto.ModifyRequest;
import cart.dao.ProductDao;
import cart.domain.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public final class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> addProduct(
            @Valid @RequestBody final ModifyRequest modifyRequest
    ) throws URISyntaxException {
        final Product product = Product.createWithoutId(
                modifyRequest.getName(),
                modifyRequest.getPrice(),
                modifyRequest.getImageUrl()
        );
        final long id = productDao.add(product);
        return ResponseEntity.created(new URI(String.format("/product/%d", id))).build();
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(
            @Valid @RequestBody final ModifyRequest modifyRequest,
            @PathVariable final Long id
    ) throws URISyntaxException {
        final Product product = Product.create(
                id,
                modifyRequest.getName(),
                modifyRequest.getPrice(),
                modifyRequest.getImageUrl()
        );
        productDao.update(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
