package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Product;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcCartRepository.class)
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void addProduct() {

    }

    @Test
    void removeProduct() {
    }

    @Test
    void findAllProduct() {
        final List<Product> allProduct = cartRepository.findAllProduct(1L);

        assertThat(allProduct).hasSize(1);
    }
}
