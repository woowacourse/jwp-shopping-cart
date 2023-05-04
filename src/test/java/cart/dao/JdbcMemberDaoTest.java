package cart.dao;

import cart.dao.dummyData.MemberInitializer;
import cart.domain.entity.MemberEntity;
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
            final List<MemberEntity> memberEntities = memberDao.selectAll();

            assertThat(memberEntities.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("사용자를 등록하는 insert 메서드 테스트")
    class InsertTest {

        @DisplayName("사용자가 등록이 되는지 확인한다")
        @Test
        void successTest() {
            final MemberEntity memberEntity = MemberEntity.of("hihi@email.com", "password3");

            assertDoesNotThrow(() -> memberDao.insert(memberEntity));
        }
    }

    @Nested
    @DisplayName("사용자를 이메일과 비밀번호로 조회하는 selectByEmailAndPassword 메서드 테스트")
    class SelectByEmailAndPasswordTest {

        @DisplayName("존재하는 사용자가 조회 되는지 확인한다")
        @Test
        void successTest() {
            final MemberEntity memberEntity = MemberEntity.of("irene@email.com", "password1");
            MemberEntity selectedMemberEntity = memberDao.selectByEmailAndPassword(memberEntity);
            assertAll(
                    () -> assertThat(selectedMemberEntity.getEmail()).isEqualTo(memberEntity.getEmail()),
                    () -> assertThat(selectedMemberEntity.getPassword()).isEqualTo(memberEntity.getPassword())
            );
        }

        @DisplayName("존재하지 않는 사용자를 조회할 경우 null을 반환하는지 확인한다")
        @Test
        void returnNullTest() {
            final MemberEntity memberEntity = MemberEntity.of("hihi@email.com", "password3");

            assertThat(memberDao.selectByEmailAndPassword(memberEntity)).isNull();
        }
    }

}
