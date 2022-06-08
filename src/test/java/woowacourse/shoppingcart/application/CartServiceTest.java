package woowacourse.shoppingcart.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CartServiceTest {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    CartService cartService;

    @DisplayName("이미 카트에 추가된 상품을 다시 추가하려는 경우 예외를 던진다.")
    @Test
    void throwExceptionWhenAddDuplicateItem() {
        String email = "awesomeo@gmail.com";
        Long customerId = customerDao.save(new Customer(email, "awesome", "Password123!"));
        Long productId = productDao.save(new Product("치킨", 10_000, "imageUrl"));

        cartService.addCart(email, productId);

        assertThatThrownBy(() -> cartService.addCart(email, productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 추가된 상품입니다.");
    }
}
