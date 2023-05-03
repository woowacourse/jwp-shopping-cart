package cart.listener;

import cart.domain.product.Product;
import cart.domain.user.User;
import cart.repository.product.ProductRepository;
import cart.repository.user.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialDataListener {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public InitialDataListener(final UserRepository userRepository, final ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setDefaultUsers() {
        userRepository.save(new User("a@naver.com", "1234"));
        userRepository.save(new User("b@naver.com", "1234"));
        productRepository.save(new Product("라빈", "https://avatars.githubusercontent.com/u/39546083?v=4", 100));
    }
}
