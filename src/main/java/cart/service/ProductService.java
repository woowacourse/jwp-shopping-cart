package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import cart.dto.ResponseProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final int MINIMUM_AFFECTED_ROWS = 1;

    private final ProductDao productDao;

    @Autowired
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ResponseProductDto> findAll() {
        final List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(entity -> new ResponseProductDto(
                        entity.getId(),
                        entity.getName(),
                        entity.getPrice(),
                        entity.getImage())
                ).collect(Collectors.toUnmodifiableList());
    }

    public Long insert(final RequestCreateProductDto requestCreateProductDto) {
        final Product newProduct = new Product(
                requestCreateProductDto.getName(),
                requestCreateProductDto.getPrice(),
                requestCreateProductDto.getImage()
        );
        return productDao.insert(newProduct);
    }

    public int update(final RequestUpdateProductDto requestUpdateProductDto) {
        final Product product = new Product(
                requestUpdateProductDto.getName(),
                requestUpdateProductDto.getPrice(),
                requestUpdateProductDto.getImage()
        );
        final int updatedRows = productDao.update(product, requestUpdateProductDto.getId());
        validateAffectedRowsCount(updatedRows);
        return updatedRows;
    }

    private void validateAffectedRowsCount(final int affectedRows) {
        if (affectedRows < MINIMUM_AFFECTED_ROWS) {
            throw new IllegalArgumentException("접근하려는 데이터가 존재하지 않습니다.");
        }
    }

    public int delete(final Long id) {
        final int affectedRows = productDao.delete(id);
        validateAffectedRowsCount(affectedRows);
        return affectedRows;
    }
}
