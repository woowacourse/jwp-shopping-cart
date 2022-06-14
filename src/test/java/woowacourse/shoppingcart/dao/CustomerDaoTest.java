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
import woowacourse.shoppingcart.domain.customer.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("이메일이 존재하지 않으면 false를 반환한다.")
    @Test
    void existEmail_notExist_falseReturned() {
        // given
        String email = "kun@naver.com";

        // when
        boolean actual = customerDao.existEmail(email);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("이메일이 존재하면 true를 반환한다.")
    @Test
    void existEmail_exist_trueReturned() {
        // given
        String email = "email1@email.com";

        // when
        boolean actual = customerDao.existEmail(email);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("Customer를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        Customer customer = new Customer("kun", "kun@email.com", "asdfqer123");

        // when
        Long actual = customerDao.save(customer);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("email에 해당하는 데이터가 존재하지 않으면 예외를 발생시킨다.")
    void findByEmail_notExistEmail_exceptionThrown() {
        // given
        String email = "kun@naver.com";

        // when, then
        assertThatThrownBy(() -> customerDao.findByEmail(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("email에 해당하는 데이터가 존재하면 Customer를 반환한다.")
    void findByEmail_existEmail_customerReturned() {
        // given
        String email = "email1@email.com";

        // when
        Customer actual = customerDao.findByEmail(email);

        // then
        assertThat(actual.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Id에 해당하는 Customer를 수정한다.")
    void updateById_matchId_void() {
        // given
        String email = "kun@email.com";
        Customer customer = new Customer("kun", email, "qwerasdf123");

        Long id = customerDao.save(customer);
        Customer updatedCustomer = new Customer("rick", email, "qwerasdf321");

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
        String email = "kun@email.com";
        Customer customer = new Customer("kun", email, "qwerasdf123");
        Long id = customerDao.save(customer);

        // when
        customerDao.deleteById(id);

        // then
        assertThatThrownBy(() -> customerDao.findByEmail(email))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
