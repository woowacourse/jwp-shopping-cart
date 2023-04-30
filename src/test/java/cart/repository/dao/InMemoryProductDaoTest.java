package cart.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.repository.entity.ProductEntity;
import java.util.List;
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
        final ProductEntity firstProductEntity = new ProductEntity("kokodak", "localhost:8080/test", 1000);
        final ProductEntity secondProductEntity = new ProductEntity("hardy", "localhost:8080/test", 1000);

        inMemoryProductDao.save(firstProductEntity);
        inMemoryProductDao.save(secondProductEntity);
        final List<ProductEntity> products = inMemoryProductDao.findAll();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(products.size()).isEqualTo(2);
        softAssertions.assertThat(products.get(0).getName()).isEqualTo(firstProductEntity.getName());
        softAssertions.assertThat(products.get(1).getName()).isEqualTo(secondProductEntity.getName());
        softAssertions.assertAll();
    }

    @Test
    void 엔티티를_업데이트한다() {
        final ProductEntity productEntity = new ProductEntity("kokodak", "localhost:8080/test", 1000);
        inMemoryProductDao.save(productEntity);

        final ProductEntity updatedProductEntity = new ProductEntity(1L, "hardy", "localhost:8080/test", 2000);
        inMemoryProductDao.update(updatedProductEntity);
        final List<ProductEntity> products = inMemoryProductDao.findAll();

        assertThat(products.get(0).getName()).isEqualTo(updatedProductEntity.getName());
    }

    @Test
    void 엔티티를_삭제한다() {
        final ProductEntity productEntity = new ProductEntity("kokodak", "localhost:8080/test", 1000);
        inMemoryProductDao.save(productEntity);

        inMemoryProductDao.delete(1L);

        assertThat(inMemoryProductDao.findAll()).isEmpty();
    }
}
