package cart.repository.product;

import cart.domain.product.Product;
import cart.repository.product.ProductDbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

import static cart.factory.product.ProductFactory.createOtherProduct;
import static cart.factory.product.ProductFactory.createProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDbRepositoryTest {

    private ProductDbRepository productDbRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @BeforeEach
    void setUp() {
        productDbRepository = new ProductDbRepository(jdbcTemplate);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("product");
    }

    @Test
    @DisplayName("모든 상품 정보를 조회한다.")
    void find_all_products_success() {
        // given
        Product product = createProduct();
        Product otherProduct = createOtherProduct();
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(product));
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(otherProduct));

        // when
        List<Product> products = productDbRepository.findAll();

        // then
        assertAll(
                () -> assertThat(products.size()).isEqualTo(2),
                () -> assertThat(products.get(0).getName()).isEqualTo(product.getName()),
                () -> assertThat(products.get(1).getName()).isEqualTo(otherProduct.getName())
        );
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void create_product_success() {
        // given
        List<Product> givenProducts = productDbRepository.findAll();
        Product product = createProduct();

        // when
        productDbRepository.add(product);

        // then
        List<Product> afterProducts = productDbRepository.findAll();

        assertAll(
                () -> assertThat(givenProducts.size() + 1).isEqualTo(afterProducts.size()),
                () -> assertThat(afterProducts.get(givenProducts.size()).getName()).isEqualTo(product.getName()),
                () -> assertThat(afterProducts.get(givenProducts.size()).getPrice()).isEqualTo(product.getPrice()),
                () -> assertThat(afterProducts.get(givenProducts.size()).getImgUrl()).isEqualTo(product.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void edit_product_success() {
        // given
        Product product = createProduct();
        productDbRepository.add(product);
        Product otherProduct = createOtherProduct();
        otherProduct.setId(product.getId());

        // when
        productDbRepository.update(otherProduct);

        // then
        Product result = productDbRepository.findAll().get(0);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo(otherProduct.getName()),
                () -> assertThat(result.getPrice()).isEqualTo(otherProduct.getPrice()),
                () -> assertThat(result.getImgUrl()).isEqualTo(otherProduct.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품을 제거한다.")
    void delete_product_success() {
        // given
        Product product = createProduct();
        productDbRepository.add(product);

        // when
        productDbRepository.delete(product);

        // then
        List<Product> products = productDbRepository.findAll();
        assertThat(products.contains(product)).isFalse();
    }
}
