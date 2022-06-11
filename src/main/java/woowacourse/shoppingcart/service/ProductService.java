package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequestDto;
import woowacourse.shoppingcart.dto.response.ProductResponseDto;
import woowacourse.shoppingcart.exception.NotFoundProductException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponseDto> findProducts() {
        return productDao.findProducts().stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getThumbnailUrl(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity()))
                .collect(Collectors.toList());
    }

    public Long addProduct(@Validated final ProductRequestDto productRequestDto) {
        return productDao.save(
                new Product(
                        productRequestDto.getName(),
                        productRequestDto.getPrice(),
                        productRequestDto.getThumbnailUrl(),
                        productRequestDto.getQuantity())
        );
    }

    public ProductResponseDto findProductById(final Long productId) {
        Product product = productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
        return new ProductResponseDto(
                product.getId(),
                product.getThumbnailUrl(),
                product.getName(),
                product.getPrice(),
                product.getQuantity());
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }

    public int addProducts(List<ProductRequestDto> productDtos) {
        List<Product> products = productDtos.stream()
                .map(dto -> new Product(dto.getName(), dto.getPrice(), dto.getThumbnailUrl(), dto.getQuantity()))
                .collect(Collectors.toList());
        return productDao.saveAll(products);
    }
}
