package cart.domain.persistence.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import cart.domain.persistence.entity.MemberEntity;

@JdbcTest
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcMemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new JdbcMemberDao(jdbcTemplate);
    }

    @Test
    void save_메서드로_사용자_정보를_저장한다() {
        final MemberEntity modi = new MemberEntity("a@a.com", "password1");

        assertDoesNotThrow(() -> memberDao.save(modi));
    }

    @Test
    void findByEmail_메서드로_존재하는_사용자_정보를_조회한다() {
        final MemberEntity memberEntity = new MemberEntity("a@a.com", "password1");
        memberDao.save(memberEntity);

        final Optional<MemberEntity> validMemberEntity = memberDao.findByEmail("a@a.com");

        assertThat(validMemberEntity).isPresent();
    }

    @Test
    void findByEmail_메서드로_존재하지_않는_사용자_조회_시_Optional_empty를_반환한다() {
        final Optional<MemberEntity> invalidMemberEntity = memberDao.findByEmail("a@a.com");

        assertThat(invalidMemberEntity).isEmpty();
    }

    @Test
    void findAll_메서드로_저장된_Member의_목록을_불러온다() {
        final MemberEntity modi = new MemberEntity("a@a.com", "password1");
        final MemberEntity jena = new MemberEntity("b@b.com", "password2");
        memberDao.save(modi);
        memberDao.save(jena);

        final List<MemberEntity> memberEntities = memberDao.findAll();

        assertThat(memberEntities.size()).isEqualTo(2);
    }

    @Test
    void 사용자가_없을_때_findAll로_조회하면_빈_리스트를_반환한다() {
        List<MemberEntity> empty = memberDao.findAll();

        assertThat(empty.size()).isZero();
    }
}
