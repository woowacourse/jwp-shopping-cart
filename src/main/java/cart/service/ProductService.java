package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.dto.ProductDto;
import cart.dto.mapper.DtoMapper;
import cart.dto.request.ProductAddRequest;
import cart.dto.request.ProductUpdateRequest;
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

    public long register(ProductAddRequest productAddRequest) {
        final Product inserted = productDao.insert(DtoMapper.toProduct(productAddRequest));
        return inserted.getId();
    }

    public ProductDto update(ProductUpdateRequest productUpdateRequest) {
        final Product product = DtoMapper.toProduct(productUpdateRequest);
        final int affectedRowsCount = productDao.update(product);
        validateAffectedRowSingle(affectedRowsCount, "존재하지 않는 상품 id입니다");
        return DtoMapper.toProductDto(product);
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
