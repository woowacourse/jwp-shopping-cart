package woowacourse.shoppingcart.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import woowacourse.shoppingcart.product.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.product.support.jdbc.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:truncate.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductServiceTest {

    private static final String PRODUCT_NAME1 = "상품1";
    private static final long PRODUCT_PRICE1 = 1000;
    private static final String PRODUCT_IMAGE_URL1 = "http://img1.com";
    private static final String PRODUCT_NAME2 = "상품2";
    private static final long PRODUCT_PRICE2 = 2000;
    private static final String PRODUCT_IMAGE_URL2 = "http://img2.com";

    private final ProductService productService;
    private final JdbcTemplate jdbcTemplate;

    public ProductServiceTest(final DataSource dataSource, final JdbcTemplate jdbcTemplate) {
        final ProductDao productDao = new ProductDao(dataSource);
        this.productService = new ProductService(productDao);
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) values(?, ?, ?)",
                PRODUCT_NAME1, PRODUCT_PRICE1, PRODUCT_IMAGE_URL1);
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) values(?, ?, ?)",
                PRODUCT_NAME2, PRODUCT_PRICE2, PRODUCT_IMAGE_URL2);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findProducts() {
        final List<ProductResponse> productResponses = productService.findProducts();
        assertThat(productResponses).hasSize(2);
    }

    @DisplayName("단일 상품을 조회한다.")
    @Test
    void findProductById() {
        final ProductResponse productResponse = productService.findProductById(1L);
        assertThat(productResponse).extracting("name", "price", "image")
                .containsExactly(PRODUCT_NAME1, PRODUCT_PRICE1, PRODUCT_IMAGE_URL1);
    }
}