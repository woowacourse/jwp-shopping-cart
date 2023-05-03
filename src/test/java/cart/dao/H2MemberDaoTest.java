package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@JdbcTest
class H2MemberDaoTest {

  private final MemberEntity memberEntity = new MemberEntity("test", "test");

  private H2MemberDao h2MemberDao;
  private SimpleJdbcInsert simpleJdbcInsert;
  @Autowired
  private NamedParameterJdbcTemplate namedParameterjdbcTemplate;

  @BeforeEach
  void setUp() {
    simpleJdbcInsert = new SimpleJdbcInsert(namedParameterjdbcTemplate.getJdbcTemplate())
        .withTableName("member")
        .usingGeneratedKeyColumns("id");
    h2MemberDao = new H2MemberDao(namedParameterjdbcTemplate);
  }

  @Test
  void findAll() {
    //given
    saveMember();
    saveMember();

    //when
    final List<MemberEntity> members = h2MemberDao.findAll();

    //then
    assertThat(members.size()).isEqualTo(2);
  }

  private Long saveMember() {
    final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(memberEntity);
    return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
  }

  @Test
  void findByMemberEntity() {
    //given
    saveMember();
    final MemberEntity MemberEntity = new MemberEntity("test", "test");

    //when
    final MemberEntity findMember = h2MemberDao.findByMemberEntity(MemberEntity).get();

    //then
    assertThat(findMember).usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(MemberEntity);
  }
}
