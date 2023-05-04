package cart.infrastructure;

import cart.domain.product.Product;
import cart.domain.user.User;
import cart.repository.ProductRepository;
import cart.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class DataGenerator implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public DataGenerator(final UserRepository userRepository, final ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        userRepository.save(new User("a@a.com", "password1"));
        userRepository.save(new User("b@b.com", "password2"));
        productRepository.save(new Product("치킨", "https://pelicana.co.kr/resources/images/menu/original_menu10_210705.png", 10000));
        productRepository.save(new Product("샐러드", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsvb-GYnRmJZRY7tyLWIoRDN_aGmCOQgVXOz3HX80td8rHbmTvWw7TH6zPd4Bt2CoOnno&usqp=CAU", 20000));
        productRepository.save(new Product("피자", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsh-vwIw4nZLQ5lUJf-hnz7uQiEykeKqMfeg&usqp=CAU", 13000));
    }
}
