package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartDto;
import woowacourse.shoppingcart.dto.CartUpdationRequest;
import woowacourse.shoppingcart.exception.IllegalProductException;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartService(final CartItemDao cartItemDao, ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public void addCart(final Long productId, final Customer customer) {
        boolean isExist = cartItemDao.existProduct(customer.getId(), productId);
        if (isExist) {
            throw new IllegalProductException("중복된 물품입니다.");
        }
        try {
            Product product = productService.findProductById(productId);
            cartItemDao.addCartItem(customer.getId(), product.getId());
        } catch (NotFoundProductException exception) {
            throw new IllegalProductException("물품이 존재하지 않습니다.");
        }
    }

    public List<Cart> getCarts(Customer customer) {
        List<Long> cartIds = cartItemDao.findIdsByCustomerId(customer.getId());

        List<CartDto> cartDtos = cartItemDao.getCartinfosByIds(cartIds);

        List<Long> productIds = cartDtos.stream()
                .map(cartDto -> cartDto.getProductId())
                .collect(Collectors.toList());

        List<Product> products = productService.getProductsByIds(productIds);

        List<Integer> quantities = cartDtos.stream()
                .map(CartDto::getQuantity)
                .collect(Collectors.toList());

        List<Cart> carts = new ArrayList<>();
        for (int i = 0; i < cartIds.size(); i++) {
            carts.add(new Cart(cartIds.get(i), products.get(i), quantities.get(i)));
        }
        return carts;
    }

    public void deleteProductInCart(Customer customer, Long productId) {
        boolean isExist = cartItemDao.existProduct(customer.getId(), productId);
        if (!isExist) {
            throw new IllegalProductException("장바구니에 상품이 존재하지 않습니다.");
        }

        cartItemDao.deleteCartItem(customer.getId(), productId);
    }

    public Cart updateProductInCart(Customer customer, CartUpdationRequest request, Long productId) {
        boolean isExist = cartItemDao.existProduct(customer.getId(), productId);
        if (!isExist) {
            throw new IllegalProductException("장바구니에 상품이 존재하지 않습니다.");
        }
        cartItemDao.updateCartItem(customer.getId(), request.getQuantity(), productId);
        CartDto cartDto = cartItemDao.findCartByProductCustomer(customer.getId(), productId);
        Product product = productService.findProductById(cartDto.getProductId());
        return new Cart(cartDto.getCartId(), product, cartDto.getQuantity());
    }
}
