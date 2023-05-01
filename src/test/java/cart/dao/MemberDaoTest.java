package cart.dao;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import cart.entity.MemberEntity;
import org.junit.jupiter.api.Assertions;
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
    private final MemberEntity insertMemberEntity =
            new MemberEntity.Builder()
                    .nickname("SeongHa")
                    .email("seongha111@gmail.com")
                    .password("1234")
                    .build();

    @BeforeEach
    void setUp() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("멤버를 저장한다.")
    void insert() {
        // when
        long insertedId = memberDao.insert(insertMemberEntity);

        // then
        assertThat(insertedId).isNotNull();
    }

    @Test
    @DisplayName("저장된 멤버를 모두 조회한다.")
    void selectAll() {
        // given
        long insertedId = memberDao.insert(insertMemberEntity);

        // when
        List<MemberEntity> members = memberDao.selectAll();
        MemberEntity member1 = members.get(0);

        // then
        assertAll(
                () -> assertThat(members).hasSize(1),
                () -> assertThat(member1.getMemberId()).isEqualTo(insertedId)
        );
    }
}
