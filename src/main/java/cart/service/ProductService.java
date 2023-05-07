package cart.service;

import cart.dao.ProductDao;
import cart.dto.entity.ProductEntity;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.exception.ErrorCode;
import cart.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public static final int ZERO = 0;

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int save(ProductRequest productRequest) {
        ProductEntity productEntity = new ProductEntity(
                productRequest.getName(),
                productRequest.getImgUrl(),
                productRequest.getPrice());
        return productDao.save(productEntity);
    }

    public List<ProductResponse> findAll() {
        List<ProductEntity> products = productDao.findAll();

        return products.stream()
                .map(productEntity -> new ProductResponse(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getImgUrl(),
                        productEntity.getPrice())
                )
                .collect(Collectors.toList());
    }

    public void update(ProductRequest productRequest, int id) {
        ProductEntity productEntity = new ProductEntity(
                id,
                productRequest.getName(),
                productRequest.getImgUrl(),
                productRequest.getPrice());
        int updateRowNumber = productDao.update(productEntity);
        if (updateRowNumber == ZERO) {
            throw new NotFoundException(ErrorCode.ID_NOT_FOUND);
        }
    }

    public void delete(int id) {
        int deleteRowNumber = productDao.delete(id);
        if (deleteRowNumber == ZERO) {
            throw new NotFoundException(ErrorCode.ID_NOT_FOUND);
        }
    }

    public ProductEntity findById(int productId) {
        List<ProductEntity> product = productDao.findById(productId);
        if (product.size() == ZERO) {
            throw new NotFoundException(ErrorCode.ID_NOT_FOUND);
        }
        return product.get(0);
    }
}
