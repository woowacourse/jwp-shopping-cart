package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
class MemberDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void insertSuccess() {
        Member member = new Member("aaa@gmail.com", "password", "그레이");

        Long savedId = memberDao.insert(member);

        assertThat(savedId).isNotNull();
    }

    @Test
    @DisplayName("지정된 id의 회원을 조회한다.")
    void findByIdSuccess() {
        Member member = new Member("aaa@gmail.com", "password", "그레이");
        Long savedId = memberDao.insert(member);

        MemberEntity findMember = memberDao.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        assertAll(
                () -> assertThat(findMember.getId()).isEqualTo(savedId),
                () -> assertThat(findMember.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(findMember.getPassword()).isEqualTo(member.getPassword()),
                () -> assertThat(findMember.getName()).isEqualTo(member.getName())
        );
    }

    @Test
    @DisplayName("이메일과 비밀번호로 회원을 조회한다.")
    void findByEmailAndPasswordSuccess() {
        Member member = new Member("aaa@gmail.com", "password", "그레이");
        Long savedId = memberDao.insert(member);

        MemberEntity findMember = memberDao.findByEmail(member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        assertAll(
                () -> assertThat(findMember.getId()).isEqualTo(savedId),
                () -> assertThat(findMember.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(findMember.getPassword()).isEqualTo(member.getPassword()),
                () -> assertThat(findMember.getName()).isEqualTo(member.getName())
        );
    }

    @Sql("/member_truncate.sql")
    @Test
    @DisplayName("모든 회원을 조회한다.")
    void findAllSuccess() {
        Member member1 = new Member("aaa@gmail.com", "password", "그레이");
        Member member2 = new Member("bbb@gmail.com", "helloWorld", "지토");
        Member member3 = new Member("ccc@gmail.com", "helloHello", "제이미");
        memberDao.insert(member1);
        memberDao.insert(member2);
        memberDao.insert(member3);

        List<MemberEntity> allMembers = memberDao.findAll();

        assertThat(allMembers).hasSize(3);
    }
}
