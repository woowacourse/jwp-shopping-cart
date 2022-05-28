package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import woowacourse.auth.domain.Member;

@JdbcTest
class MemberDaoTest {

	@Autowired
	private DataSource dataSource;
	private MemberDao memberDao;

	@BeforeEach
	void setUp() {
		memberDao = new MemberDao(dataSource);
	}

	@DisplayName("Member를 저장한다.")
	@Test
	void save() {
		// given
		Member member = new Member("123@gmail.com", "!234");

		// when
		Member saved = memberDao.save(member);

		// then
		assertThat(saved.getId()).isNotNull();
	}

	@DisplayName("저장된 이메일이 있는지 확인한다.")
	@Test
	void existByName() {
		// given
		Member member = new Member("123@gmail.com", "!234");
		memberDao.save(member);

		// when
		boolean result = memberDao.existByEmail(member.getEmail());

		// then
		assertThat(result).isTrue();
	}

	@DisplayName("저장된 이메일이 없는지 확인한다.")
	@Test
	void existByNameFalse() {
		// given
		Member member = new Member("123@gmail.com", "!234");

		// when
		boolean result = memberDao.existByEmail(member.getEmail());

		// then
		assertThat(result).isFalse();
	}
}
