package cart.dao.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.entity.product.Product;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@TestInstance(Lifecycle.PER_CLASS)
class ProductDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeAll
    void generateProductDao() {
        productDao = new ProductDaoImpl(jdbcTemplate);
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void insert_product() {
        // given
        Product product = new Product("연필", "이미지url", 1000);

        // when
        Long savedId = productDao.insertProduct(product);
        Optional<Product> result  = productDao.findById(savedId);

        // then
        assertThat(result.get().getName()).isEqualTo(product.getName());
        assertThat(result.get().getImageUrl()).isEqualTo(product.getImageUrl());
        assertThat(result.get().getPrice()).isEqualTo(product.getPrice());
    }

    @DisplayName("상품 전체를 조회한다.")
    @Test
    void find_all_product() {
        // given
        Product product1 = new Product("연필", "이미지url", 1000);
        Product product2 = new Product("지우개", "이미지url", 1000);
        productDao.insertProduct(product1);
        productDao.insertProduct(product2);

        // when
        List<Product> result = productDao.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("상품 아이디를 통해서 상품을 조회한다.")
    @Test
    void find_product_by_id() {
        // given
        Product product1 = new Product("연필", "이미지url", 1000);
        Product product2 = new Product("지우개", "이미지url", 1000);
        Long product1Id = productDao.insertProduct(product1);
        Long product2Id = productDao.insertProduct(product2);

        // when
        Optional<Product> result = productDao.findById(product1Id);

        // then
        assertThat(result.get().getName()).isEqualTo(product1.getName());
        assertThat(result.get().getImageUrl()).isEqualTo(product1.getImageUrl());
        assertThat(result.get().getPrice()).isEqualTo(product1.getPrice());
    }

    @DisplayName("상품의 ID를 통해 내용을 업데이트 한다.")
    @Test
    void update_product_by_id() {
        // given
        Product product = new Product("연필", "이미지url", 1000);
        Long productId = productDao.insertProduct(product);
        Product updateProduct = new Product("지우개", "이미지url", 2000);

        // when
        productDao.updateProduct(productId, updateProduct);
        Optional<Product> result = productDao.findById(productId);

        // then
        assertThat(result.get().getName()).isEqualTo(updateProduct.getName());
        assertThat(result.get().getImageUrl()).isEqualTo(updateProduct.getImageUrl());
        assertThat(result.get().getPrice()).isEqualTo(updateProduct.getPrice());
    }

    @DisplayName("상품을 ID를 통해서 삭제한다.")
    @Test
    void delete_product_by_id() {
        // given
        Product product = new Product("연필", "이미지url", 1000);
        Long productId = productDao.insertProduct(product);

        // when
        Long result = productDao.deleteProduct(productId);

        // then
        assertThatThrownBy(() -> productDao.findById(result).get())
            .isInstanceOf(NoSuchElementException.class);
    }
}
