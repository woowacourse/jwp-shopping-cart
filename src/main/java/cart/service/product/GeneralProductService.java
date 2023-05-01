package cart.service.product;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.product.ProductRepository;
import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class GeneralProductService implements ProductService {
    private final ProductRepository productRepository;

    public GeneralProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId().getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImage()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProductId save(final ProductCreateRequest request) {
        final Product product = new Product(request.getName(), request.getPrice(), request.getImage());
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public ProductResponse update(final ProductId productId, final ProductUpdateRequest request) {
        final ProductId updateProductId = productRepository.updateByProductId(productId, request);
        final Product findProduct = productRepository.findByProductId(updateProductId)
                .orElseThrow(() -> new IllegalStateException("갱신된 상품 조회에 실패했습니다."));

        return new ProductResponse(
                findProduct.getId().getId(),
                findProduct.getName(),
                findProduct.getPrice(),
                findProduct.getImage()
        );
    }

    @Transactional
    @Override
    public ProductId deleteByProductId(final ProductId productId) {
        return productRepository.deleteByProductId(productId);
    }
}
