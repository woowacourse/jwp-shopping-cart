package cart.repository;

import cart.entity.MemberEntity;
import cart.exception.DuplicateEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Sql({"/testSchema.sql"})
class DBMemberRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private MemberRepository memberRepository;
    private MemberEntity entity1;
    private MemberEntity entity2;

    @BeforeEach
    void setUp() {
        memberRepository = new DBMemberRepository(jdbcTemplate);
        MemberEntity memberEntity1 = new MemberEntity(null, "a@a.com", "password1");
        MemberEntity memberEntity2 = new MemberEntity(null, "b@b.com", "password2");

        this.entity1 = memberRepository.save(memberEntity1);
        this.entity2 = memberRepository.save(memberEntity2);
    }

    @Test
    @DisplayName("회원 정보를 DB에 저장한다.")
    void saveTest() {
        MemberEntity memberEntity3 = new MemberEntity(null, "c@c.com", "password3");

        memberRepository.save(memberEntity3);
        List<MemberEntity> memberEntities = memberRepository.findAll();
        assertThat(memberEntities).hasSize(3);
    }

    @Test
    @DisplayName("ID로 회원 정보를 조회한다.")
    void findByIdTest() {
        Optional<MemberEntity> nullableEntity = memberRepository.findById(entity1.getId());
        if (nullableEntity.isEmpty()) {
            throw new RuntimeException();
        }
        MemberEntity response = nullableEntity.get();

        assertAll(
                () -> assertThat(response.getEmail()).isEqualTo(entity1.getEmail()),
                () -> assertThat(response.getPassword()).isEqualTo(entity1.getPassword())
        );
    }

    @Test
    @DisplayName("email로 회원 정보를 조회한다.")
    void findByEmailTest_success() {
        Optional<MemberEntity> nullableEntity = memberRepository.findByEmail(entity1.getEmail());
        if (nullableEntity.isEmpty()) {
            throw new RuntimeException();
        }
        MemberEntity response = nullableEntity.get();

        assertAll(
                () -> assertThat(response.getEmail()).isEqualTo(entity1.getEmail()),
                () -> assertThat(response.getPassword()).isEqualTo(entity1.getPassword())
        );
    }

    @Test
    @DisplayName("email로 회원 정보를 조회할 때, 조회된 회원이 2명 이상이면 예외를 발생시킨다.")
    void findByEmailTest_fail() {
        String duplicatedEmail = entity1.getEmail();
        MemberEntity emailDuplicatedEntity = new MemberEntity(null, duplicatedEmail, "password3");
        memberRepository.save(emailDuplicatedEntity);
        assertThatThrownBy(() -> memberRepository.findByEmail(duplicatedEmail))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("동일한 이메일이 2개 이상 존재합니다.");
    }

    @Test
    @DisplayName("모든 회원 정보를 조회한다.")
    void findAllTest() {
        List<MemberEntity> entities = memberRepository.findAll();
        assertThat(entities).hasSize(2);
    }

    @Test
    @DisplayName("ID에 해당하는 회원 정보를 수정한다.")
    void updateByIdTest() {
        MemberEntity modifiedEntity = new MemberEntity(entity1.getId(), entity1.getEmail(), entity1.getPassword());
        memberRepository.update(modifiedEntity);

        Optional<MemberEntity> nullableEntity = memberRepository.findById(entity1.getId());
        if (nullableEntity.isEmpty()) {
            throw new RuntimeException();
        }
        MemberEntity entityAfterUpdate = nullableEntity.get();

        assertAll(
                () -> assertThat(entityAfterUpdate.getEmail()).isEqualTo(modifiedEntity.getEmail()),
                () -> assertThat(entityAfterUpdate.getPassword()).isEqualTo(modifiedEntity.getPassword())
        );
    }

    @Test
    @DisplayName("ID에 해당하는 회원 정보를 삭제한다.")
    void deleteByIdTest() {
        memberRepository.deleteById(entity2.getId());

        List<MemberEntity> entities = memberRepository.findAll();
        assertThat(entities).hasSize(1);
    }
}