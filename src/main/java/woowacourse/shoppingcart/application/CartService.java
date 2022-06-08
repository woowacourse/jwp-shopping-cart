package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CartDto;
import woowacourse.shoppingcart.application.dto.EmailDto;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.repository.CartRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartRepository cartRepository;

    public CartService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartResponse> findCartsByCustomerEmail(final EmailDto emailDto) {
        final List<Cart> carts = cartRepository.findCartsByCustomerName(new Email(emailDto.getEmail()));
        return carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    public Long addCart(final CartDto cartDto, final EmailDto emailDto) {
        final Email email = new Email(emailDto.getEmail());
        return cartRepository.addCart(cartDto.getProductId(), cartDto.getQuantity(), email);
    }

    public void deleteCart(final EmailDto emailDto, final Long cartId) {
        validateCustomerCart(cartId, emailDto);
        cartRepository.deleteCart(cartId);
    }

    public boolean hasProduct(final EmailDto emailDto, final Long productId) {
        return cartRepository.hasProduct(new Email(emailDto.getEmail()), productId);
    }

    public void updateQuantity(final EmailDto emailDto, final CartDto cartDto) {
        cartRepository.updateQuantity(new Email(emailDto.getEmail()), cartDto.getProductId(), cartDto.getQuantity());
    }

    private void validateCustomerCart(final Long cartId, final EmailDto emailDto) {
        final List<Long> cartIds = cartRepository.findCartIdsByCustomerEmail(new Email(emailDto.getEmail()));
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
