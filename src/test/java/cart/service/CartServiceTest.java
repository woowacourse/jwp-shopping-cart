package cart.service;

import cart.dto.CartProductResponse;
import cart.dto.UserResponse;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.ProductDao;
import cart.persistence.dao.UserDao;
import cart.persistence.entity.CartEntity;
import cart.persistence.entity.ProductEntity;
import cart.persistence.entity.UserEntity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CartServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    @Test
    void 유저_정보를_모두_불러온다() {
        //given, when
        List<UserResponse> userResponses = cartService.readAllUsers();

        //then
        assertThat(userResponses).isEqualTo(List.of(new UserResponse("jena@naver.com", "1234"),
                new UserResponse("spring@gmail.com", "pwspring34")));
    }

    @Test
    void 사용자_이메일로_사용자_장바구니의_목록을_불러온다() {
        //given
        Long userId = userDao.save(new UserEntity("jea@naver.com", "1234"));

        Long productId1 = productDao.save(new ProductEntity("chicken", 30, "http://naver.com"));
        Long productId2 = productDao.save(new ProductEntity("zzang", 30, "http://naver.com"));
        Long cartProductId1 = cartDao.save(new CartEntity(userId, productId1));
        Long cartProductId2 = cartDao.save(new CartEntity(userId, productId2));
        CartProductResponse cartProduct1 = new CartProductResponse(cartProductId1, "chicken", 30, "http://naver.com");
        CartProductResponse cartProduct2 = new CartProductResponse(cartProductId2, "zzang", 30, "http://naver.com");

        //when
        List<CartProductResponse> cartProductResponses = cartService.readCart("jea@naver.com");

        //then
        assertThat(cartProductResponses).isEqualTo(List.of(cartProduct1, cartProduct2));
    }

    @Test
    void 장바구니에_상품을_추가한다() {
        //given, when
        long cartId = cartService.addCartItem("spring@gmail.com", 1L);

        //then
        assertThat(cartService.readCart("spring@gmail.com")).isEqualTo(
                List.of(new CartProductResponse(cartId, "스프링", 1000, "https://previews.123rf.com/images/marucyan/marucyan1211/marucyan121100106/16114832-새싹.jpg")));
    }

    @Test
    void 장바구니에서_상품을_제거한다() {
        //given
        long cartId = cartService.addCartItem("spring@gmail.com", 1L);

        //when
        cartService.deleteCartItem(cartId);

        //then
        assertThat(cartService.readCart("spring@gmail.com").size()).isEqualTo(0);
    }

    @Test
    void 장바구니에_없는_상품을_지우려하면_에러가_발생한다() {
        assertThatThrownBy(() -> cartService.deleteCartItem(1L)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
