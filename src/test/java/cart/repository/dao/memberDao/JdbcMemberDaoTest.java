package cart.repository.dao.memberDao;

import cart.entity.Member;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcMemberDaoTest {

    @Autowired
    private DataSource dataSource;

    private JdbcMemberDao jdbcMemberDao;

    @BeforeEach
    void setUp() {
        jdbcMemberDao = new JdbcMemberDao(dataSource);
    }

    @Test
    void 회원가입을_한다() {
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        Member member = new Member(email, name, password);

        Optional<Long> savedMemberId = jdbcMemberDao.save(member);

        assertThat(savedMemberId.isPresent()).isTrue();
    }

    @Test
    void 회원가입시_이메일이_중복되면_빈_옵셔널을_반환한다() {
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        Member member = new Member(email, name, password);

        jdbcMemberDao.save(member);

        assertThat(jdbcMemberDao.save(member).isEmpty()).isTrue();
    }

    @Test
    void 회원ID로_회원을_찾는다() {
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        Member member = new Member(email, name, password);

        Optional<Long> savedMemberId = jdbcMemberDao.save(member);
        Optional<Member> findMember = jdbcMemberDao.findById(savedMemberId.get());

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(findMember.isPresent()).isTrue();
        softAssertions.assertThat(findMember.get().getEmail()).isEqualTo(email);
        softAssertions.assertThat(findMember.get().getName()).isEqualTo(name);
        softAssertions.assertThat(findMember.get().getPassword()).isEqualTo(password);
        softAssertions.assertAll();
    }

    @Test
    void 없는_회원ID로_회원을_찾으면_빈_옵셔널을_반환한다() {
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        Member member = new Member(email, name, password);
        Optional<Long> savedMemberId = jdbcMemberDao.save(member);
        Long id = savedMemberId.get();

        assertThat(jdbcMemberDao.findById(id + 1).isEmpty()).isTrue();
    }

    @Test
    void 회원이메일로_회원을_찾는다() {
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        Member member = new Member(email, name, password);

        jdbcMemberDao.save(member);
        Optional<Member> findMember = jdbcMemberDao.findByEmail(email);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(findMember.isPresent()).isTrue();
        softAssertions.assertThat(findMember.get().getEmail()).isEqualTo(email);
        softAssertions.assertThat(findMember.get().getName()).isEqualTo(name);
        softAssertions.assertThat(findMember.get().getPassword()).isEqualTo(password);
        softAssertions.assertAll();
    }

    @Test
    void 없는_회원이메일로_회원을_찾으면_빈_옵셔널을_반환한다() {
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "1234";
        Member member = new Member(email, name, password);
        Optional<Long> savedMemberId = jdbcMemberDao.save(member);
        Long id = savedMemberId.get();

        assertThat(jdbcMemberDao.findByEmail(email + "random").isEmpty()).isTrue();
    }

    @Test
    void 모든회원을_찾는다() {
        String emailFirst = "ehdgur4814@naver.com";
        String nameFirst = "hardy";
        String passwordFirst = "1234";
        Member firstMember = new Member(emailFirst, nameFirst, passwordFirst);
        String emailSecond = "oz@naver.com";
        String nameSecond = "oz";
        String passwordSecond = "1234";
        Member secondMember = new Member(emailSecond, nameSecond, passwordSecond);

        jdbcMemberDao.save(firstMember);
        jdbcMemberDao.save(secondMember);
        List<Member> members = jdbcMemberDao.findAll();

        assertThat(members.size()).isEqualTo(2);
    }
}
