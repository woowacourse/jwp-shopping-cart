package cart.dao;

import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JdbcTest
@Import(ProductDao.class)
class ProductDaoTest {

    private Product product;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        product = new Product("치킨",
                "chicken_image_url",
                20000, ProductCategory.KOREAN);
    }

    @DisplayName("존재하는 상품을 조회하면, 성공적으로 가져온다.")
    @Test
    void findById_success() {
        // given
        final Long productId = productDao.insert(product);

        // when
        final Optional<Product> product = productDao.findById(productId);

        // then
        final Product findProduct = product.get();

        assertThat(findProduct)
                .extracting("name", "price", "imageUrl", "category")
                .contains("치킨", 20000, "chicken_image_url", ProductCategory.KOREAN);
    }

    @DisplayName("존재하지 않는 상품을 가져오면, 빈 값을 반환한다.")
    @Test
    void findById_empty() {
        // when
        final Optional<Product> findProduct = productDao.findById(1L);

        // then
        assertThat(findProduct).isEmpty();
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void insert() {
        // when
        final Long productId = productDao.insert(product);

        // then
        final Optional<Product> product = productDao.findById(productId);
        final Product findProduct = product.get();

        assertThat(findProduct)
                .extracting("name", "price", "imageUrl", "category")
                .contains("치킨", 20000, "chicken_image_url", ProductCategory.KOREAN);
    }

    @DisplayName("상품 전체를 조회한다.")
    @Test
    void findAll() {
        // given
        productDao.insert(product);
        productDao.insert(new Product("탕수육", "pork_image_url", 30000, ProductCategory.CHINESE));

        // when
        final List<Product> products = productDao.findAll();

        // then
        assertThat(products).hasSize(2);
        assertThat(products)
                .extracting("name", "price", "imageUrl", "category")
                .contains(tuple("치킨", 20000, "chicken_image_url", ProductCategory.KOREAN),
                        tuple("탕수육", 30000, "pork_image_url", ProductCategory.CHINESE));
    }

    @DisplayName("상품을 수정한다.")
    @Test
    void updateById() {
        // given
        final Long productId = productDao.insert(product);

        // when
        final Product updateProduct = new Product(productId, "탕수육", "pork_image_url", 30000, ProductCategory.CHINESE);
        int updatedCount = productDao.updateById(updateProduct, productId);

        // then
        final Optional<Product> product = productDao.findById(productId);
        final Product findProduct = product.get();

        assertThat(updatedCount).isEqualTo(1);
        assertThat(findProduct)
                .extracting("name", "price", "imageUrl", "category")
                .contains("탕수육", 30000, "pork_image_url", ProductCategory.CHINESE);
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteById() {
        // given
        final Long productId = productDao.insert(product);

        // when
        int deletedCount = productDao.deleteById(productId);

        // then
        final Optional<Product> product = productDao.findById(productId);

        assertThat(product).isEmpty();
        assertThat(deletedCount).isEqualTo(1);
    }
}
