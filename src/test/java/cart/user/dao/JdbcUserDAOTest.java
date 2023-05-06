package cart.user.dao;

import cart.auth.dao.JdbcUserDAO;
import cart.auth.domain.User;
import cart.auth.dto.UserInfo;
import cart.common.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcUserDAO.class)
class JdbcUserDAOTest {
    
    @Autowired
    private JdbcUserDAO jdbcUserDAO;
    
    @Test
    @DisplayName("유저 DB 저장 테스트")
    void insert() {
        //given
        final int initialCount = this.jdbcUserDAO.findAll().size();
        final var user = UserInfo.of("test@test.com", "#test1234");
        
        //when
        this.jdbcUserDAO.create(user);
        
        //then
        final int finalCount = this.jdbcUserDAO.findAll().size();
        Assertions.assertEquals(initialCount + 1, finalCount);
        final String email = user.getEmail().getValue();
        final User resultUser = this.jdbcUserDAO.find(user);
        Assertions.assertEquals(email, resultUser.getEmail().getValue());
    }
    
    @Test
    @DisplayName("유저 DB 저장 실패 테스트 - 중복 이메일")
    void insertFail() {
        //given
        final UserInfo user = UserInfo.of("test@test.com", "#test1234");
        this.jdbcUserDAO.create(user);
        
        //when
        final UserInfo duplicatedUser = UserInfo.of("test@test.com", "#test1234");
        final boolean exist = this.jdbcUserDAO.isExist(duplicatedUser);
        
        //then
        Assertions.assertTrue(exist);
    }
    
    @Test
    @DisplayName("유저 Password 테스트")
    void findByEmail() {
        //given
        final UserInfo user = UserInfo.of("echo@test.com", "#test1234");
        
        //when
        final UserInfo userInfo = UserInfo.of("echo@test.com", "#test1235");
        
        //then
        Assertions.assertThrows(NotFoundException.class, () -> this.jdbcUserDAO.find(userInfo));
    }
}