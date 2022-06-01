package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixture.페퍼;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    @DisplayName("회원을 DB에 저장한다.")
    void save() {
        // given
        Customer customer = new Customer(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

        // when
        Long customerId = customerDao.save(customer);

        // then
        assertThat(customerId).isEqualTo(customerDao.findIdByUserName(페퍼_이름));
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
        final String userName = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByUserName(userName);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @DisplayName("id로 고객을 조회한다.")
    @Test
    void findById() {
        // given
        final Long id = customerDao.save(페퍼);

        // when
        Customer customer = customerDao.findById(id);

        // then
        assertAll(
                () -> assertThat(customer.getLoginId()).isEqualTo(페퍼_아이디),
                () -> assertThat(customer.getName()).isEqualTo(페퍼_이름)
        );
    }

    @DisplayName("LoginId로 고객을 조회한다.")
    @Test
    void findByLoginId() {
        // given
        customerDao.save(페퍼);

        // when
        Customer customer = customerDao.findByLoginId(페퍼_아이디);

        // then
        assertThat(customer.getName()).isEqualTo(페퍼_이름);
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void update() {
        //given
        customerDao.save(페퍼);

        //when
        customerDao.update(new Customer(페퍼_아이디, "바꿀 이름", 페퍼_비밀번호));

        //then
        Customer findCustomer = customerDao.findByLoginId(페퍼_아이디);
        assertAll(
                () -> assertThat(findCustomer.getLoginId()).isEqualTo(페퍼_아이디),
                () -> assertThat(findCustomer.getName()).isEqualTo("바꿀 이름"),
                () -> assertThat(findCustomer.getPassword()).isEqualTo(페퍼_비밀번호)
        );
    }

    @DisplayName("회원 정보를 삭제한다.")
    @Test
    void delete() {
        // given
        customerDao.save(페퍼);

        // when
        customerDao.delete(페퍼_아이디);

        // then
        Assertions.assertThatThrownBy(() -> customerDao.findByLoginId(페퍼_아이디))
                .isInstanceOf(InvalidCustomerException.class);
    }
}
