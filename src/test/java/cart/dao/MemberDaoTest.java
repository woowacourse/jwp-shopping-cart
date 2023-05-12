package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberDaoTest {

    MemberEntity testMemberEntity = new MemberEntity("testEmail@gmail.com", "test123");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("selectAllUsers 성공 테스트")
    @Test
    void selectAllUsers() {
        List<MemberEntity> memberEntities = memberDao.selectAllMembers();

        assertAll(
                () -> assertThat(memberEntities).hasSize(2),
                () -> assertThat(memberEntities).extracting("email", "password")
                        .contains(
                                tuple("dino96@naver.com", "jjongwa96"),
                                tuple("jeomxon@gmail.com", "jeomxon00")
                        )
        );
    }

    @DisplayName("findMemberId 성공 테스트")
    @Test
    void findMemberId() {
        int testMemberId = memberDao.addMember(testMemberEntity);

        int findMemberId = memberDao.findMemberId("testEmail@gmail.com", "test123");

        assertThat(testMemberId).isEqualTo(findMemberId);
    }

    @DisplayName("isMemberExist 성공 테스트")
    @Test
    void isMemberExist() {
        memberDao.addMember(testMemberEntity);

        assertThat(memberDao.isMemberExist("testEmail@gmail.com", "test123")).isTrue();
    }

    @DisplayName("isMemberExist 실패 테스트")
    @Test
    void failIsMemberExist() {
        assertAll(
                () -> assertThat(memberDao.isMemberExist("dinosour", "jjongwa96")).isFalse(),
                () -> assertThat(memberDao.isMemberExist("dino96@naver.com", "password")).isFalse()
        );
    }

}
