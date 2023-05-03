package cart.listener;

import cart.domain.product.Product;
import cart.repository.product.ProductRepository;
import cart.service.user.UserCommandService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialDataListener {

    private final UserCommandService userCommandService;
    private final ProductRepository productRepository;

    public InitialDataListener(final UserCommandService userCommandService, final ProductRepository productRepository) {
        this.userCommandService = userCommandService;
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setDefaultUsers() {
        userCommandService.save("a@naver.com", "1234");
        userCommandService.save("b@naver.com", "1234");
        productRepository.save(new Product("라빈", "https://avatars.githubusercontent.com/u/39546083?v=4", 100));
    }
}
