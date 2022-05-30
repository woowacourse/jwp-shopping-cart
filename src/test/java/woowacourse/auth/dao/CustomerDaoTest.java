package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import woowacourse.auth.domain.Customer;

@JdbcTest
class CustomerDaoTest {

	@Autowired
	private DataSource dataSource;
	private CustomerDao customerDao;

	@BeforeEach
	void setUp() {
		customerDao = new CustomerDao(dataSource);
	}

	@DisplayName("Member를 저장한다.")
	@Test
	void save() {
		// given
		Customer customer = new Customer("123@gmail.com", "!234");

		// when
		Customer saved = customerDao.save(customer);

		// then
		assertThat(saved.getId()).isNotNull();
	}

	@DisplayName("저장된 이메일이 있는지 확인한다.")
	@Test
	void existByName() {
		// given
		Customer customer = new Customer("123@gmail.com", "!234");
		customerDao.save(customer);

		// when
		boolean result = customerDao.existByEmail(customer.getEmail());

		// then
		assertThat(result).isTrue();
	}

	@DisplayName("저장된 이메일이 없는지 확인한다.")
	@Test
	void existByNameFalse() {
		// given
		Customer customer = new Customer("123@gmail.com", "!234");

		// when
		boolean result = customerDao.existByEmail(customer.getEmail());

		// then
		assertThat(result).isFalse();
	}
}
