package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long save(ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        Long savedId = productDao.save(product);
        return savedId;
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productDao.findById(id);
        return new ProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .collect(toList());
    }

    @Transactional
    public void deleteById(Long id) {
        int deleteCount = productDao.deleteById(id);
        hasNoMatchingResult(deleteCount);
    }

    @Transactional
    public void updateById(Long id, ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        int updateCount = productDao.updateById(id, product);
        hasNoMatchingResult(updateCount);
    }

    private void hasNoMatchingResult(int count) {
        if (count == 0) {
            throw new NoSuchElementException("존재하지 않는 상품입니다.");
        }
    }
}
