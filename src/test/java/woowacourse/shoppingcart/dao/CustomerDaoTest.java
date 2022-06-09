package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
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
    private static final String PASSWORD = "Leo1234!";

    @BeforeEach
    void setUp() {
        customer = new Customer(EMAIL, NAME, PHONE, ADDRESS, PASSWORD);
    }

    @DisplayName("Customer 객체가 올바르게 저장되는지 확인한다.")
    @Test
    void save() {
        customerDao = new CustomerDao(jdbcTemplate);

        final Customer savedCustomer = customerDao.save(customer);

        assertThat(savedCustomer.getId()).isEqualTo(1L);
    }

    @DisplayName("Customer 이름으로 Customer의 Id를 조회한다.")
    @Test
    void findIdByUserName() {
        customerDao = new CustomerDao(jdbcTemplate);
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(customer));
        final Long id = customerDao.findIdByUserName(customer.getName());

        assertThat(id).isEqualTo(1L);
    }

    @DisplayName("Customer Email로 Customer를 조회한다.")
    @Test
    void findByEmail() {
        customerDao = new CustomerDao(jdbcTemplate);
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(customer));

        final Customer result = customerDao.findByEmail(customer.getEmail()).get();

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(result.getName()).isEqualTo(NAME),
                () -> assertThat(result.getPhone()).isEqualTo(PHONE),
                () -> assertThat(result.getAddress()).isEqualTo(ADDRESS)
        );
    }

    @DisplayName("Customer Id로 Customer를 조회한다.")
    @Test
    void findById() {
        customerDao = new CustomerDao(jdbcTemplate);
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(customer));

        final Customer result = customerDao.findById(1L);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(result.getName()).isEqualTo(NAME),
                () -> assertThat(result.getPhone()).isEqualTo(PHONE),
                () -> assertThat(result.getAddress()).isEqualTo(ADDRESS)
        );

    }

    @DisplayName("Customer Id를 통해 Customer를 수정한다.")
    @Test
    void edit() {
        customerDao = new CustomerDao(jdbcTemplate);
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(customer));

        Customer update = new Customer(1L, new Customer(EMAIL, "bunny", "010-9999-9999", "Seoul City", PASSWORD));

        customerDao.edit(update);
        Customer customer = customerDao.findById(1L);

        assertAll(
                () -> assertThat(customer.getId()).isEqualTo(1L),
                () -> assertThat(customer.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customer.getName()).isEqualTo("bunny"),
                () -> assertThat(customer.getPhone()).isEqualTo("010-9999-9999"),
                () -> assertThat(customer.getAddress()).isEqualTo("Seoul City")
        );
    }

    @DisplayName("Customer Id를 통해 Customer를 삭제한다.")
    @Test
    void delete() {
        customerDao = new CustomerDao(jdbcTemplate);
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(customer));

        assertDoesNotThrow(
                () -> customerDao.delete(1L)
        );
    }
}
