package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidInputException;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse findById(final Long productId) {
        Product product = productRepository.findById(productId);
        return ProductResponse.of(product);
    }

    public List<ProductResponse> findProductsOfPage(final int page, final int limit) {
        List<Product> products = productRepository.findAll();
        int totalProductsCount = products.size();
        int totalPageCount = totalProductsCount / limit + 1;
        validatePage(page, totalPageCount);
        return getProductResponses(page, limit, products, totalProductsCount, totalPageCount);



    }

    private void validatePage(final int page, final int pageCount) {
        if (page > pageCount) {
            throw new InvalidInputException("존재하지 않는 페이지 입니다.");
        }
    }

    private List<ProductResponse> getProductResponses(final int page,
                                                      final int limit,
                                                      final List<Product> products,
                                                      final int totalProductsCount,
                                                      final int totalPageCount) {
        if (page == totalPageCount) {
            return getProductResponses((page - 1) * limit, totalProductsCount, products);
        }
        return getProductResponses((page - 1) * limit, page * limit, products);
    }

    private List<ProductResponse> getProductResponses(final int fromIndex,
                                                      final int toIndex,
                                                      final List<Product> products) {
        return products.subList(fromIndex, toIndex).stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
