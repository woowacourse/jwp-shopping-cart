package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.exception.custom.ResourceNotFoundException;
import cart.persistnece.dao.ProductDao;
import cart.persistnece.entity.Product;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        Long savedId = productDao.save(product);
        return savedId;
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당하는 id의 상품이 존재하지 않습니다."));
        return new ProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .collect(toList());
    }

    public void deleteById(Long id) {
        int deleteCount = productDao.deleteById(id);
        hasNoMatchingResult(deleteCount);
    }

    public void updateById(Long id, ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        int updateCount = productDao.updateById(id, product);
        hasNoMatchingResult(updateCount);
    }

    private void hasNoMatchingResult(int count) {
        if (count == 0) {
            throw new ResourceNotFoundException("해당하는 id의 상품이 존재하지 않습니다.");
        }
    }
}
