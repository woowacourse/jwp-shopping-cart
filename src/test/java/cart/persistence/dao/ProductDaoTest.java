package cart.persistence.dao;

import cart.persistence.entity.ProductEntity;
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
@Import({ProductDao.class, MemberDao.class, CartDao.class})
class ProductDaoTest {

    private ProductEntity productEntity;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        productEntity = new ProductEntity("치킨", "chicken_image_url", 20000, "KOREAN");
    }

    @DisplayName("유효한 상품 아이디가 주어지면, 상품 정보를 조회한다")
    @Test
    void findById_success() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        final Optional<ProductEntity> product = productDao.findById(productId);

        // then
        final ProductEntity findProduct = product.get();

        assertThat(findProduct)
                .extracting("name", "price", "imageUrl", "category")
                .containsExactly("치킨", 20000, "chicken_image_url", "KOREAN");
    }

    @DisplayName("유효하지 않은 상품 아이디가 주어지면, 빈 값을 반환한다")
    @Test
    void findById_empty() {
        // when
        final Optional<ProductEntity> findProduct = productDao.findById(1L);

        // then
        assertThat(findProduct).isEmpty();
    }

    @DisplayName("상품 정보를 저장한다")
    @Test
    void insert() {
        // when
        final Long productId = productDao.insert(productEntity);

        // then
        final Optional<ProductEntity> product = productDao.findById(productId);
        final ProductEntity findProduct = product.get();

        assertThat(findProduct)
                .extracting("name", "price", "imageUrl", "category")
                .containsExactly("치킨", 20000, "chicken_image_url", "KOREAN");
    }

    @DisplayName("상품 정보 전체를 조회한다.")
    @Test
    void findAll() {
        // given
        productDao.insert(productEntity);
        productDao.insert(new ProductEntity("탕수육", "pork_image_url", 30000, "CHINESE"));

        // when
        final List<ProductEntity> products = productDao.findAll();

        // then
        assertThat(products).hasSize(2);
        assertThat(products)
                .extracting("name", "price", "imageUrl", "category")
                .containsExactly(tuple("치킨", 20000, "chicken_image_url", "KOREAN"),
                        tuple("탕수육", 30000, "pork_image_url", "CHINESE"));
    }

    @DisplayName("주어진 상품 아이디에 해당하는 상품 정보를 수정한다.")
    @Test
    void updateById() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        final ProductEntity updateProduct = new ProductEntity(productId, "탕수육", "pork_image_url", 30000, "CHINESE");
        int updatedCount = productDao.updateById(updateProduct, productId);

        // then
        final Optional<ProductEntity> product = productDao.findById(productId);
        final ProductEntity findProduct = product.get();

        assertThat(updatedCount).isEqualTo(1);
        assertThat(findProduct)
                .extracting("name", "price", "imageUrl", "category")
                .containsExactly("탕수육", 30000, "pork_image_url", "CHINESE");
    }

    @DisplayName("주어진 상품 아이디에 해당하는 상품을 삭제한다.")
    @Test
    void deleteById() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        int deletedCount = productDao.deleteById(productId);

        // then
        final Optional<ProductEntity> product = productDao.findById(productId);

        assertThat(product).isEmpty();
        assertThat(deletedCount).isEqualTo(1);
    }
}
