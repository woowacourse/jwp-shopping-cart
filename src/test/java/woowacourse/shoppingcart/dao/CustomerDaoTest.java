package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.fixture.CustomerFixture;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    @Autowired
    private DataSource dataSource;

    private CustomerDao customerDao;

    @BeforeEach
    public void setUp() {
        customerDao = new CustomerDao(dataSource);
    }

    @DisplayName("전달받은 데이터로 소비자 데이터를 생성한다.")
    @Test
    void createCustomer() {
        assertThatNoException().isThrownBy(() -> customerDao.createCustomer(CustomerFixture.tommy));
    }

    @DisplayName("전달 받은 이메일로 사용자 정보를 반환한다.")
    @Test
    void findIdByUserEmail() {
        final Long customerId = customerDao.findIdByEmail(new Email("puterism@example.com"));
        assertThat(customerId).isNotNull();
    }

    @DisplayName("전달받은 데이터를 소비자 데이터를 업데이트한다.")
    @Test
    void updateCustomer() {
        Long customerId = customerDao.createCustomer(CustomerFixture.tommy);
        customerDao.updateCustomer(customerId, CustomerFixture.updatedTommyDto);
        CustomerResponse updatedCustomer = customerDao.findByUserEmail(new Email(CustomerFixture.tommy.getEmail()));
        assertThat(updatedCustomer.getContact())
                .isEqualTo(CustomerFixture.updatedTommyDto.getContact());
    }
}
