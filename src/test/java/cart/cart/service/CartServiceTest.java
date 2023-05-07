package cart.cart.service;

import cart.cart.dao.CartDAO;
import cart.catalog.dao.CatalogDAO;
import cart.catalog.dto.ProductResponseDTO;
import cart.product.service.FakeCatalogDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class CartServiceTest {

    private final CartDAO cartDAO = new FakeCartDAO();
    private final CatalogDAO catalogDAO = new FakeCatalogDAO();

    @Test
    @DisplayName("장바구니 상품 리스트 조회 테스트")
    void display() {
        //given
        final long userId = 1L;
        //cartDAO에는 2개의 상품이 들어있음
        final CartService cartService = new CartService(this.cartDAO, this.catalogDAO);

        //when
        final List<ProductResponseDTO> userCart = cartService.findUserCart(userId);

        //then
        Assertions.assertEquals(2, userCart.size());
        Assertions.assertEquals("망고", userCart.get(0).getName());
    }
}