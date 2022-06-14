package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SuppressWarnings("NonAsciiChracters")
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql", "classpath:test.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerDaoTest {

    private final CustomerDao customerDao;
    private DataSource dataSource;

    public CustomerDaoTest(DataSource dataSource) {
        this.customerDao = new CustomerDao(dataSource);
    }

    @DisplayName("findIdByUserName 메서드는 username으로 해당 id를 찾는다")
    @Nested
    class FindIdByUserNameTest {

        @Test
        void username이_정상적이면_id를_반환_성공() {
            final String userName = "puterism";

            final Long customerId = customerDao.findIdByUserName(userName);

            assertThat(customerId).isEqualTo(1L);
        }

        @Test
        void 대소문자_구분없이_username으로_id를_반환_성공() {
            final String userName = "puterism";

            final Long customerId = customerDao.findIdByUserName(userName);

            assertThat(customerId).isEqualTo(1L);
        }

        @Test
        void 존재하지_않는_이름인_경우_예외발생() {
            final String userName = "invalidName";

            assertThatThrownBy(() -> customerDao.findIdByUserName(userName))
                .isInstanceOf(InvalidCustomerException.class);
        }
    }

    @DisplayName("findByLoginId메서드는 LoginId로 Customer를 찾는다")
    @Nested
    class FindByLoginIdTest {

        @Test
        void loginId가_정상적이면_Customor반환_성공() {
            final String loginId = "puterism@gmail.com";

            Customer customer = customerDao.findByLoginId(loginId)
                .orElseThrow();

            assertThat(customer).extracting("id", "loginId", "username")
                .containsExactly(1L, "puterism@gmail.com", "puterism");
        }

        @Test
        void loginId가_존재하지_않을_경우_empty를_반환() {
            final String loginId = "invalidLoginId";

            Optional<Customer> result = customerDao.findByLoginId(loginId);

            assertThat(result).isEmpty();
        }
    }

    @DisplayName("save메서드는 고객을 저장한다")
    @Nested
    class SaveTest {

        @Test
        void 데이터가_정상적인_경우_회원가입_성공() {
            final Customer customer = new Customer("seungpapang@gmail.com", "승팡", "12345678aA!");

            Customer result = customerDao.save(customer).orElseThrow();

            assertThat(result)
                .extracting("id", "loginId", "username", "password")
                .containsExactly(26L, "seungpapang@gmail.com", "승팡", "12345678aA!");
        }

        @Test
        void 이미_존재하는_고객일_경우_empty반환() {
            final Customer customer = new Customer("seungpapang@gmail.com", "승팡", "12345678aA!");
            customerDao.save(customer);

            Optional<Customer> result = customerDao.save(customer);

            assertThat(result).isEmpty();
        }
    }

    @DisplayName("update메서드는 고객정보를 업데이트한다")
    @Nested
    class UpdateTest {

        @Test
        void 정상적인_데이터인_경우_업데이트_성공() {
            final Customer customer = new Customer("seungpapang@gmail.com", "승팡", "12345678aA!");
            customerDao.save(customer);

            customerDao.update(new Customer("seungpapang@gmail.com", "승파팡", "12345678aA!"));
            Customer result = customerDao.findByLoginId("seungpapang@gmail.com").orElseThrow();

            assertThat(result).extracting("loginId", "username")
                .containsExactly("seungpapang@gmail.com", "승파팡");
        }

        @Test
        void 중복되는_이름으로_업데이트_할_경우_예외발생() {
            final Customer customer = new Customer("seungpapang@gmail.com", "승팡", "12345678aA!");
            final Customer otherCustomer = new Customer("seungpapang2@gmail.com", "승파팡", "12345678aA!");
            customerDao.save(customer);
            customerDao.save(otherCustomer);

            Customer updateCustomer = new Customer("seungpapang@gmail.com", "승파팡", "12345678aA!");

            assertThatThrownBy(() -> customerDao.update(updateCustomer))
                .isInstanceOf(DuplicateCustomerException.class);
        }
    }

    @DisplayName("delete메서드는_고객을_삭제한다")
    @Nested
    class Delete {

        @Test
        void 존재하는_유저를_삭제하는_경우_true() {
            assertThat(customerDao.delete(1L)).isTrue();
        }

        @Test
        void 존재하지_않는_유저를_삭제하는_경우_false() {
            assertThat(customerDao.delete(9999L)).isFalse();
        }
    }
}
