package woowacourse.shoppingcart.unit.customer.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.exception.notfound.NotFoundCustomerException;
import woowacourse.shoppingcart.unit.DaoTest;

class CustomerDaoTest extends DaoTest {

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
