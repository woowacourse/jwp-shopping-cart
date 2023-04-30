package cart.persistence.dao;

import cart.persistence.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JdbcTest
@Import(MemberDao.class)
class MemberDaoTest {

    private MemberEntity memberEntity;

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberEntity = new MemberEntity("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("사용자 정보를 저장한다.")
    void insert() {
        // when
        final Long savedMemberId = memberDao.insert(memberEntity);

        // then
        final Optional<MemberEntity> member = memberDao.findById(savedMemberId);
        final MemberEntity findMember = member.get();
        assertThat(findMember)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효한 사용자 아이디가 주어지면, 사용자 정보를 조회한다.")
    void findById_success() {
        final Long savedMemberId = memberDao.insert(memberEntity);

        // when
        final Optional<MemberEntity> member = memberDao.findById(savedMemberId);

        // then
        final MemberEntity findMember = member.get();
        assertThat(findMember)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효하지 않은 사용자 아이디가 주어지면, 빈 값을 반환한다.")
    void findById_empty() {
        // when
        final Optional<MemberEntity> member = memberDao.findById(1L);

        // then
        assertThat(member).isEmpty();
    }

    @Test
    @DisplayName("사용자 전체를 조회한다")
    void findAll() {
        // given
        memberDao.insert(memberEntity);
        memberDao.insert(new MemberEntity("koda@gmail.com", "kodaissocute", "코다", "010-4321-8765"));

        // when
        final List<MemberEntity> memberEntities = memberDao.findAll();

        // then
        assertThat(memberEntities).hasSize(2);
        assertThat(memberEntities)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly(tuple("journey@gmail.com", "password", "져니", "010-1234-5678"),
                        tuple("koda@gmail.com", "kodaissocute", "코다", "010-4321-8765"));
    }
    
    @Test
    @DisplayName("유효한 사용자 이메일이 주어지면, 사용자 정보를 반환한다.")
    void findByEmail_success() {
        // given
        memberDao.insert(memberEntity);
        
        // when
        final Optional<MemberEntity> member = memberDao.findByEmail("journey@gmail.com");
        final MemberEntity findMember = member.get();

        // then
        assertThat(findMember)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }
    
    @Test
    @DisplayName("유효하지 않은 사용자 이메일이 주어지면, 빈 값을 반환한다.")
    void findByEmail_fail() {
        // when
        final Optional<MemberEntity> member = memberDao.findByEmail("koda@gmail.com");

        // then
        assertThat(member).isEmpty();
    }
}
