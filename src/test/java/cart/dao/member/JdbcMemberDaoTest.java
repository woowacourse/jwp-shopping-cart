package cart.dao.member;

import cart.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Sql("classpath:schema.sql")
class JdbcMemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao.save(new MemberEntity("test@naver.com", "test", "01012345678", "qwer1234"));
        memberDao.save(new MemberEntity("test@gmail.com", "test", "01098765432", "qwer1234"));
    }

    @Test
    @DisplayName("단일 멤버 저장 테스트")
    void save() {
        MemberEntity member = new MemberEntity("test@kakao.com", "test", "01012345678", "qwer1234");
        MemberEntity resultMember = memberDao.save(member);
        Assertions.assertThat(resultMember).isEqualTo(resultMember);
    }

    @Test
    @DisplayName("모든 멤버 조회 테스")
    void findAll() {
        Optional<List<MemberEntity>> members = memberDao.findAll();
        List<MemberEntity> retrievedMembers = members.get();
        Assertions.assertThat(retrievedMembers).hasSize(2);
    }

    @Test
    @DisplayName("이메일 단일 멤버 조회 테스트")
    void findByEmail() {
        Optional<MemberEntity> member = memberDao.findByEmail("test@gmail.com");
        MemberEntity retrievedMember = member.get();
        Assertions.assertThat(retrievedMember.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @DisplayName("멤버 업데이트 테스트")
    void update() {
        MemberEntity member = new MemberEntity("test@gmail.com", "test0", "01012345678", "qwer1230");
        memberDao.update(member);
    }

    @Test
    @DisplayName("멤버 삭제 테스트")
    void delete() {
        memberDao.delete("test@gmail.com");
        Optional<List<MemberEntity>> members = memberDao.findAll();
        List<MemberEntity> retrievedMembers = members.get();
        Assertions.assertThat(retrievedMembers).hasSize(1);
    }
}
