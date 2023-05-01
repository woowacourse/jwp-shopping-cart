package cart.repository;

import cart.authentication.repository.JdbcMemberRepository;
import cart.authentication.entity.Member;
import cart.authentication.repository.MemberRepository;
import cart.authentication.exception.MemberPersistenceFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(JdbcMemberRepository.class)
class JdbcMemberRepositoryTest {
    @Autowired
    MemberRepository repository;

    @Test
    @DisplayName("회원을 저장할 수 있다.")
    void saveUser() throws MemberPersistenceFailedException {
        Member member = new Member("email@Gmail.com", "password1234");
        Member savedMember = repository.save(member);
        assertThat(savedMember).isEqualTo(member);
    }

    @Test
    @DisplayName("중복된 email로 회원을 저장할 수 없다.")
    void duplicateEmail() throws MemberPersistenceFailedException {
        Member member = new Member("email@Gmail.com", "password1234");
        repository.save(member);
        assertThatThrownBy(() -> repository.save(member))
                .isInstanceOf(MemberPersistenceFailedException.class)
                .hasMessage("이미 등록된 email입니다.");
    }

    @Test
    @DisplayName("주어진 Id로 회원을 조회할 수 있다.")
    void findById() throws MemberPersistenceFailedException {

        // given : 회원을 저장한다.
        String email = "email@Gmail.com";
        Member member = new Member(email, "password1234");
        repository.save(member);

        // when
        Member foundMember = repository.findByEmail(email);

        // then
        assertThat(foundMember).isEqualTo(member);
    }

    @Test
    @DisplayName("주어진 회원으로 회원을 찾을 수 없을 경우 예외가 발생한다.")
    void findByInvalidId() {
        // when
        assertThatThrownBy(() -> repository.findByEmail("notExist@gmail.com"))
                .isInstanceOf(MemberPersistenceFailedException.class)
                .hasMessage("주어진 ID로 Member를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("저장된 Member를 전부 조회할 수 있다.")
    void findAll() throws MemberPersistenceFailedException {
        // when
        List<Member> foundMembers = repository.findAll();

        // then
        assertThat(foundMembers).hasSize(2);
    }
}