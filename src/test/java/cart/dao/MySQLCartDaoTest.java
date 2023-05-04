package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import cart.dao.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/schema.sql", "/data.sql", "/cart_data.sql"})
@JdbcTest
public class MySQLCartDaoTest {

    private CartDao mySQLCartDao;
    private Long memberId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        mySQLCartDao = new MySQLCartDao(jdbcTemplate);

        memberId = 1L;
    }

    @Test
    @DisplayName("add() 메서드를 호출하면 하나의 데이터가 cart에 추가된다")
    void add() {
        // given, when
        final int beforeSize = mySQLCartDao.findByMemberId(memberId).size();
        mySQLCartDao.add(memberId, 3L);
        final int afterSize = mySQLCartDao.findByMemberId(memberId).size();

        // then
        assertThat(afterSize).isEqualTo(beforeSize + 1);
    }

    @Test
    @DisplayName("findByMember() 메서드를 호출하면 2개의 초기 cart 데이터를 반환한다")
    void findByMember() {
        // given, when
        final List<ProductEntity> productEntities = mySQLCartDao.findByMemberId(memberId);

        // then
        assertThat(productEntities.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("isExistEntity() 메서드를 호출하면 멤버가 해당 상품을 가지고있는지 여부를 반환한다")
    void isExistEntity() {
        // given, when
        final boolean actual = mySQLCartDao.isExistEntity(memberId, 1L);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("deleteById() 메서드를 호출하면 해당 멤버의 cart에 있는 상품을 제거한다")
    void deleteById() {
        // given, when
        final int deleteCount = mySQLCartDao.deleteById(memberId, 1L);
        final int afterSize = mySQLCartDao.findByMemberId(memberId).size();

        // then
        assertAll(
            () -> assertThat(deleteCount).isEqualTo(1),
            () -> assertThat(afterSize).isEqualTo(1)
        );
    }
}