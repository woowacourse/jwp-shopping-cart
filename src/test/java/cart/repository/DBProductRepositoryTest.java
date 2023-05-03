package cart.repository;

import cart.domain.Product;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Sql({"/testSchema.sql"})
class DBProductRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private ProductRepository productRepository;
    private ProductEntity entity1;
    private ProductEntity entity2;

    @BeforeEach
    void setUp() {
        productRepository = new DBProductRepository(jdbcTemplate);

        Product product1 = new Product("name1", "url1", 1000);
        Product product2 = new Product("name2", "url2", 2000);

        this.entity1 = productRepository.save(product1);
        this.entity2 = productRepository.save(product2);
    }

    @Test
    @DisplayName("상품 정보를 DB에 저장한다.")
    void save() {
        Product product3 = new Product("name3", "url3", 3000);

        productRepository.save(product3);
        List<ProductEntity> productEntities = productRepository.findAll();
        assertThat(productEntities).hasSize(3);
    }

    @Test
    @DisplayName("ID로 상품 정보를 조회한다.")
    void findById() {
        Optional<ProductEntity> nullableEntity = productRepository.findById(entity1.getId());
        if (nullableEntity.isEmpty()) {
            throw new RuntimeException();
        }
        ProductEntity response = nullableEntity.get();

        assertAll(
                () -> assertThat(response.getName()).isEqualTo("name1"),
                () -> assertThat(response.getImgUrl()).isEqualTo("url1"),
                () -> assertThat(response.getPrice()).isEqualTo(1000)
        );
    }

    @Test
    @DisplayName("모든 상품 정보를 조회한다.")
    void findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        assertThat(productEntities).hasSize(2);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 수정한다.")
    void updateById() {
        ProductEntity modifiedProductEntity = new ProductEntity(entity1.getId(), "newName", "newUrl", 4000);
        productRepository.update(modifiedProductEntity);

        Optional<ProductEntity> nullableEntity = productRepository.findById(entity1.getId());
        if (nullableEntity.isEmpty()) {
            throw new RuntimeException();
        }
        ProductEntity findProduct = nullableEntity.get();

        assertAll(
                () -> assertThat(findProduct.getName()).isEqualTo(modifiedProductEntity.getName()),
                () -> assertThat(findProduct.getImgUrl()).isEqualTo(modifiedProductEntity.getImgUrl()),
                () -> assertThat(findProduct.getPrice()).isEqualTo(modifiedProductEntity.getPrice())
        );
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 삭제한다.")
    void deleteById() {
        productRepository.deleteById(entity2.getId());

        List<ProductEntity> productEntities = productRepository.findAll();
        assertThat(productEntities).hasSize(1);
    }
}
