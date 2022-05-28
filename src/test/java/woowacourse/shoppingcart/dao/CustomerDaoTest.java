package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    @DisplayName("이메일, 암호화 된 패스워드, 닉네임을 받아서 Customer 테이블에 저장한다.")
    void save() {
        final Customer testCustomer = Customer.createWithoutId("test@test.com", "testtest", "테스트");
        final Long createdMemberId = customerDao.save(testCustomer);

        final Customer findCustomer = customerDao.findById(createdMemberId).get();

        assertThat(findCustomer.getId()).isEqualTo(createdMemberId);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String username = "puterism";

        // when
        final Long customerId = customerDao.findByUsername(username);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String username = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findByUsername(username);

        // then
        assertThat(customerId).isEqualTo(16L);
    }
}