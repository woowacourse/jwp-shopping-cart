package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:testData.sql")
class MemberJdbcDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void init() {
        memberDao = new MemberJdbcDao(jdbcTemplate);
    }

    @DisplayName("전체 멤버 조회 테스트")
    @Test
    void findAllMember() {
        // data.sql 멤버 네명 등록
        // when
        List<MemberEntity> findMembers = memberDao.findAll();

        // then
        assertThat(findMembers).hasSize(4);
    }

    @DisplayName("멤버 아이디로 특정 멤버 조회 테스트")
    @Test
    void findMemberById() {
        // data.sql 멤버 네명 등록
        List<MemberEntity> findMembers = memberDao.findAll();
        MemberEntity findFirstMember = findMembers.get(0);
        int firstMemberId = findFirstMember.getId();

        // when
        Optional<MemberEntity> findMember = memberDao.findMemberById(firstMemberId);

        // then
        assertThat(findMember.isEmpty()).isFalse();
        assertThat(findMember.get().getEmail()).isEqualTo(findFirstMember.getEmail());
        assertThat(findMember.get().getPassword()).isEqualTo(findFirstMember.getPassword());
    }

    @DisplayName("없는 멤버 조회 테스트")
    @Test
    void findEmptyMember() {
        // when
        Optional<MemberEntity> findMember = memberDao.findMemberById(1000);

        // then
        assertThat(findMember.isEmpty()).isTrue();
    }

}
