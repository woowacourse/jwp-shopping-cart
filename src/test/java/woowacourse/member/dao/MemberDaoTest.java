package woowacourse.member.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.member.domain.Member;
import woowacourse.member.infrastructure.SHA256PasswordEncoder;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        Member member = createMember(EMAIL, PASSWORD, NAME);

        Long id = memberDao.save(member);
        assertThat(id).isNotNull();
    }

    @DisplayName("이메일을 중복 확인한다.")
    @Test
    void isEmailExist() {
        Member member = createMember(EMAIL, PASSWORD, NAME);
        memberDao.save(member);

        assertThat(memberDao.isEmailExist(EMAIL)).isTrue();
    }

    @DisplayName("이메일로 멤버를 조회한다.")
    @Test
    void findByEmail() {
        memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Member member = memberDao.findByEmail(EMAIL).get();

        assertThat(member.getName()).isEqualTo(NAME);
    }

    @DisplayName("이메일이 존재하지 않으면 empty를 반환한다.")
    @Test
    void findByEmailEmpty() {
        Optional<Member> member = memberDao.findByEmail(EMAIL);

        assertThat(member.isEmpty()).isTrue();
    }

    @DisplayName("id로 멤버를 조회한다.")
    @Test
    void findById() {
        Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Member member = memberDao.findById(id).get();

        assertThat(member.getName()).isEqualTo(NAME);
    }

    @DisplayName("id가 존재하지 않으면 empty를 반환한다.")
    @Test
    void findByIdEmpty() {
        Optional<Member> member = memberDao.findById(0L);

        assertThat(member.isEmpty()).isTrue();
    }

    @DisplayName("이름을 업데이트 한다.")
    @Test
    void updateName() {
        Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Member savedMember = memberDao.findById(id).get();
        savedMember.updateName("MARU");
        memberDao.updateName(savedMember);

        assertThat(memberDao.findById(id).get().getName()).isEqualTo("MARU");
    }

    @DisplayName("비밀번호를 업데이트 한다.")
    @Test
    void updatePassword() {
        Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Member savedMember = memberDao.findById(id).get();
        savedMember.updatePassword(PASSWORD, "Maru1234!", new SHA256PasswordEncoder());
        memberDao.updatePassword(savedMember);

        assertThat(memberDao.findById(id).get().getPassword())
                .isEqualTo(new SHA256PasswordEncoder().encode("Maru1234!"));
    }

    @DisplayName("멤버를 삭제한다.")
    @Test
    void deleteMember() {
        Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        memberDao.deleteById(id);

        assertThat(memberDao.findById(id).isEmpty()).isTrue();
    }
}
