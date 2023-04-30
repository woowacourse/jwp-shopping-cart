package cart.member.dao;

import cart.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class MemberDaoTest {

    private final MemberDao memberDao;

    @Autowired
    MemberDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws SQLException {
        System.out.println(namedParameterJdbcTemplate.getJdbcTemplate().getDataSource().getConnection().getMetaData());
        this.memberDao = new MemberDao(namedParameterJdbcTemplate);
    }

    @Test
    @DisplayName("member를 저장한다")
    void insert() {
        // given
        Member member = new Member("email@email.com", "password", "0100100100");

        // when
        Member inserted = memberDao.save(member);

        // then
        assertThat(memberDao.findAll()).contains(inserted);
    }

    @Test
    @DisplayName("member를 수정한다")
    void update() {
        // given
        Member member = new Member("email@email.com", "password", "0100100100");
        Member inserted = memberDao.save(member);

        // when
        Member updated = new Member(inserted.getId(), "email2@email.com", "password2", "0000000000");
        memberDao.update(updated);

        // then
        assertThat(memberDao.findById(inserted.getId()).get()).isEqualTo(updated);
    }

    @Test
    @DisplayName("member를 id로 조회한다")
    void findById() {
        // given
        Member member = new Member("email@email.com", "password", "0100100100");

        // when
        Member inserted = memberDao.save(member);

        // then
        assertThat(memberDao.findById(inserted.getId()).get()).isEqualTo(inserted);
    }

    @Test
    @DisplayName("member를 id로 삭제한다")
    void deleteById() {
        // given
        Member member = new Member("email@email.com", "password", "0100100100");
        Member inserted = memberDao.save(member);

        // when
        final int deleted = memberDao.deleteById(inserted.getId());

        // then
        assertThat(memberDao.findById(deleted).isEmpty()).isTrue();
    }
}
