package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.PasswordFixture.plainReversePassword;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncryptPassword;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.CannotDeleteCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {
        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByUserName(userName);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {
        // given
        final String userName = "puterisM";

        // when
        final Long customerId = customerDao.findIdByUserName(userName);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("유저를 저장한다.")
    @Test
    void save() {
        // given
        final String userName = "tiki";
        final String password = "password";

        // when
        Long id = customerDao.save(userName, password);

        // then
        assertThat(id).isNotNull();
    }

    @DisplayName("주어진 이름으로 대소문자 관계없이 존재하는 유저가 있으면 참을 반환한다.")
    @Test
    void existsByUserName() {
        // given
        final String alreadyExistsName = "puterisM";

        // when
        final boolean result = customerDao.existsByUserName(alreadyExistsName);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("id를 통해서 customer정보를 가져온다.")
    @Test
    void findById() {
        // given
        final Long id = 1L;

        // when
        final Customer customer = customerDao.findById(id).get();

        // then
        assertThat(customer)
                .isEqualTo(new Customer(id, new UserName("puterism"), new EncryptPassword(plainBasicPassword)));
    }

    @DisplayName("유저의 정보를 수정한다.")
    @Test
    void update() {
        // given
        final Long id = 1L;
        final String userName = "puterism";

        // when
        final Customer customer = customerDao.update(id, userName, plainReversePassword);

        // then
        assertThat(customer)
                .isEqualTo(new Customer(id, new UserName("puterism"), new EncryptPassword(plainReversePassword)));
    }

    @DisplayName("유저의 정보를 수정할 때 해당 id로 유저가 존재하지 않으면 예외가 발생한다.")
    @Test
    void updateWithNotExistId() {
        // given
        final Long id = 0L;
        final String userName = "puterism";

        // when // then
        assertThatThrownBy(() -> customerDao.update(id, userName, plainReversePassword))
                .isExactlyInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("존재하지 않는 유저입니다.");
    }

    @DisplayName("유저의 id를 통해서 삭제한다.")
    @Test
    void deleteById() {
        // given
        final Long id = 1L;

        // when // then
        assertDoesNotThrow(() -> customerDao.deleteById(id));
    }

    @DisplayName("존재하지 않는 유저의 id를 통해서 삭제하려고 하면 예외가 발생한다.")
    @Test
    void deleteByIdWithWrongId() {
        // given
        final Long id = 0L;

        // when // then
        assertThatThrownBy(() -> customerDao.deleteById(id))
                .isExactlyInstanceOf(CannotDeleteCustomerException.class)
                .hasMessageContaining("회원 데이터가 존재하지 않아 삭제에 실패했습니다.");
    }

    @DisplayName("존재하는 유저이름으로 유저를 찾는다.")
    @Test
    void findByUserName() {
        // given
        final String userName = "puterism";

        //when
        final Customer customer = customerDao.findByUserName(userName).get();

        // then
        assertThat(customer)
                .isEqualTo(new Customer(1L, new UserName("puterism"), new EncryptPassword(plainBasicPassword)));
    }
}
