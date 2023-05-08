package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import cart.controller.dto.CartResponse;
import cart.entiy.CartEntity;
import cart.repository.StubCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartCreateServiceTest {

    private StubCartRepository stubCartRepository;
    private CartCreateService cartCreateService;

    @BeforeEach
    void setUp() {
        stubCartRepository = new StubCartRepository();
        cartCreateService = new CartCreateService(stubCartRepository);
    }

    @Test
    void 생성_테스트() {
        final CartResponse cartResponse = cartCreateService.create("a@a.com", 1L);
        final Long cartId = cartResponse.getCartId();
        final Optional<CartEntity> cartEntity = stubCartRepository.findById(cartId);
        assertAll(
                () -> assertThat(cartEntity).isPresent(),
                () -> assertThat(cartEntity.get().getCartId()).isEqualTo(cartId),
                () -> assertThat(cartEntity.get().getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(cartEntity.get().getProductId()).isEqualTo(1L)
        );
    }
}
