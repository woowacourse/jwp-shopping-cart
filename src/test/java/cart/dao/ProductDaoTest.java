package cart.dao;

import cart.controller.dto.ProductRequest;
import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/data-test.sql"})
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;
    private Product product;
    private String name;
    private String imageUrl;
    private int price;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);

        name = "치킨";
        imageUrl = "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg";
        price = 10000;

        ProductRequest request = new ProductRequest(name, imageUrl, price);
        product = Product.from(request.getName(), request.getImageUrl(), request.getPrice());
    }

    @DisplayName("상품을 정상적으로 추가한다.")
    @Test
    void save() {
        // when
        Long productId = productDao.save(product);

        // then
        assertThat(productId).isInstanceOf(Long.class);
    }

    @DisplayName("전체 상품을 조회한다.")
    @Test
    void findAll() {
        // given
        productDao.save(product);

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

    @DisplayName("상품의 ID로 상품을 조회한다.")
    @Test
    void findById() {
        // given
        Long productId = productDao.save(product);
        // when
        Optional<Product> nullableProduct = productDao.findById(1L);

        // then
        assertSoftly(softly -> {
            softly.assertThat(nullableProduct.isPresent()).isTrue();
            softly.assertThat(nullableProduct.get()).hasFieldOrPropertyWithValue("name", name)
                    .hasFieldOrPropertyWithValue("imageUrl", imageUrl)
                    .hasFieldOrPropertyWithValue("price", price);
        });
    }

    @DisplayName("상품의 ID를 받아 상품 정보를 수정한다.")
    @Test
    void updateById() {
        // given
        Long productId = productDao.save(product);
        String newName = "업데이트치킨";
        String newImageUrl = "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg";
        int newPrice = 10000;
        ProductRequest newProductRequest = new ProductRequest(newName, newImageUrl, newPrice);
        Product newProduct = Product.from(productId, newProductRequest.getName(), newProductRequest.getImageUrl(), newProductRequest.getPrice());

        // when
        productDao.update(productId, newProduct);

        // then
        Product product = productDao.findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        assertThat(product)
                .hasFieldOrPropertyWithValue("name", newName)
                .hasFieldOrPropertyWithValue("imageUrl", newImageUrl)
                .hasFieldOrPropertyWithValue("price", newPrice);
    }

    @DisplayName("상품의 ID를 받아 상품을 삭제한다.")
    @Test
    void deleteById() {
        // given
        Long productId = productDao.save(product);

        // when
        productDao.deleteById(productId);
        Optional<Product> maybeProduct = productDao.findById(productId);

        // then
        assertThat(maybeProduct.isEmpty()).isTrue();
    }
}
