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

    public void insertProduct(final ProductDto productDto) {
        productsDao.create(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    }

    public List<ProductDto> findAll() {
        return productsDao.readAll()
                .stream()
                .map(product ->
                        new ProductDto.Builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .imageUrl(product.getImage())
                                .build()
                )
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateById(final ProductDto productDto) {
        productsDao.update(new Product(productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImageUrl()));
    }

    public void deleteById(final long id) {
        productsDao.delete(id);
    }
}
