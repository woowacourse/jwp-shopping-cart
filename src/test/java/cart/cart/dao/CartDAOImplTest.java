package cart.cart.dao;

import cart.cart.domain.Cart;
import cart.cart.dto.CartRequestDTO;
import cart.common.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(CartDAOImpl.class)
class CartDAOImplTest {

    @Autowired
    private CartDAO cartDAO;

    @Test
    @DisplayName("장바구니에 상품 추가 테스트")
    void insert() {
        //given
        final long productId = 1L;
        final long userId = 1L;
        final CartRequestDTO cartRequestDTO = CartRequestDTO.of(userId, productId);

        //when
        final Cart cart = this.cartDAO.create(cartRequestDTO);

        //then
        Assertions.assertEquals(userId, cart.getUserId());
    }

    @Test
    @DisplayName("장바구니에 상품 조회 테스트")
    void find() {
        //given
        final long productId = 1L;
        final long userId = 1L;
        final CartRequestDTO cartRequestDTO = CartRequestDTO.of(userId, productId);

        //when
        final Cart cart = this.cartDAO.find(cartRequestDTO);

        //then
        Assertions.assertEquals(userId, cart.getUserId());
    }

    @Test
    @DisplayName("장바구니에 상품 삭제 테스트")
    void delete() {
        //given
        final long productId = 1L;
        final long userId = 1L;
        final CartRequestDTO cartRequestDTO = CartRequestDTO.of(userId, productId);

        //when
        final Cart cart = this.cartDAO.find(cartRequestDTO);
        this.cartDAO.delete(cart);

        //then
        Assertions.assertThrows(NotFoundException.class, () -> this.cartDAO.find(cartRequestDTO));
    }

    @Test
    @DisplayName("장바구니 비우기 테스트")
    void clear() {
        //given
        final long productId = 1L;
        final long userId = 1L;
        final CartRequestDTO cartRequestDTO = CartRequestDTO.of(userId, productId);
        this.cartDAO.create(cartRequestDTO);

        //when
        this.cartDAO.clear(userId);

        //then
        Assertions.assertThrows(NotFoundException.class, () -> this.cartDAO.find(cartRequestDTO));
    }

}