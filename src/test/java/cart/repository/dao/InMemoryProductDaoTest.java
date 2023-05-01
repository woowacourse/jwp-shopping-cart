package cart.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Product;
import java.util.List;

import cart.repository.dao.productDao.InMemoryProductDao;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InMemoryProductDaoTest {

    InMemoryProductDao inMemoryProductDao;

    @BeforeEach
    void setUp() {
        inMemoryProductDao = new InMemoryProductDao();
    }

    @Test
    void 엔티티를_저장하고_조회한다() {
        final Product firstProduct = new Product("kokodak", "localhost:8080/test", 1000);
        final Product secondProduct = new Product("hardy", "localhost:8080/test", 1000);

        inMemoryProductDao.save(firstProduct);
        inMemoryProductDao.save(secondProduct);
        final List<Product> products = inMemoryProductDao.findAll();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(products.size()).isEqualTo(2);
        softAssertions.assertThat(products.get(0).getName()).isEqualTo(firstProduct.getName());
        softAssertions.assertThat(products.get(1).getName()).isEqualTo(secondProduct.getName());
        softAssertions.assertAll();
    }

    @Test
    void 엔티티를_업데이트한다() {
        final Product product = new Product("kokodak", "localhost:8080/test", 1000);
        inMemoryProductDao.save(product);

        final Product updatedProduct = new Product(1L, "hardy", "localhost:8080/test", 2000);
        inMemoryProductDao.update(updatedProduct);
        final List<Product> products = inMemoryProductDao.findAll();

        assertThat(products.get(0).getName()).isEqualTo(updatedProduct.getName());
    }

    @Test
    void 엔티티를_삭제한다() {
        final Product product = new Product("kokodak", "localhost:8080/test", 1000);
        inMemoryProductDao.save(product);

        inMemoryProductDao.delete(1L);

        assertThat(inMemoryProductDao.findAll()).isEmpty();
    }
}
