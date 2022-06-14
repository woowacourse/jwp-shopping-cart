package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
class ProductRepositoryTest {

    private ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productRepository = new ProductRepository(new ProductDao(namedParameterJdbcTemplate));
    }

    @DisplayName("id 를 이용해서 저장되어 있는 상품을 조회한다.")
    @Test
    void findById() {
        // given
        Long productId = 1L;

        // when
        Product product = productRepository.selectById(productId);

        // then
        assertAll(
                () -> assertThat(product.getId()).isEqualTo(1L),
                () -> assertThat(product.getName()).isEqualTo("SPC삼립 뉴욕샌드위치식빵 (990g×4ea) BOX"),
                () -> assertThat(product.getPrice()).isEqualTo(12090),
                () -> assertThat(product.getImageUrl()).isEqualTo(
                        "https://cdn-mart.baemin.com/sellergoods/main/678bd8ec-e5fa-4ae2-be55-2cd290b3f10f.jpg"
                )
        );
    }

    @DisplayName("페이지 번호와 불러올 상품 갯수를 이용해서 페이지의 상품들을 조회한다.")
    @Test
    void findProductsOfPage() {
        // given
        int page = 2;
        int limit = 5;

        // when
        List<Product> productsOfPage = productRepository.selectProductsOfPage(page, limit);

        // then
        assertAll(
                () -> assertThat(productsOfPage.size()).isEqualTo(5),
                () -> assertThat(productsOfPage.stream().map(Product::getId).collect(Collectors.toList()))
                        .containsExactly(6L, 7L, 8L, 9L, 10L)
        );
    }

    @DisplayName("모든 상품을 조회하는 기능")
    @Test
    void findAll() {
        // given when
        List<Product> products = productRepository.selectAll();

        // then
        assertThat(products.size()).isEqualTo(99);
    }
}
