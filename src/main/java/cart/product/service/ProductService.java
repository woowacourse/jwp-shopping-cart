package cart.product.service;

import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import cart.product.dto.ProductDto;
import cart.product.dto.mapper.DtoMapper;
import cart.product.dto.request.ProductAddRequest;
import cart.product.dto.request.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> getAllProducts() {
        return productDao.findAll()
                         .stream()
                         .map(DtoMapper::toProductDto)
                         .collect(Collectors.toUnmodifiableList());
    }

    public ProductDto getProductById(long productId) {
        final Product foundProduct = findProductOrThrow(productId);

        return DtoMapper.toProductDto(foundProduct);
    }

    public long register(ProductAddRequest productAddRequest) {
        final Product validProduct = DtoMapper.toValidProduct(productAddRequest);
        final Product inserted = productDao.save(validProduct);

        return inserted.getId();
    }

    public long update(ProductUpdateRequest productUpdateRequest) {
        findProductOrThrow(productUpdateRequest.getId());

        final Product product = DtoMapper.toValidProduct(productUpdateRequest);
        productDao.update(product);

        return product.getId();
    }

    public long delete(long productId) {
        findProductOrThrow(productId);

        productDao.deleteById(productId);

        return productId;
    }

    public void validateProductExist(long productId) {
        productDao.findById(productId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다"));
    }

    private Product findProductOrThrow(long productId) {
        return productDao.findById(productId)
                         .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다"));
    }
}
