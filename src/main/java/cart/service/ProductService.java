package cart.service;

import cart.dao.ProductsDao;
import cart.service.dto.ProductDto;
import cart.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductsDao productsDao;

    public ProductService(final ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    public Long insertProduct(final ProductDto productDto) {
        return productsDao.create(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    }

    public List<ProductDto> findAll() {
        return productsDao.readAll()
                .stream()
                .map(product ->
                        new ProductDto.Builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .imageUrl(product.getImageUrl())
                                .build()
                )
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateById(final ProductDto productDataToUpdate) {
        final Product product = productsDao.findById(productDataToUpdate.getId());
        productsDao.update(product,
                productDataToUpdate.getName(),
                productDataToUpdate.getPrice(),
                productDataToUpdate.getImageUrl()
        );
    }

    public void deleteById(final long id) {
        productsDao.delete(id);
    }
}
