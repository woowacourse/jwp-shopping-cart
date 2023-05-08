package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.controller.dto.CartResponse;
import cart.domain.user.User;
import cart.repository.StubCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartDeleteServiceTest {

    private StubCartRepository stubCartRepository;
    private CartDeleteService cartDeleteService;

    @BeforeEach
    void setUp() {
        stubCartRepository = new StubCartRepository();
        cartDeleteService = new CartDeleteService(stubCartRepository);
    }

    @Test
    void 제거_테스트() {
        final CartResponse cartResponse = stubCartRepository.save("a@a.com", 1L);
        final long cartId = cartResponse.getCartId();

        cartDeleteService.delete(cartId, new User("a@a.com", "password"));
        assertThat(stubCartRepository.findById(cartId)).isEmpty();
    }
}
