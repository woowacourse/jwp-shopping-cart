package cart.service;

import cart.domain.Product;
import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.ProductResponse;
import cart.persistence.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductResponse create(ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice()
        );

        Long createdId = productDao.create(product);
        Product created = productDao.find(createdId);

        return new ProductResponse(
                created.getId(),
                created.getName(),
                created.getPrice(),
                created.getImageUrl());
    }

    public List<ProductResponse> readAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public ProductResponse update(ProductUpdateRequest productUpdateRequest) {
        Product product = new Product(
                productUpdateRequest.getId(),
                productUpdateRequest.getName(),
                productUpdateRequest.getImageUrl(),
                productUpdateRequest.getPrice()
        );

        Long updatedId = productDao.update(product);
        Product updated = productDao.find(updatedId);

        return new ProductResponse(
                updated.getId(),
                updated.getName(),
                updated.getPrice(),
                updated.getImageUrl()
        );
    }

    public void delete(Long id) {
        productDao.delete(id);
    }
}
