package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.Member;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@DisplayName("MemberDao 테스트")
@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setup() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void insert() {
        //given
        Member member = new Member(null, null, "sdf@naver.com", "12345");
        //when
        assertThat(memberDao.insert(member)).isEqualTo(1L);
    }

    @Test
    void findAll() {
        List<Member> allMembers = memberDao.findAll();

        assertThat(allMembers).isNotNull();
    }

    @Test
    void findById() {
        assertThat(memberDao.findById(1L)).isEmpty();
    }

    @Test
    void updateMember() {
        //given
        Member savedMember = new Member(null, null, "sdf@naver.com", "12345");
        Long memberId = memberDao.insert(savedMember);

        //when
        Member memberToUpdate = new Member(memberId, "내이름은", "sdf@naver.com", "12345");
        memberDao.update(memberToUpdate);

        //then
        Member updatedMember = memberDao.findById(memberId).get();
        assertThat(updatedMember).usingRecursiveComparison().isEqualTo(memberToUpdate);
    }

    @Test
    void deleteMember() {
        //given
        Member product = new Member(null, null, "sdf@naver.com", "12345");
        Long memberId = memberDao.insert(product);
        assertThat(memberDao.findAll()).hasSize(1);

        //when
        memberDao.delete(memberId);

        //then
        assertThat(memberDao.findAll()).isEmpty();
    }
}
