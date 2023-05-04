package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.domain.product.ProductEntity;
import cart.domain.product.ProductId;
import cart.dto.application.ProductDto;
import cart.dto.application.ProductEntityDto;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final Function<ProductEntity, ProductEntityDto> productEntityToProductEntityDto =
            product -> new ProductEntityDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl()
            );

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductEntityDto> findAll() {
        return productDao.findAll().stream()
                .map(productEntityToProductEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductEntityDto register(final ProductDto productDto) {
        final Product newProduct = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

        final long id = productDao.insert(newProduct);

        return new ProductEntityDto(id, productDto);
    }

    @Transactional
    public ProductEntityDto updateProduct(final ProductEntityDto productDto) {
        validateExistData(new ProductId(productDto.getId()));

        final ProductEntity newProduct = new ProductEntity(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImageUrl()
        );

        productDao.update(newProduct);

        return productDto;
    }

    @Transactional
    public void deleteProduct(final long id) {
        final ProductId productId = new ProductId(id);

        validateExistData(productId);

        productDao.delete(productId);
    }

    private void validateExistData(final ProductId productId) {
        if (!productDao.isExist(productId)) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
