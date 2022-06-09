package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @DisplayName("name을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByNameTest() {

        // given
        final String name = "puterism";

        // when
        final Long customerId = customerDao.findIdByName(name);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 name을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String name = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByName(name);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        Customer customer = new Customer("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111");
        Long savedId = customerDao.save(customer).orElseThrow();

        Customer foundCustomer = customerDao.findById(savedId).orElseThrow();

        assertAll(
                () -> assertThat(foundCustomer.getId()).isEqualTo(savedId),
                () -> assertThat(customer.getName()).isEqualTo(foundCustomer.getName())
        );
    }

    @DisplayName("회원을 수정한다.")
    @Test
    void updateCustomer() {
        Long id = 1L;
        Customer foundCustomer = customerDao.findById(id).orElseThrow();
        Customer updatingCustomer = foundCustomer.update("some-address", "010-9999-9999");
        customerDao.update(updatingCustomer);

        Customer updatedCustomer = customerDao.findById(id).orElseThrow();

        assertAll(
                () -> assertThat(updatedCustomer.getAddress()).isEqualTo("some-address"),
                () -> assertThat(updatedCustomer.getPhoneNumber()).isEqualTo("010-9999-9999")
        );
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void deleteCustomer() {
        final String username = "chleeslow";
        final Customer customer = new Customer(username, "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111");
        final Long savedId = customerDao.save(customer).orElseThrow();

        customerDao.deleteByName(username);

        assertThat(customerDao.findById(savedId)).isEmpty();
    }
}
