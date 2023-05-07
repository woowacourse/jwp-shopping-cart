package cart.database.repository;

import cart.controller.dto.response.CartItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
public class CartRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartRepository cartRepository;
    private Long productId = 1L;
    private String productName;
    private int price;
    private String imageUrl;
    private Long userId;
    private int count;


    @BeforeEach
    public void setUp() {
        cartRepository = new CartRepository(jdbcTemplate);

        productId = 1L;
        productName = "testP";
        price = 1000;
        imageUrl = "test";
        userId = 1L;
        count = 1;

        String insertProduct = "INSERT INTO PRODUCT (name, price, image_url) VALUES (?, ?, ?)";
        String insertCart = "INSERT INTO CART (USER_ID, PRODUCT_ID, COUNT) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertProduct, productName, price, imageUrl);
        jdbcTemplate.update(insertCart, userId, productId, count);
    }

    @DisplayName("UserId를 이용해 조인으로 장바구니 아이템들 가져오는 테스트")
    @Test
    public void findCartsWithProductByUserId() {
        //given

        //when
        List<CartItemResponse> cartsWithProductByUserId = cartRepository.findCartsWithProductByUserId(userId);

        assertEquals(1, cartsWithProductByUserId.size());
    }
}
