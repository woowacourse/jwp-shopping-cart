package cart.dao;

import static cart.fixture.MemberFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import cart.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("멤버를 저장한다.")
    void insert() {
        // when
        long insertedId = memberDao.insert(INSERT_MEMBER_ENTITY);

        // then
        assertThat(insertedId).isNotNull();
    }

    @Test
    @DisplayName("저장된 멤버를 모두 조회한다.")
    void selectAll() {
        // given
        long insertedId = memberDao.insert(INSERT_MEMBER_ENTITY);

        // when
        List<MemberEntity> members = memberDao.selectAll();
        MemberEntity member1 = members.get(0);

        // then
        assertAll(
                () -> assertThat(members).hasSize(1),
                () -> assertThat(member1.getMemberId()).isEqualTo(insertedId)
        );
    }

    @Test
    @DisplayName("닉네임으로 멤버 조회 시 행이 존재하면 TRUE를 반환한다.")
    void isExistByNickname() {
        // given
        memberDao.insert(INSERT_MEMBER_ENTITY);

        // when, then
        assertThat(memberDao.isExistByNickname(DUMMY_NICKNAME)).isTrue();
    }

    @Test
    @DisplayName("이메일으로 멤버 조회 시 행이 존재하면 TRUE를 반환한다.")
    void isExistByEmail() {
        // given
        memberDao.insert(INSERT_MEMBER_ENTITY);

        // when, then
        assertThat(memberDao.isExistByEmail(DUMMY_EMAIL)).isTrue();
    }

    @Test
    @DisplayName("이메일과 비밀번호로 멤버 조회 시 행이 존재하지 않으면 TRUE를 반환한다.")
    void isExistByEmailAndPassword() {
        // given
        memberDao.insert(INSERT_MEMBER_ENTITY);

        // when, then
        assertThat(memberDao.isNotExistByEmailAndPassword("new" + DUMMY_EMAIL, "new" + DUMMY_PASSWORD)).isTrue();
    }
}
