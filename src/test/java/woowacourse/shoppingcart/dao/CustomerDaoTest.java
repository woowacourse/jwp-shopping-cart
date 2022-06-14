package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixture.페퍼;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

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
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("CustomerDao 클래스의")
class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @Nested
    @DisplayName("save 메서드는")
    class save {

        @Test
        @DisplayName("회원을 DB에 저장한다.")
        void success() {
            // given
            Customer customer = new Customer(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

            // when
            Long customerId = customerDao.save(customer);

            // then
            assertThat(customerId).isEqualTo(customerDao.findIdByUserName(페퍼_이름));
        }

        @Test
        @DisplayName("이미 존재하는 회원을 저장하면, 예외를 던진다.")
        void duplicatedLoginId() {
            // given
            customerDao.save(페퍼);

            // when & then
            assertThatThrownBy(() -> customerDao.save(페퍼))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 존재하는 아이디입니다.");
        }
    }

    @Nested
    @DisplayName("findIdByUserName 메서드는")
    class findIdByUserName {

        @Test
        @DisplayName("name을 통해 아이디를 찾으면, id를 반환한다.")
        void success() {
            // given
            final String userName = "puterism";

            // when
            final Long customerId = customerDao.findIdByUserName(userName);

            // then
            assertThat(customerId).isEqualTo(1L);
        }

        @Test
        @DisplayName("대소문자를 구별하지 않고 name을 통해 아이디를 찾으면, id를 반환한다.")
        void ignoreUpperLowerCase() {
            // given
            final String userName = "gwangyeol-iM";

            // when
            final Long customerId = customerDao.findIdByUserName(userName);

            // then
            assertThat(customerId).isEqualTo(16L);
        }

        @Test
        @DisplayName("저장되지 않은 name을 통해 아이디를 찾으면, 예외를 던진다.")
        void emptyResultData() {
            // given & when & then
            assertThatThrownBy(() -> customerDao.findIdByUserName(페퍼_이름))
                    .isInstanceOf(InvalidCustomerException.class);
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class findById {

        @Test
        @DisplayName("id로 고객을 조회한다.")
        void success() {
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

        @Test
        @DisplayName("저장되지 않은 id로 고객을 조회하면, 예외를 던진다.")
        void emptyResultData() {
            // given
            final Long id = customerDao.save(페퍼);
            customerDao.delete(페퍼_아이디);

            // when & then
            assertThatThrownBy(() -> customerDao.findById(id))
                    .isInstanceOf(InvalidCustomerException.class);
        }
    }

    @Nested
    @DisplayName("findByLoginId 메서드는")
    class findByLoginId {

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

        @Test
        @DisplayName("저장되지 않은 LoginId로 고객을 조회하면, 예외를 던진다.")
        void emptyResultData() {
            // given & when & then
            assertThatThrownBy(() -> customerDao.findByLoginId(페퍼_아이디))
                    .isInstanceOf(InvalidCustomerException.class);
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class update {

        @DisplayName("회원 정보를 수정한다.")
        @Test
        void success() {
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

        @Test
        @DisplayName("저장되지 않은 LoginId로 회원정보를 수정하면, 예외를 던진다.")
        void noUpdated() {
            // given & when & then
            assertThatThrownBy(() -> customerDao.update(페퍼))
                    .isInstanceOf(InvalidCustomerException.class);
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class delete {

        @DisplayName("회원 정보를 삭제한다.")
        @Test
        void success() {
            // given
            customerDao.save(페퍼);

            // when
            customerDao.delete(페퍼_아이디);

            // then
            assertThatThrownBy(() -> customerDao.findByLoginId(페퍼_아이디))
                    .isInstanceOf(InvalidCustomerException.class);
        }

        @DisplayName("저장되지 않은 LoginId로 회원정보를 삭제하면, 예외를 던진다.")
        @Test
        void noUpdated() {
            // given & when & then
            assertThatThrownBy(() -> customerDao.delete(페퍼_아이디))
                    .isInstanceOf(InvalidCustomerException.class);
        }
    }
}
