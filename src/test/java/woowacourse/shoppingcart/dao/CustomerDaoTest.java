package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.support.test.ExtendedJdbcTest;

@ExtendedJdbcTest
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        Customer customer = Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111");
        Long savedId = customerDao.save(customer).orElseThrow();

        Customer foundCustomer = customerDao.findById(savedId).orElseThrow();

        assertAll(
            () -> assertThat(foundCustomer.getId()).isEqualTo(savedId),
            () -> assertThat(customer.getName()).isEqualTo(foundCustomer.getName())
        );
    }

    @DisplayName("중복된 회원을 저장하면 empty를 얻는다.")
    @Test
    void throwsExceptionOnDuplicatedCustomerSave() {
        // given
        Customer customer = Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111");
        customerDao.save(customer);

        // when
        final Optional<Long> savedId = customerDao.save(customer);

        // then
        assertThat(savedId).isEmpty();
    }

    @DisplayName("ID값으로 회원을 조회한다.")
    @Test
    void findCustomerById() {
        // given
        Customer customer = Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111");
        Long savedId = customerDao.save(customer).orElseThrow();
        // when
        final Optional<Customer> foundCustomer = customerDao.findById(savedId);
        // then
        assertThat(foundCustomer).isPresent();
    }

    @DisplayName("이름으로 회원을 조회한다.")
    @Test
    void findCustomerByName() {
        // given
        Customer customer = Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111");
        Long savedId = customerDao.save(customer).orElseThrow();
        // when
        final Optional<Customer> foundCustomer = customerDao.findByName(new UserName("chleeslow"));
        // then
        assertThat(foundCustomer).isPresent();
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
        customerDao.deleteById(1L);

        assertThat(customerDao.findById(1L)).isEmpty();
    }

    @DisplayName("속성이 이미 존재하는지 검사한다")
    @Test
    void isDuplicated() {
        // given
        customerDao.save(
            Customer.fromInput("somename", "Password123!", "exmaple@email.com", "some-address", "010-1234-1234"));
        // when
        final boolean duplicated = customerDao.isDuplicated(CustomerDao.COLUMN_USERNAME, new UserName("somename"));
        // then
        assertThat(duplicated).isTrue();
    }
}
