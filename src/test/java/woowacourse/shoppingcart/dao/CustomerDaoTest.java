package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import woowacourse.shoppingcart.domain.Member;

@JdbcTest
class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    @DisplayName("이메일, 암호화 된 패스워드, 닉네임을 받아서 Member 테이블에 저장한다.")
    void save() {
        final Member testMember = Member.createWithoutId("test@test.com", "testtest", "테스트");
        final Long createdMemberId = customerDao.save(testMember);

        final Member findMember = customerDao.findById(createdMemberId).get();

        assertThat(findMember.getId()).isEqualTo(createdMemberId);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByNickName(userName);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String userName = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByNickName(userName);

        // then
        assertThat(customerId).isEqualTo(16L);
    }
}