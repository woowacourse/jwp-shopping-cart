package cart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class JdbcMemberDaoTest {

    @Autowired
    private JdbcMemberDao jdbcCustomerDao;

    @DisplayName("")
    @Test
    void selectAllUsers() {
        assertDoesNotThrow(() -> jdbcCustomerDao.selectAllMembers());
    }

}