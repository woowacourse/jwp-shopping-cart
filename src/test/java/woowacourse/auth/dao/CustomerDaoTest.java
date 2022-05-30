package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

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
		Customer customer = new Customer("123@gmail.com", "!234", "does");

		// when
		Customer saved = customerDao.save(customer);

		// then
		assertThat(saved.getId()).isNotNull();
	}

	@DisplayName("저장된 이메일이 있는지 확인한다.")
	@Test
	void existByName() {
		// given
		Customer customer = new Customer("123@gmail.com", "!234", "does");
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
		Customer customer = new Customer("123@gmail.com", "!234", "does");

		// when
		boolean result = customerDao.existByEmail(customer.getEmail());

		// then
		assertThat(result).isFalse();
	}

	@DisplayName("이메일로 회원을 조회한다.")
	@Test
	void findByEmail() {
		// given
		Customer customer = new Customer("123@gmail.com", "!234", "does");
		customerDao.save(customer);

		// when
		Optional<Customer> byEmail = customerDao.findByEmail(customer.getEmail());

		// then
		assertThat(byEmail)
			.map(Customer::getEmail)
			.get()
			.isEqualTo("123@gmail.com");
	}

	@DisplayName("이메일에 맞는 회원이 없으면 empty 반환")
	@Test
	void findByEmailException() {
		// when
		Optional<Customer> byEmail = customerDao.findByEmail("123@gmail.com");

		// then
		assertThat(byEmail).isEmpty();
	}
}
