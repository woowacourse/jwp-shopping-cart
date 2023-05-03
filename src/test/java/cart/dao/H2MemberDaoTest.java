package cart.dao;

import cart.domain.member.Member;
import cart.domain.member.MemberDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
public class H2MemberDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    MemberDao memberDao;

    @BeforeEach
    public void setUp() {
        this.memberDao = new H2MemberDao(jdbcTemplate);
    }

    @Test
    public void 멤버가_정상적으로_생성된다() {
        Member member = new Member("cyh6099@gmail.com", "qwer1234");
        Member createdMember = memberDao.save(member);

        assertAll(
                () -> assertThat(createdMember).isNotNull(),
                () -> assertThat(createdMember.getId()).isPositive(),
                () -> assertThat(createdMember.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(createdMember.getPassword()).isEqualTo(member.getPassword())
        );
    }

    @Test
    public void 전체_멤버를_조회한다() {
//        Member member1 = new Member("cyh6099@gmail.com", "qwer1234");
//        Member member2 = new Member("cyh6099@wooteco.com", "qwer1234");
//
//        memberDao.save(member1);
//        memberDao.save(member2);

        assertThat(memberDao.findAll()).hasSize(2);
    }

    @Test
    public void 이메일로_멤버를_조회한다() {
        String email = "cyh6099@gmail.com";
        Member member = new Member(email, "qwer1234");
        memberDao.save(member);

        assertAll(
                () -> assertThat(memberDao.findByEmail(email)).isNotEmpty(),
                () -> assertThat(memberDao.findByEmail(email).get().getPassword()).isEqualTo("qwer1234")
        );

    }
}
