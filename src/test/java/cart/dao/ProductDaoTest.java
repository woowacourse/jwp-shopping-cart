package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.ProductRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class ProductDaoTest {

    private ProductDao productDao;
    private ProductEntity firstRecord;
    private Long firstRecordId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);

        firstRecord = productDao.findAll().get(0);
        firstRecordId = firstRecord.getId();
    }

    @Test
    @DisplayName("add() 메서드를 호출하면 하나의 데이터가 product에 추가된다")
    void add() {
        final ProductRequest request = new ProductRequest("test", 15_000, "test/image/url");
        final int beforeSize = productDao.findAll().size();
        productDao.add(request);
        final int afterSize = productDao.findAll().size();
        assertThat(afterSize).isEqualTo(beforeSize + 1);
    }

    @Test
    @DisplayName("findAll() 메서드를 호출하면 3개의 초기 product 데이터를 반환한다")
    void findAll() {
        final List<ProductEntity> productEntities = productDao.findAll();

        assertThat(productEntities.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("findById() 메서드를 호출하면 해당 id의 product 데이터를 반환한다")
    void findById() {
        // given

        // when
        final ProductEntity actual = productDao.findById(firstRecordId).get();

        // then
        assertThat(actual).isEqualTo(firstRecord);
    }

    @Test
    @DisplayName("updateById() 메서드를 호출하면 해당 id의 product를 요청에 따른 데이터로 변경한다")
    void updateById() {
        // given
        final String requestName = "소주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest updateRequest = new ProductRequest(requestName, requestPrice,
                requestUrl);

        // when
        final int updateCount = productDao.updateById(firstRecordId, updateRequest);

        // then
        final ProductEntity actual = productDao.findById(firstRecordId).get();
        assertAll(
                () -> assertThat(updateCount).isEqualTo(1),
                () -> assertThat(actual.getName()).isEqualTo(requestName),
                () -> assertThat(actual.getPrice()).isEqualTo(requestPrice),
                () -> assertThat(actual.getImageUrl()).isEqualTo(requestUrl)
        );
    }

    @Test
    @DisplayName("deleteById() 메서드를 호출하면 해당 id의 product를 제거한다")
    void deleteById() {
        // given

        // when
        final int deleteCount = productDao.deleteById(firstRecordId);
        final Optional<ProductEntity> deletedRecord = productDao.findById(firstRecordId);

        // then
        assertAll(
                () -> assertThat(deletedRecord).isEmpty(),
                () -> assertThat(deleteCount).isEqualTo(1)
        );
    }
}
