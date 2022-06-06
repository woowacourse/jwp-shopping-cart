package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.exception.notfound.NotFoundCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(final JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    @DisplayName("nickname을 통해 아이디를 찾으면, id를 반환한다.")
    void findIdByNickNameTest() {
        // given
        final String nickname = "puterism";

        // when
        final Long customerId = customerDao.findInByNickname(nickname);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @Test
    @DisplayName("대소문자를 구별하지 않고 nickname을 통해 아이디를 찾으면, id를 반환한다.")
    void findIdByUserNameTestIgnoreUpperLowerCase() {
        // given
        final String userName = "gwangyeo";

        // when
        final Long customerId = customerDao.findInByNickname(userName);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @Test
    @DisplayName("이메일이 존재하지 않으면 false를 반환한다.")
    void existEmail_notExist_falseReturned() {
        // given
        final String email = "kun@naver.com";

        // when
        final boolean actual = customerDao.existEmail(email);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("이메일이 존재하면 true를 반환한다.")
    void existEmail_exist_trueReturned() {
        // given
        final String email = "email1@email.com";

        // when
        final boolean actual = customerDao.existEmail(email);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Customer를 저장하면, id를 반환한다.")
    void save() {
        // given
        final Customer customer = new Customer("kun", "kun@email.com", "asdfqer123");

        // when
        final Long actual = customerDao.save(customer);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("email에 해당하는 데이터가 존재하지 않으면 예외를 발생시킨다.")
    void findByEmail_notExistEmail_exceptionThrown() {
        // given
        final String email = "kun@naver.com";

        // when, then
        assertThatThrownBy(() -> customerDao.findByEmail(email))
                .isInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    @DisplayName("email에 해당하는 데이터가 존재하면 Customer를 반환한다.")
    void findByEmail_existEmail_customerReturned() {
        // given
        final String email = "email1@email.com";

        // when
        final Customer actual = customerDao.findByEmail(email);

        // then
        assertThat(actual.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Id에 해당하는 Customer를 수정한다.")
    void updateById_matchId_void() {
        // given
        final String email = "kun@email.com";
        final Customer customer = new Customer("kun", email, "qwerasdf123");

        final Long id = customerDao.save(customer);
        final Customer updatedCustomer = new Customer("rick", email, "qwerasdf321");

        // when
        customerDao.updateById(id, updatedCustomer);

        // then
        assertThat(customerDao.findByEmail(email))
                .isEqualTo(updatedCustomer);
    }

    @Test
    @DisplayName("Id에 해당하는 Customer를 삭제한다.")
    void deleteById_existId_void() {
        // given
        final String email = "kun@email.com";
        final Customer customer = new Customer("kun", email, "qwerasdf123");
        final Long id = customerDao.save(customer);

        // when
        customerDao.deleteById(id);

        // then
        assertThatThrownBy(() -> customerDao.findByEmail(email))
                .isInstanceOf(NotFoundCustomerException.class);
    }
}
