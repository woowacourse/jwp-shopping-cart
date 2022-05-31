package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
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

	@DisplayName("회원을 삭제한다")
	@Test
	void delete() {
		Customer save = customerDao.save(new Customer("123@gmail.com", "a1234!", "does"));
		customerDao.delete(save.getId());

		assertThat(customerDao.findByEmail(save.getEmail()))
			.isEmpty();
	}

	@DisplayName("회원 정보를 수정한다.")
	@Test
	void update() {
		// given
		Customer save = customerDao.save(new Customer("123@gmail.com", "a1234!", "does"));

		// when
		customerDao.update(new Customer(save.getId(), save.getEmail(), "b1234!", "thor"));

		// then
		Optional<Customer> update = customerDao.findByEmail(save.getEmail());
		assertAll(
			() -> assertThat(update)
				.map(Customer::getNickname)
				.get()
				.isEqualTo("thor"),
			() -> assertThat(update)
				.map(Customer::getPassword)
				.get()
				.isEqualTo("b1234!")
		);
	}

	@DisplayName("없는 회원을 수정하면 예외 발생.")
	@Test
	void updateException() {
		// when
		assertThatThrownBy(() -> customerDao.update(
			new Customer(1L, "123$gmail.com", "b1234!", "thor")))
			.isInstanceOf(NoSuchElementException.class);
	}
}
