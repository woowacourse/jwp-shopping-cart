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
        return productDao.update(id, productEntity);
    }

    @Transactional
    public int delete(final Long id) {
        return productDao.delete(id);
    }

    @Transactional(readOnly = true)
    public ProductEntity findById(final Long productId) {
        return productDao.findById(productId);
    }
}
