package cart.persistence;

import cart.service.product.Product;
import cart.service.product.ProductDao;
import cart.service.product.dto.ProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static cart.fixture.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
@Sql("/test.sql")
class H2ProductDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new H2ProductDao(jdbcTemplate);
    }

    @Test
    void 상품이_정상적으로_저장된다() {
        Product product = new Product("pizza", "url", 10000);
        Long productId = productDao.save(product);
        assertThat(productId).isPositive();
    }

    @Test
    void 상품_데이터_정합성_검증() {
        Product product = new Product("pizza", "url", 10000);
        Long productId = productDao.save(product);
        Product created = productDao.findById(productId).orElse(null);
        Assertions.assertAll(
                () -> assertThat(created).isNotNull(),
                () -> {
                    assert created != null;
                    assertThat(product.getName()).isEqualTo(created.getName());
                }
        );
    }

    @Test
    void 모든_상품을_조회한다() {
        List<Product> products = List.of(RAMYEON, WATER, PIZZA, SNACK);
        for (Product product : products) {
            productDao.save(product);
        }
        List<Product> all = productDao.findAll();
        assertThat(all).hasSize(products.size());
    }

    @Test
    void 존재하지_않는_상품_조회_시_empty_를_반환한다() {
        Optional<Product> result = productDao.findById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    void 상품_정보_업데이트() {
        Product ramyeon = RAMYEON;
        Long productId = productDao.save(ramyeon);
        Product product = productDao.findById(productId).get();

        ProductRequest updateRequest = new ProductRequest("expectedUrl", "expected", 1000);

        Product newInfoProduct = product.replaceProduct(updateRequest, product.getId());
        productDao.update(newInfoProduct);

        Product updatedProduct = productDao.findById(productId).get();

        Assertions.assertAll(
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(updateRequest.getImageUrl()),
                () -> assertThat(updatedProduct.getName()).isEqualTo(updateRequest.getName()),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(updateRequest.getPrice())
        );
    }

    @Test
    void 상품_삭제() {
        Long productId = productDao.save(COFFEE);

        productDao.deleteById(productId);
        Optional<Product> result = productDao.findById(productId);

        assertThat(result).isEmpty();
    }
}
