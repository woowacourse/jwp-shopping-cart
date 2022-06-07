package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartAddRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;

    @Test
    void findCartsByLoginCustomer() {
        //given
        Long bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        Long appleId = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));

        LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);
        Cart bananaCart = cartService.addCart(loginCustomer, new CartAddRequest(bananaId));
        Cart appleCart = cartService.addCart(loginCustomer, new CartAddRequest(appleId));

        //when
        List<Cart> carts = cartService.findCartsByLoginCustomer(loginCustomer);

        //then
        assertThat(carts).hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(List.of(bananaCart, appleCart));
    }

    @Test
    void addCart() {
        Long bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        Long appleId = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));

        LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);
        Cart cart = cartService.addCart(loginCustomer, new CartAddRequest(bananaId));

        assertThat(cart.getName()).isEqualTo("banana");
    }

}
