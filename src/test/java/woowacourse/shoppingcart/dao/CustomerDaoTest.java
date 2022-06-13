package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.values.password.EncryptedPassword;

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
        final String username = "puterism";

        final Long customerId = customerDao.findIdByUserName(username);

        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {
        final String username = "gwangyeol-iM";

        final Long customerId = customerDao.findIdByUserName(username);

        assertThat(customerId).isEqualTo(16L);
    }

    @Test
    @DisplayName("customer를 이름으로 검색할 수 있다.")
    void findByUsername() {
        Customer customer = Customer.builder()
                .username("username")
                .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();
        customerDao.save(customer);

        Customer findCustomer = customerDao.findByUsername("username");

        assertThat(findCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("customner를 저장하면 아이디를 반환한다.")
    void save() {
        Customer customer = Customer.builder()
                .username("username")
                .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();

        Long customerId = customerDao.save(customer);

        assertThat(customerId).isNotNull();
    }

    @Test
    @DisplayName("username이 이미 존재하는지 확인한다.")
    void existUsername() {
        Customer customer = Customer.builder()
                .username("username")
                .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();

        customerDao.save(customer);

        assertThat(customerDao.existCustomerByUsername("username")).isTrue();
    }

    @Test
    @DisplayName("유저 정보를 수정한다.")
    void update() {
        Customer customer = Customer.builder()
                .username("username")
                .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();
        Long customerId = customerDao.save(customer);
        Customer changedCustomer = Customer.builder()
                .id(customerId)
                .username("username")
                .phoneNumber("01087654321")
                .address("루터회관")
                .build();

        customerDao.update(changedCustomer);

        assertThat(customerDao.findById(changedCustomer.getId()))
                .usingRecursiveComparison()
                .ignoringFields("id", "password")
                .isEqualTo(changedCustomer);
    }

    @Test
    @DisplayName("유저 비밀번호를 수정한다.")
    void updatePassword() {
        Customer customer = Customer.builder()
                .username("username")
                .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();
        Long customerId = customerDao.save(customer);

        customerDao.updatePasswordById(customerId,
                new EncryptedPassword("47625ed74cab8fbc0a8348f3df1feb07f87601e34d62bd12eb0d51616566fab5"));

        assertThat(customerDao.findByUsername("username").getPassword())
                .isEqualTo("47625ed74cab8fbc0a8348f3df1feb07f87601e34d62bd12eb0d51616566fab5");
    }

    @Test
    @DisplayName("유저를 유저 이름으로 찾아 삭제한다.")
    void deleteByUsername() {
        Customer customer = Customer.builder()
                .username("username")
                .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();
        Long customerId = customerDao.save(customer);

        assertThat(customerDao.deleteById(customerId)).isEqualTo(1);
    }
}
