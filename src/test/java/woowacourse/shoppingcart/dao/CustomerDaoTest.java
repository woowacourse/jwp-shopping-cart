package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CustomerDaoTest {

    private CustomerDao customerDao;

    public CustomerDaoTest(DataSource dataSource) {
        this.customerDao = new CustomerDao(dataSource);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void saveCustomer() {
        // given
        Customer customer = new Customer("email@email.com", "password123!A", "rookie");

        // when
        Long id = customerDao.save(customer);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("이메일과 패스워드를 통해서 회원을 조회할 수 있다.")
    void findByEmailAndPassword() {
        // given
        customerDao.save(new Customer("email@email.com", "password123!A", "rookie"));

        // when
        Customer customer = customerDao.findByEmail("email@email.com").get();

        // then
        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(new Customer(1L, "email@email.com", "password123!A", "rookie"));
    }

    @Test
    @DisplayName("비밀번호와 닉네임을 변경할 수 있다.")
    void update() {
        // given
        customerDao.save(new Customer("email@email.com", "password123!A", "rookie"));

        // when
        customerDao.update(new Customer(1L, "email@email.com", "password123@Q", "zero"));

        // then
        Customer customer = customerDao.findById(1L).get();
        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(new Customer(1L, "email@email.com", "password123@Q", "zero"));
    }

    @Test
    @DisplayName("닉네임 중복을 확인할 수 있다.")
    void checkDuplicatedNickname() {
        // given
        customerDao.save(new Customer("email1@email.com", "password123!A", "rookie"));
        customerDao.save(new Customer("email2@email.com", "password123!A", "zero"));

        // when & then
        assertAll(
                () -> assertThat(customerDao.existByNicknameExcludedId(1L, "rookie")).isFalse(),
                () -> assertThat(customerDao.existByNicknameExcludedId(2L, "rookie")).isTrue()
        );
    }

    @Test
    @DisplayName("회원을 삭제할 수 있다.")
    void delete() {
        // given
        customerDao.save(new Customer("email1@email.com", "password123!A", "rookie"));

        // when
        customerDao.delete(1L);

        // then
        assertThat(customerDao.findById(1L)).isEmpty();
    }
}
