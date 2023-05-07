package cart.repository;

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

        ProductEntity entity1 = new ProductEntity(null, "name1", "url1.com", 1000);
        ProductEntity entity2 = new ProductEntity(null, "name2", "url2.com", 2000);

        this.entity1 = productRepository.save(entity1);
        this.entity2 = productRepository.save(entity2);
    }

    @Test
    @DisplayName("상품 정보를 DB에 저장한다.")
    void saveTest() {
        ProductEntity entity3 = new ProductEntity(null, "name3", "url3.com", 3000);

        productRepository.save(entity3);
        List<ProductEntity> entities = productRepository.findAll();
        assertThat(entities).hasSize(3);
    }

    @Test
    @DisplayName("ID로 상품 정보를 조회한다.")
    void findByIdTest() {
        Optional<ProductEntity> nullableEntity = productRepository.findById(entity1.getId());
        if (nullableEntity.isEmpty()) {
            throw new RuntimeException();
        }
        ProductEntity foundEntity = nullableEntity.get();
        assertThat(foundEntity).isEqualTo(entity1);
    }

    @Test
    @DisplayName("모든 상품 정보를 조회한다.")
    void findAllTest() {
        List<ProductEntity> entities = productRepository.findAll();
        assertThat(entities).hasSize(2);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 수정한다.")
    void updateByIdTest() {
        ProductEntity modifiedEntity = new ProductEntity(entity1.getId(), entity1.getName(), entity1.getImgUrl(), 4000);
        productRepository.update(modifiedEntity);

        Optional<ProductEntity> nullableEntity = productRepository.findById(entity1.getId());
        if (nullableEntity.isEmpty()) {
            throw new RuntimeException();
        }
        ProductEntity entityAfterUpdate = nullableEntity.get();

        assertThat(entityAfterUpdate).isEqualTo(modifiedEntity);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 삭제한다.")
    void deleteByIdTest() {
        productRepository.deleteById(entity2.getId());

        List<ProductEntity> entities = productRepository.findAll();
        assertThat(entities).hasSize(1);
    }
}
