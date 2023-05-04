package cart.dao;

import cart.entity.Cart;
import cart.vo.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void beforeEach() {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("데이터 베이스에 Cart를 저장이 잘 되는지 확인한다.")
    void save() {
        Email email = Email.from("kpeel5839@a.com");
        cartDao.save(new Cart.Builder()
                .email(email)
                .productId(1L)
                .build());

        List<Cart> carts = cartDao.selectAll(email);
        assertThat(carts).hasSize(1);
        assertThat(carts.get(0).getEmail()).isEqualTo(email.getValue());
    }
    
    @Test
    @DisplayName("데이터 베이스에 Cart를 저장한 뒤, findById 로 확인한다.")
    void findById() {
        Email email = Email.from("kpeel5839@a.com");
        cartDao.save(new Cart.Builder()
                .email(email)
                .productId(1L)
                .build());

        List<Cart> carts = cartDao.selectAll(email);
        Cart cart = cartDao.findById(carts.get(0).getId());

        assertThat(carts.get(0).getEmail()).isEqualTo(cart.getEmail());
        assertThat(carts.get(0).getProductId()).isEqualTo(cart.getProductId());
    }

}
