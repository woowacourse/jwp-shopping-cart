package cart.service;

import cart.dao.ProductDao;
import cart.domain.ProductEntity;
import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import cart.dto.ResponseProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final int MINIMUM_AFFECTED_ROWS = 1;

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ResponseProductDto> findAll() {
        final List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(ResponseProductDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public Long insert(final RequestCreateProductDto requestCreateProductDto) {
        final ProductEntity productEntity = requestCreateProductDto.toProductEntity();
        return productDao.insert(productEntity);
    }

    @Transactional
    public int update(final Long id, final RequestUpdateProductDto requestUpdateProductDto) {
        final ProductEntity productEntity = requestUpdateProductDto.toProductEntity();
        final int updatedRows = productDao.update(id, productEntity);
        validateAffectedRowsCount(updatedRows);
        return updatedRows;
    }

    private void validateAffectedRowsCount(final int affectedRows) {
        if (affectedRows < MINIMUM_AFFECTED_ROWS) {
            throw new IllegalArgumentException("접근하려는 데이터가 존재하지 않습니다.");
        }
    }

    @Transactional
    public int delete(final Long id) {
        final int affectedRows = productDao.delete(id);
        validateAffectedRowsCount(affectedRows);
        return affectedRows;
    }

    @Transactional(readOnly = true)
    public List<ResponseProductDto> findByIds(final List<Long> productIds) {
        return productIds.stream()
                .map(productDao::findById)
                .map(ResponseProductDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public ProductEntity findById(final Long productId) {
        return productDao.findById(productId);
    }
}
