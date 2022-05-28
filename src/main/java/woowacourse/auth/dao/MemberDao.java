package woowacourse.auth.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.auth.domain.Member;

@Repository
public class MemberDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("member")
			.usingGeneratedKeyColumns("id");
	}

	public Member save(Member member) {
		long id = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(member))
			.longValue();
		return new Member(id, member.getEmail(), member.getPassword());
	}

	public Boolean existByEmail(String email) {
		String sql = "select exists (select * from member where email = :email)";
		return jdbcTemplate.queryForObject(sql, Map.of("email", email), Boolean.class);
	}
}
