package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.common.exception.NotFoundException;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.request.CartRequest;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.repository.CartItemRepository;
import woowacourse.shoppingcart.repository.CustomerRepository;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(CustomerRepository customerRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public CartResponse findCartsByCustomerId(Long customerId) {
        List<ProductResponse> products = productRepository.findProductsByCartByCustomerId(customerId)
                .stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
        return new CartResponse(products);
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl());
    }

    public Long addCart(final CartRequest request, final Long customerId) {
        Customer customer = getCustomer(customerId);
        Product product = getProduct(request);
        return cartItemRepository.save(new CartItem(customer, product));
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

    private Product getProduct(CartRequest request) {
        return productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다."));
    }

    public void deleteCart(Long cartId, Long customerId) {
        validateCustomerCart(cartId, customerId);
        cartItemRepository.deleteCartItem(cartId);
    }

    private void validateCustomerCart(Long cartId, Long customerId) {
        List<Long> cartIds = cartItemRepository.findIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
