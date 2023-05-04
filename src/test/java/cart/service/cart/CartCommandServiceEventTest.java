package cart.service.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.cart.Cart;
import cart.domain.user.User;
import cart.event.user.UserRegisteredEvent;
import cart.repository.cart.CartRepository;
import cart.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
class CartCommandServiceEventTest {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    void 이벤트_테스트() {
        //given
        final User user = userRepository.save(new User("asdf", "1234"));

        //when
        applicationEventPublisher.publishEvent(new UserRegisteredEvent(user));

        //then
        final Optional<Cart> expectResult = cartRepository.findByUser(user);
        assertAll(
                () -> assertThat(expectResult).isPresent(),
                () -> assertThat(expectResult.get().getCartId().getValue()).isPositive()
        );
    }
}
