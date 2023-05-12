package cart.dao;

import cart.dummydata.MemberInitializer;
import cart.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Import({JdbcMemberDao.class, MemberInitializer.class})
@JdbcTest
class JdbcMemberDaoTest {

    @Autowired
    JdbcMemberDao memberDao;

    @Nested
    @DisplayName("사용자 목록을 조회하는 selectAll 메서드 테스트")
    class SelectAllTest {

        @DisplayName("모든 사용자를 반환하는지 확인한다")
        @Test
        void returnAllProductsTest() {
            final List<Member> memberEntities = memberDao.selectAll();

            assertThat(memberEntities.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("사용자를 등록하는 insert 메서드 테스트")
    class InsertTest {

        @DisplayName("사용자가 등록이 되는지 확인한다")
        @Test
        void successTest() {
            final Member member = Member.of("hihi@email.com", "password3");

            assertDoesNotThrow(() -> memberDao.insert(member));
        }
    }

    @Nested
    @DisplayName("사용자를 이메일로 조회하는 selectByEmail 메서드 테스트")
    class SelectByEmailTest {

        @DisplayName("존재하는 사용자가 조회 되는지 확인한다")
        @Test
        void successTest() {
            final Member member = Member.of("irene@email.com", "password1");
            Member selectedMember = memberDao.selectByEmail(member.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));;
            assertAll(
                    () -> assertThat(selectedMember.getEmail()).isEqualTo(member.getEmail()),
                    () -> assertThat(selectedMember.getPassword()).isEqualTo(member.getPassword())
            );
        }

        @DisplayName("존재하지 않는 사용자를 조회할 경우 빈값을 반환하는지 확인한다")
        @Test
        void returnEmptyest() {
            final Member member = Member.of("hihi@email.com", "password3");

            assertThat(memberDao.selectByEmail(member.getEmail())).isEmpty();
        }
    }

}
