package cart.service;

import cart.domain.product.Product;
import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.ProductResponse;
import cart.persistence.ProductsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsService {

    private final ProductsDao productsDao;

    public ProductsService(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    public ProductResponse create(ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl()
        );

        Long createdId = productsDao.create(product);
        Product created = productsDao.findById(createdId);

        return new ProductResponse(
                created.getId(),
                created.getName(),
                created.getPrice(),
                created.getImageUrl());
    }

    public List<ProductResponse> readAll() {
        List<Product> products = productsDao.findAll();
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
                productUpdateRequest.getPrice(),
                productUpdateRequest.getImageUrl()
        );

        Long updatedId = productsDao.update(product);
        Product updated = productsDao.findById(updatedId);

        return new ProductResponse(
                updated.getId(),
                updated.getName(),
                updated.getPrice(),
                updated.getImageUrl()
        );
    }

    public void delete(Long id) {
        productsDao.delete(id);
    }
}
