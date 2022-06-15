package woowacourse.shoppingcart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.notfound.ProductNotFoundException;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.infra.ProductRepository;
import woowacourse.shoppingcart.service.dto.ProductCreateServiceRequest;

@Transactional(readOnly = true)
@Service
public class SpringProductService implements ProductService {
    private final ProductRepository productRepository;

    public SpringProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public Product create(final ProductCreateServiceRequest productCreateServiceRequest) {
        return productRepository.save(productCreateServiceRequest.toProduct());
    }

    @Override
    public List<Product> findAllWithPage(final int page, final int size) {
        return productRepository.findAllWithPage(page, size);
    }

    @Override
    public Product findById(final long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    @Override
    public void deleteById(final long id) {
        productRepository.deleteById(id);
    }

    @Override
    public long countAll() {
        return productRepository.countAll();
    }
}
