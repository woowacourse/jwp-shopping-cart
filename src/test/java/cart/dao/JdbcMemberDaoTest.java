package cart.dao;

import cart.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}
