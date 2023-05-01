package cart.member.dao;

import cart.member.domain.Member;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Member member = new Member("email@email.com", "password", "0100100100");

        Member inserted = memberDao.save(member);

        assertThat(memberDao.findAll()).contains(inserted);
    }

    @Test
    @DisplayName("이메일이 중복이 되면 예외를 발생시킨다")
    void duplciateEmail() {
        Member member = new Member("email@email.com", "password", "0100100100");
        memberDao.save(member);
        Member duplicateEmailMember = new Member(member.getEmail(), "password2", "0000000000");

        final ThrowingCallable throwingCallable = () -> memberDao.save(duplicateEmailMember);

        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("member를 수정한다")
    void update() {
        Member member = new Member("email@email.com", "password", "0100100100");
        Member inserted = memberDao.save(member);

        Member updateMember = new Member(inserted.getId(), "email2@email.com", "password2", "0000000000");
        memberDao.update(updateMember);

        assertThat(memberDao.findById(inserted.getId()).get()).isEqualTo(updateMember);
    }

    @Test
    @DisplayName("member를 id로 조회한다")
    void findById() {
        Member member = new Member("email@email.com", "password", "0100100100");
        Member inserted = memberDao.save(member);

        final Optional<Member> found = memberDao.findById(inserted.getId());

        assertThat(found.get()).isEqualTo(inserted);
    }

    @Test
    @DisplayName("member를 email과 비밀번호로 조회한다")
    void findByEmailWithPassword() {
        Member member = new Member("email@email.com", "password", "0100100100");
        Member inserted = memberDao.save(member);

        final Optional<Member> found = memberDao.findByEmailWithPassword(inserted.getEmail(), inserted.getPassword());

        assertThat(found.get()).isEqualTo(inserted);
    }

    @ParameterizedTest(name = "member가 없으면 empty optional을 반환한다")
    @CsvSource(value = {"null,password", "email@email.com,null", "email@email.com,Password"}, nullValues = "null")
    void findByEmailWithPasswordEmpty(String email, String password) {
        Member member = new Member("email@email.com", "password", "0100100100");

        Member inserted = memberDao.save(member);

        assertThat(memberDao.findByEmailWithPassword(email, password).isEmpty()).isTrue();
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
