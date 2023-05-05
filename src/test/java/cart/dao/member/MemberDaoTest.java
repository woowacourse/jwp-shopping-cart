package cart.dao.member;

import static org.assertj.core.api.Assertions.assertThat;

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
        MemberEntity memberEntity = new MemberEntity(null, null, "sdf@naver.com", "12345");
        //when
        assertThat(memberDao.insert(memberEntity)).isEqualTo(1L);
    }

    @Test
    void findAll() {
        List<MemberEntity> allMembers = memberDao.findAll();

        assertThat(allMembers).isNotNull();
    }

    @Test
    void findById() {
        assertThat(memberDao.findById(1L)).isEmpty();
    }

    @Test
    void updateMember() {
        //given
        MemberEntity savedMember = new MemberEntity(null, null, "sdf@naver.com", "12345");
        Long memberId = memberDao.insert(savedMember);

        //when
        MemberEntity productToUpdate = new MemberEntity(memberId, "내이름은", "sdf@naver.com", "12345");
        memberDao.update(productToUpdate);

        //then
        MemberEntity updatedroduct = memberDao.findById(memberId).get();
        assertThat(updatedroduct).usingRecursiveComparison().isEqualTo(productToUpdate);
    }

    @Test
    void deleteMember() {
        //given
        MemberEntity product = new MemberEntity(null, null, "sdf@naver.com", "12345");
        Long memberId = memberDao.insert(product);
        assertThat(memberDao.findAll()).hasSize(1);

        //when
        memberDao.delete(memberId);

        //then
        assertThat(memberDao.findAll()).isEmpty();
    }
}
