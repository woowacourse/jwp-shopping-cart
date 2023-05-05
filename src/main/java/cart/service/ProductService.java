package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.domain.product.ProductEntity;
import cart.domain.product.ProductId;
import cart.dto.application.ProductDto;
import cart.dto.application.ProductEntityDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<ProductEntityDto> findAll() {
        return productDao.findAll().stream()
                .map(productEntityToProductEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductEntityDto find(final long id) {
        final ProductId productId = new ProductId(id);

        final ProductEntity result = productDao.find(productId);

        return new ProductEntityDto(
                result.getId(),
                result.getName(),
                result.getPrice(),
                result.getImageUrl()
        );
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
