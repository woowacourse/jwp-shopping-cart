package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import cart.controller.dto.MemberRequest;
import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/schema.sql", "/data.sql"})
@JdbcTest
class MySQLMemberDaoTest {

    private static final String EMAIL = "songsy405@naver.com";
    private static final String PASSWORD = "abcd";

    private MemberDao mySQLMemberDao;
    private Long memberId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        mySQLMemberDao = new MySQLMemberDao(jdbcTemplate);
        memberId = 1L;
    }

    @Test
    @DisplayName("add() 메서드를 호출하면 하나의 데이터가 member에 추가된다")
    void add() {
        //given
        MemberRequest request = new MemberRequest(EMAIL, PASSWORD);

        //when
        final int beforeSize = mySQLMemberDao.findAll().size();
        mySQLMemberDao.add(request);
        final int afterSize = mySQLMemberDao.findAll().size();

        //then
        assertThat(afterSize).isEqualTo(beforeSize + 1);
    }

    @Test
    @DisplayName("findAll() 메서드를 호출하면 2개의 초기 member 데이터를 반환한다")
    void findAll() {
        //given
        final List<MemberEntity> productEntities = mySQLMemberDao.findAll();

        //when, then
        assertThat(productEntities.size()).isEqualTo(2);
    }


    @Test
    @DisplayName("findIdByMember() 메서드를 호출하면 회원의 아이디를 반환한다")
    void findIdByMember() {
        // given, when
        final Member member = new Member(EMAIL, PASSWORD);
        final Optional<Long> actual = mySQLMemberDao.findIdByMember(member);

        // then
        assertAll(
            () -> assertThat(actual.isPresent()).isTrue(),
            () -> assertThat(actual.get()).isEqualTo(memberId)
        );

    }

    @Test
    @DisplayName("존재하지 않는 회원에 대해 findIdByMember() 메서드를 호출하면 Null이 반환한다")
    void findIdByMember_fail() {
        // given, when
        final Member member = new Member(EMAIL, "1234");
        final Optional<Long> actual = mySQLMemberDao.findIdByMember(member);

        // then
        assertThat(actual.isEmpty()).isTrue();
    }
}