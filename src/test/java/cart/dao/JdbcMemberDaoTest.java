package cart.dao;

import cart.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class JdbcMemberDaoTest {

    @Autowired
    private JdbcMemberDao jdbcCustomerDao;

    @DisplayName("selectAllUsers 성공 테스트")
    @Test
    void selectAllUsers() {
        List<MemberEntity> memberEntities = jdbcCustomerDao.selectAllMembers();

        assertAll(
                () -> assertThat(memberEntities).hasSize(2),
                () -> assertThat(memberEntities).extracting("email", "password")
                        .contains(
                                tuple("dino96@naver.com", "jjongwa96"),
                                tuple("jeomxon@gmail.com", "jeomxon00")
                        )
        );
    }

}
