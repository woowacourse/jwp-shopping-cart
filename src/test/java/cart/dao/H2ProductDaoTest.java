package cart.dao;

import cart.domain.Product;
import cart.dto.request.ProductRequest;
import cart.entity.ProductEntity;
import cart.fixture.ImageFixture;
import cart.fixture.ProductFixture.COFFEE;
import cart.fixture.ProductFixture.PIZZA;
import cart.fixture.ProductFixture.RAMYEON;
import cart.fixture.ProductFixture.SNACK;
import cart.fixture.ProductFixture.WATER;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class H2ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new H2ProductDao(jdbcTemplate);
    }

    @Test
    void 상품이_정상적으로_저장된다() {
        Product product = new Product("pizza", ImageFixture.url, 10000);
        ProductEntity created = productDao.save(product).orElseGet(() -> null);
        assertThat(created).isNotNull();
    }

    @Test
    void 상품_데이터_정합성_검증() {
        Product product = new Product("pizza", "https://www.naver.com", 10000);
        ProductEntity created = productDao.save(product).orElseGet(() -> null);

        Assertions.assertAll(
                () -> assertThat(created).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(created.getName())
        );
    }

    @Test
    void 모든_상품을_조회한다() {
        List<Product> products = List.of(RAMYEON.PRODUCT, WATER.PRODUCT, PIZZA.PRODUCT, SNACK.PRODUCT);
        for (Product product : products) {
            productDao.save(product);
        }
        List<ProductEntity> all = productDao.findAll();
        assertThat(all).hasSize(products.size());
    }

    @Test
    void 존재하지_않는_상품_조회_시_empty_를_반환한다() {
        Optional<ProductEntity> result = productDao.findById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    void 상품_정보_업데이트() {
        Product ramyeon = RAMYEON.PRODUCT;
        Optional<ProductEntity> saved = productDao.save(ramyeon);
        ProductEntity product = saved.get();
        Long productId = product.getId();
        ProductRequest updateRequest = new ProductRequest(ImageFixture.url, "expected", 1000);

        product.replace(updateRequest);
        productDao.update(product);

        ProductEntity updatedProduct = productDao.findById(productId).get();

        Assertions.assertAll(
                () -> assertThat(updatedProduct.getImage()).isEqualTo(updateRequest.getImage()),
                () -> assertThat(updatedProduct.getName()).isEqualTo(updateRequest.getName()),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(updateRequest.getPrice())
        );
    }

    @Test
    void 상품_삭제() {
        ProductEntity product = productDao.save(COFFEE.PRODUCT).get();
        Long productId = product.getId();

        productDao.deleteById(productId);
        Optional<ProductEntity> result = productDao.findById(productId);

        assertThat(result).isEmpty();
    }
}
