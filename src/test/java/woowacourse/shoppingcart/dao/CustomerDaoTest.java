package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.customerDao = new CustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("customer를 등록한다.")
    @Test
    void saveCustomer() {
        Customer customer = new Customer(
                "email", "Pw123456!", "name", "010-1234-5678", "address");
        Long customerId = customerDao.save(customer);
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("email과 password가 일치하는 customer의 id를 반환한다.")
    @Test
    void findIdByEmailAndPassword() {
        Customer customer = new Customer(
                "email", "Pw123456!", "name", "010-1234-5678", "address");
        Long customerId = customerDao.save(customer);

        Long responseId = customerDao.findIdByEmailAndPassword(
                "email", Password.from("Pw123456!").getPassword()).get();

        assertThat(responseId).isEqualTo(customerId);
    }

    @DisplayName("email이 존재하는지 확인한다.")
    @Test
    void existEmail() {
        //given
        String email = "email";
        Customer customer = new Customer(
                email, "Pw123456!", "name", "010-1234-5678", "address");
        customerDao.save(customer);

        //when
        boolean isExistEmail = customerDao.existByEmail(email);

        //then
        assertThat(isExistEmail).isTrue();
    }

    @DisplayName("id가 일치하는 customer를 반환한다.")
    @Test
    void findCustomerById() {
        //given
        Customer customer = new Customer(
                "email", "Pw123456!", "name", "010-1234-5678", "address");
        Long customerId = customerDao.save(customer);

        //when
        Customer response = customerDao.findById(customerId);

        //then
        assertThat(response).extracting("email", "password", "name", "phone", "address")
                .containsExactly(
                        "email",
                        Password.from("Pw123456!").getPassword(),
                        "name", "010-1234-5678",
                        "address");
    }

    @DisplayName("id가 존재하는지 확인한다.")
    @Test
    void existId() {
        //given
        Customer customer = new Customer(
                "email", "Pw123456!", "name", "010-1234-5678", "address");
        Long id = customerDao.save(customer);

        //when
        boolean isExistId = customerDao.existById(id);

        //then
        assertThat(isExistId).isTrue();
    }

    @DisplayName("name과 password, phone, address를 받아 customer를 수정한다.")
    @Test
    void updateCustomer() {
        //given
        Customer customer = new Customer(
                "email", "Pw123456!", "name", "010-1234-5678", "address");
        Long id = customerDao.save(customer);

        //when
        Customer updateCustomer = new Customer(
                id, "email", "Pw123456!!", "name2", "010-1234-1234", "address2");
        customerDao.update(updateCustomer);

        //then
        Customer actual = customerDao.findById(id);
        assertThat(actual).extracting("email", "password", "name", "phone", "address")
                .containsExactly(
                        "email",
                        Password.from("Pw123456!!").getPassword(),
                        "name2", "010-1234-1234", "address2");
    }

    @DisplayName("id를 이용하여 customer를 삭제한다.")
    @Test
    void delete() {
        //given
        Customer customer = new Customer(
                "email", "Pw123456!", "name", "010-1234-5678", "address");
        Long id = customerDao.save(customer);

        //when
        customerDao.deleteById(id);

        //then
        assertThat(customerDao.existById(id)).isFalse();
    }
}
