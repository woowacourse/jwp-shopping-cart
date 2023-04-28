package cart.dao;

import cart.controller.dto.ProductRequest;
import cart.dao.entity.ProductEntity;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class MySQLProductDaoTest {

    private ProductDao mySQLProductDao;
    private ProductEntity firstRecord;
    private Long firstRecordId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        mySQLProductDao = new MySQLProductDao(jdbcTemplate);
        String productDropQuery = "DROP TABLE product IF EXISTS";
        String productCreateQuery = "CREATE TABLE product(id BIGINT NOT NULL AUTO_INCREMENT,name VARCHAR(20) NOT NULL,price INT NOT NULL,image_url TEXT NOT NULL, PRIMARY KEY(id))";
        String sampleQuery1 = "INSERT INTO product(name, price, image_url) VALUES ('피자', 13000, 'https://searchad-phinf.pstatic.net/MjAyMjEyMjdfMTE1/MDAxNjcyMTAxNTI0Nzg4.WfiSlsy9fTUQJ6q2FTGOaaOVU0QpSB0U1LvplKZQXzIg.H4UgI0VbKUszP7mzC3qhwpSMe15DluJnxjxVGDq_QUgg.PNG/451708-1fa87663-02e3-4303-b8a9-d7eea3676018.png?type=f160_160')";
        String sampleQuery2 = "INSERT INTO product(name, price, image_url) VALUES ('치킨', 27000, 'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAzMjdfMTI2%2FMDAxNjc5OTI1ODQ4NTgy.6RT9z-i5prsnwwc-6B9TaK6Q0Zcgsd3TeDGiUdqyDRIg.rW2kMtzBKNFhWWXyr_X2bZfR15AEPUOz-VJnqVaP0jEg.JPEG.koreasinju%2FIMG_3379.jpg&type=ff332_332')";
        String sampleQuery3 = "INSERT INTO product(name, price, image_url) VALUES ('샐러드', 9500, 'https://searchad-phinf.pstatic.net/MjAyMzA0MThfMjk0/MDAxNjgxODAwNDY4NjU4.FJcjmoGsxyCq0nZqlcmoAL2mwX8mM9ny9DdliQcqGZ0g.9cGk2IQHfPIm2-ABelEOY1cc-_8NBQgPMgPpjFZkGFEg.JPEG/2814800-bb4236af-96dd-42e7-8256-32ffaa73de52.jpg?type=f160_160')";

        jdbcTemplate.execute(productDropQuery);
        jdbcTemplate.execute(productCreateQuery);
        jdbcTemplate.update(sampleQuery1);
        jdbcTemplate.update(sampleQuery2);
        jdbcTemplate.update(sampleQuery3);

        firstRecord = mySQLProductDao.findAll().get(0);
        firstRecordId = firstRecord.getId();
    }

    @Test
    @DisplayName("add() 메서드를 호출하면 하나의 데이터가 product에 추가된다")
    void add() {
        final ProductRequest request = new ProductRequest("test", 15_000, "test/image/url");
        final int beforeSize = mySQLProductDao.findAll().size();
        mySQLProductDao.add(request);
        final int afterSize = mySQLProductDao.findAll().size();
        assertThat(afterSize).isEqualTo(beforeSize + 1);
    }

    @Test
    @DisplayName("findAll() 메서드를 호출하면 3개의 초기 product 데이터를 반환한다")
    void findAll() {
        final List<ProductEntity> productEntities = mySQLProductDao.findAll();

        assertThat(productEntities.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("findById() 메서드를 호출하면 해당 id의 product 데이터를 반환한다")
    void findById() {
        // given

        // when
        final ProductEntity actual = mySQLProductDao.findById(firstRecordId).get();

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
        final int updateCount = mySQLProductDao.updateById(firstRecordId, updateRequest);

        // then
        final ProductEntity actual = mySQLProductDao.findById(firstRecordId).get();
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
        final int deleteCount = mySQLProductDao.deleteById(firstRecordId);
        final Optional<ProductEntity> deletedRecord = mySQLProductDao.findById(firstRecordId);

        // then
        assertAll(
            () -> assertThat(deletedRecord).isEmpty(),
            () -> assertThat(deleteCount).isEqualTo(1)
        );
    }
}
