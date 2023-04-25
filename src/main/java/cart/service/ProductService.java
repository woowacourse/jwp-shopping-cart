package cart.service;

import cart.dao.ProductsDao;
import cart.dto.ProductDto;
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
        productsDao.create(productDto.getName(), productDto.getPrice(), productDto.getImage());
    }

    public List<ProductDto> findAll() {
        return productsDao.readAll()
                .stream()
                .map(product ->
                        ProductDto.createProductDto(product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getImage()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateById(final ProductDto productDto) {
        productsDao.update(new Product(productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImage()));
    }

    public void deleteById(final long id) {
        productsDao.delete(id);
    }
}
