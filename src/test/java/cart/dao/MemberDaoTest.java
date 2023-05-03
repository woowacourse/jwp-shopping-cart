package cart.dao;

import cart.dto.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class MemberDaoTest {
    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("사용자를 저장할 수 있다.")
    void save() {
        MemberEntity member = memberDao.save(new MemberEntity("eastsea@eastsea", "eastsea"));

        assertAll(
                () -> assertThat(member.getEmail()).isEqualTo("eastsea@eastsea"),
                () -> assertThat(member.getPassword()).isEqualTo("eastsea")
        );
    }

    @Test
    @DisplayName("전체 사용자를 찾을 수 있다.")
    void findAll() {
        memberDao.save(new MemberEntity("eastsea@eastsea", "eastsea"));
        memberDao.save(new MemberEntity("westsea@westsea", "westsea"));

        List<MemberEntity> members = memberDao.findAll();

        assertAll(
                () -> assertThat(members.get(0).getEmail()).isEqualTo("eastsea@eastsea"),
                () -> assertThat(members.get(1).getEmail()).isEqualTo("westsea@westsea")
        );
    }

    @Test
    @DisplayName("이메일로 사용자를 찾을 수 있다.")
    void findByEmail() {
        MemberEntity member = memberDao.save(new MemberEntity("eastsea@eastsea", "eastsea"));

        MemberEntity findMember = memberDao.findByEmail(new MemberEntity(member.getEmail(), member.getPassword()));

        assertAll(
                () -> assertThat(findMember.getEmail()).isEqualTo("eastsea@eastsea"),
                () -> assertThat(findMember.getPassword()).isEqualTo("eastsea")
        );
    }
}
