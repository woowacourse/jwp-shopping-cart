package cart.dao;

import cart.domain.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Transactional
class ProductDaoImplTest {

    ProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("제품 데이터베이스에 상품 데이터를 추가한다")
    void insert() {
        productDao.insert(new ProductEntity.Builder().name("pizza").image("image").price(10000).build());

        assertThat(productDao.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("데이터베이스에 등록된 모든 상품 정보를 찾는다")
    void findAll() {
        productDao.insert(new ProductEntity.Builder().name("pizza").image("image1").price(10000).build());
        productDao.insert(new ProductEntity.Builder().name("chiken").image("image2").price(20000).build());

        assertThat(productDao.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("특정 Id에 해당하는 제품을 조회한다.")
    void findById() {
        String name = "pizza";
        String image = "image1";
        int price = 10000;
        ProductEntity product = new ProductEntity.Builder().name("pizza").image("image1").price(10000).build();

        int id = (int) productDao.insert(product);

        ProductEntity insertProduct = productDao.findById(id).get();

        assertAll(
                () -> assertThat(insertProduct.getName()).isEqualTo(name),
                () -> assertThat(insertProduct.getImage()).isEqualTo(image),
                () -> assertThat(insertProduct.getPrice()).isEqualTo(price)
        );
    }

    @Test
    @DisplayName("특정 id에 해당하는 제품을 제거한다.")
    void delete() {
        int id = (int) productDao.insert(new ProductEntity.Builder().name("pizza").image("image1").price(10000).build());

        productDao.delete(id);

        assertThat(productDao.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 제품을 조회할 경우 Optional Empty가 반환된다.")
    void findByIdWithInvalidId() {

        final Optional<ProductEntity> productEntity = productDao.findById(1);

        assertTrue(productEntity.isEmpty());
    }

}