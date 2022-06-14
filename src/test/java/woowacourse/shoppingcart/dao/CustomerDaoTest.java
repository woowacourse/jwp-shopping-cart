package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private CustomerDao customerDao;
    private Customer customer;

    private static final String EMAIL = "leo@naver.com";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1111-1111";
    private static final String ADDRESS = "서울시 종로구 숭인동";
    private static final String PASSWORD = "Leo1234!1";

    @BeforeEach
    void setUp() {

        customer = new Customer(new Email(EMAIL), new Name(NAME), new Phone(PHONE), new Address(ADDRESS), Password.of(PASSWORD));
    }

    @DisplayName("Customer 객체가 올바르게 저장되는지 확인한다.")
    @Test
    void save() {
        customerDao = new CustomerDao(jdbcTemplate);

        final Customer savedCustomer = customerDao.save(customer);

        assertThat(savedCustomer.getId()).isEqualTo(new CustomerId(1L));
    }

    @DisplayName("Customer 이름으로 Customer의 Id를 조회한다.")
    @Test
    void findIdByUserName() {
        customerDao = new CustomerDao(jdbcTemplate);
        customerDao.save(customer);
        final CustomerId customerId = customerDao.findIdByUserName(customer.getName());

        assertThat(customerId).isEqualTo(new CustomerId(1L));
    }

    @DisplayName("Customer Email로 Customer를 조회한다.")
    @Test
    void findByEmail() {
        customerDao = new CustomerDao(jdbcTemplate);
        customerDao.save(customer);

        final Customer result = customerDao.findByEmail(customer.getEmail());

        assertThat(result.getId()).isEqualTo(new CustomerId(1L));
        assertThat(result.getEmail()).isEqualTo(new Email(EMAIL));
        assertThat(result.getName()).isEqualTo(new Name(NAME));
        assertThat(result.getPhone()).isEqualTo(new Phone(PHONE));
        assertThat(result.getAddress()).isEqualTo(new Address(ADDRESS));
    }

    @DisplayName("Customer Id로 Customer를 조회한다.")
    @Test
    void findById() {
        customerDao = new CustomerDao(jdbcTemplate);
        customerDao.save(customer);

        final Customer result = customerDao.findById(new CustomerId(1L));

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(new CustomerId(1L)),
                () -> assertThat(result.getEmail()).isEqualTo(new Email(EMAIL)),
                () -> assertThat(result.getName()).isEqualTo(new Name(NAME)),
                () -> assertThat(result.getPhone()).isEqualTo(new Phone(PHONE)),
                () -> assertThat(result.getAddress()).isEqualTo(new Address(ADDRESS))
        );

    }

    @DisplayName("Customer Id를 통해 Customer를 수정한다.")
    @Test
    void edit() {
        customerDao = new CustomerDao(jdbcTemplate);
        customerDao.save(customer);

        Customer update = new Customer(new CustomerId(1L), new Email(EMAIL), new Name("bunny"), new Phone("010-9999-9999"), new Address("Seoul City"), Password.of(PASSWORD));

        customerDao.save(update);
        Customer customer = customerDao.findById(new CustomerId(1L));

        assertAll(
                () -> assertThat(customer.getId()).isEqualTo(new CustomerId(1L)),
                () -> assertThat(customer.getEmail()).isEqualTo(new Email(EMAIL)),
                () -> assertThat(customer.getName()).isEqualTo(new Name("bunny")),
                () -> assertThat(customer.getPhone()).isEqualTo(new Phone("010-9999-9999")),
                () -> assertThat(customer.getAddress()).isEqualTo(new Address("Seoul City"))
        );
    }

    @DisplayName("Customer Id를 통해 Customer를 수정하고 새로운 Customer를 생성했을 때 정보가 올바른지 확인한다.")
    @Test
    void saveTwoCustomers() {
        customerDao = new CustomerDao(jdbcTemplate);
        customerDao.save(customer);

        Customer update = new Customer(new CustomerId(1L), new Email(EMAIL), new Name("bunny"), new Phone("010-9999-9999"), new Address("Seoul City"), Password.of(PASSWORD));
        customerDao.save(update);

        Customer newCustomer = new Customer(new Email("new@naver.com"), new Name(NAME), new Phone(PHONE), new Address(ADDRESS), Password.of(PASSWORD));
        customerDao.save(newCustomer);

        Customer result = customerDao.findByEmail(new Email("new@naver.com"));

        assertThat(result.getName()).isEqualTo(new Name(NAME));
    }

    @DisplayName("Customer Id를 통해 Customer를 삭제한다.")
    @Test
    void delete() {
        customerDao = new CustomerDao(jdbcTemplate);
        customerDao.save(customer);

        assertDoesNotThrow(
                () -> customerDao.delete(new CustomerId(1L))
        );
    }

    @DisplayName("email이 존재하는지 확인한다.")
    @Test
    void isDuplicationEmail() {
        customerDao = new CustomerDao(jdbcTemplate);
        customerDao.save(customer);
        final Boolean result = customerDao.isDuplication(new Email(EMAIL));

        assertThat(result).isTrue();
    }
}
