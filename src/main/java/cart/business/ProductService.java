package cart.business;

import cart.entity.Product;
import cart.persistence.ProductRepository;
import cart.presentation.dto.ProductRequest;
import cart.presentation.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository jdbcTemplateRepository;

    public ProductService(ProductRepository jdbcTemplateRepository) {
        this.jdbcTemplateRepository = jdbcTemplateRepository;
    }

    @Transactional
    public Integer create(ProductRequest request) {
        Product product = makeProductFromRequest(request);
        jdbcTemplateRepository.findSameProductExist(product);

        return jdbcTemplateRepository.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> read() {
        return jdbcTemplateRepository.findAll();
    }

    @Transactional
    public Integer update(Integer id, ProductRequest request) {
        Product product = makeProductFromRequest(request);
        return jdbcTemplateRepository.update(id, product);
    }

    @Transactional
    public Integer delete(Integer id) {
        return jdbcTemplateRepository.remove(id);
    }

    private Product makeProductFromRequest(ProductRequest request) {
        return new Product(null, request.getName(), request.getUrl(), request.getPrice());
    }

    private Product makeProductFromResponse(ProductResponse response) {
        return new Product(
                response.getId(), (response.getName()), response.getUrl(), response.getPrice());
    }
}
