package cart.dao;

import cart.controller.dto.ProductRequest;
import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    private ProductRequest request;
    private String name;
    private String imageUrl;
    private int price;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);

        name = "치킨";
        imageUrl = "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg";
        price = 10000;

        request = new ProductRequest(name, imageUrl, price);
    }

    @DisplayName("상품을 정상적으로 추가한다.")
    @Test
    void save() {
        // when
        Long savedId = productDao.save(request);

        // then
        assertThat(savedId).isInstanceOf(Long.class);
    }

    @DisplayName("전체 상품을 조회한다.")
    @Test
    void findAll() {
        // given
        productDao.save(request);

        // when
        List<Product> products = productDao.findAll();

        // then
        assertSoftly(softly -> {
                    softly.assertThat(products).hasSize(1);
                    softly.assertThat(products.get(0))
                            .hasFieldOrPropertyWithValue("name", name)
                            .hasFieldOrPropertyWithValue("imageUrl", imageUrl)
                            .hasFieldOrPropertyWithValue("price", price);
                }
        );
    }

    @DisplayName("상품의 ID를 받아 상품 정보를 수정한다.")
    @Test
    void updateById() {
        // given
        Long savedId = productDao.save(request);

        // when
        productDao.updateById(savedId, request);

        // then
        Product product = productDao.findById(savedId)
                .orElseThrow(IllegalArgumentException::new);

        assertThat(product)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("imageUrl", imageUrl)
                .hasFieldOrPropertyWithValue("price", price);
    }

    @DisplayName("상품의 ID를 받아 상품을 삭제한다.")
    @Test
    void deleteById() {
        // given
        Long savedId = productDao.save(request);

        // when
        productDao.deleteById(savedId);
        Optional<Product> maybeProduct = productDao.findById(savedId);

        // then
        assertThat(maybeProduct.isEmpty()).isTrue();
    }
}