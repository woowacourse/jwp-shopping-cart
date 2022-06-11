package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartAddRequest;
import woowacourse.shoppingcart.dto.CartUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerAddRequest;

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
    @DisplayName("로그인한 사용자의 장바구니 내역을 불러올 수 있다.")
    void findCartsByLoginCustomer() {
        //given
        Long bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        Long appleId = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));

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
    @DisplayName("장바구니에 물건을 추가할 수 있다.")
    void addCart() {
        //given
        Long bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
        LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);

        //when
        Cart cart = cartService.addCart(loginCustomer, new CartAddRequest(bananaId));

        //then
        assertThat(cart.getName()).isEqualTo("banana");
    }

    @Test
    @DisplayName("장바구니 물건을 전체 삭제할 수 있다.")
    void deleteAllCart() {
        //given
        Long bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        Long appleId = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));

        LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);
        cartService.addCart(loginCustomer, new CartAddRequest(bananaId));
        cartService.addCart(loginCustomer, new CartAddRequest(appleId));

        //when
        cartService.deleteAllCart(loginCustomer);

        //then
        List<Cart> carts = cartService.findCartsByLoginCustomer(loginCustomer);
        Assertions.assertThat(carts).isEmpty();
    }

    @Test
    @DisplayName("장바구니 물품 개수를 수정할 수 있다.")
    void updateQuantity() {
        //given
        Long bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));

        customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));

        LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);
        Cart cart = cartService.addCart(loginCustomer, new CartAddRequest(bananaId));

        //when
        Cart updateCart = cartService.updateQuantity(loginCustomer, new CartUpdateRequest(5), cart.getId());

        //then
        assertThat(updateCart.getQuantity()).isEqualTo(5);
    }
}
