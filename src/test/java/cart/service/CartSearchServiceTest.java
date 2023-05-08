package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import cart.domain.cart.Cart;
import cart.repository.StubCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartSearchServiceTest {

    private CartSearchService cartSearchService;

    @BeforeEach
    void setUp() {
        final StubCartRepository stubCartRepository = new StubCartRepository();
        cartSearchService = new CartSearchService(stubCartRepository);
    }

    @Test
    void 조회_테스트() {
        final List<Cart> carts = cartSearchService.findByEmail("a@a.com");
        assertThat(carts).isNull();
    }
}
