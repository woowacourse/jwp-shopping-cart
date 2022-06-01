package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.notfound.NotFoundCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(final DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    void 사용자_저장() {
        // when
        customerDao.save(new Customer("ellie", "12345678"));

        // then
        final Customer customer = customerDao.getByName("ellie");
        assertAll(
                () -> assertThat(customer.getId()).isNotNull(),
                () -> assertThat(customer.getUserName()).isEqualTo("ellie"),
                () -> assertThat(customer.getPassword()).isEqualTo("12345678")
        );
    }

    @Test
    void 이름으로_사용자_ID_조회() {
        // given
        final Long expectedId = customerDao.save(new Customer("ellie", "12345678"));

        // when
        final Long actualId = customerDao.getIdByUserName("ellie");

        // then
        assertThat(actualId).isEqualTo(expectedId);
    }

    @Test
    void 대소문자를_구분하지_않고_이름으로_사용자_ID_조회() {
        // given
        final Long expectedId = customerDao.save(new Customer("ellie", "12345678"));

        // when
        final Long actualId = customerDao.getIdByUserName("ellie");

        // then
        assertThat(actualId).isEqualTo(expectedId);
    }

    @Test
    void 사용자_삭제() {
        // given
        customerDao.save(new Customer("ellie", "12345678"));

        // when
        customerDao.deleteByName("ellie");

        // then
        assertThatThrownBy(() -> customerDao.getByName("ellie"))
                .isInstanceOf(NotFoundCustomerException.class);
    }
}
