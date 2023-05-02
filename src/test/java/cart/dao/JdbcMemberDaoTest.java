package cart.dao;

import cart.dao.dummyData.MemberInitializer;
import cart.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

}
