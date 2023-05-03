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
import java.util.Optional;
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

    public void validateProductExist(long id) {
        final Optional<Product> optionalProduct = productDao.findById(id);

        optionalProduct.orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다"));
    }

    public ProductDto getById(long id) {
        final Optional<Product> optionalProduct = productDao.findById(id);

        final Product foundProduct = optionalProduct.orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다"));

        return DtoMapper.toProductDto(foundProduct);
    }

    public long register(ProductAddRequest productAddRequest) {
        final Product inserted = productDao.save(DtoMapper.toValidProduct(productAddRequest));
        return inserted.getId();
    }

    public long update(ProductUpdateRequest productUpdateRequest) {
        final Product product = DtoMapper.toValidProduct(productUpdateRequest);
        final int affectedRowsCount = productDao.update(product);
        validateAffectedRowSingle(affectedRowsCount, "존재하지 않는 상품 id입니다");
        return product.getId();
    }

    public long delete(long id) {
        final int affectedRowsCount = productDao.deleteById(id);
        validateAffectedRowSingle(affectedRowsCount, "존재하지 않는 상품 id입니다");
        return id;
    }

    private void validateAffectedRowSingle(final int affectedRowsCount, String message) {
        if (affectedRowsCount != 1) {
            throw new NoSuchElementException(message);
        }
    }
}
