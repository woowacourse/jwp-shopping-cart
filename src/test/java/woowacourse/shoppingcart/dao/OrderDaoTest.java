package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.dao.MemberDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:schema.sql")
class OrderDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private OrderDao orderDao;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        orderDao = new OrderDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));

        final Long orderId = orderDao.addOrders(id);

        assertThat(orderId).isNotNull();
    }

    @DisplayName("MemberId 집합을 이용하여 OrderId 집합을 얻는 기능")
    @Test
    void findOrderIdsByMemberId() {
        Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        orderDao.addOrders(id);

        final List<Long> orderIdsByMemberId = orderDao.findOrderIdsByMemberId(id);

        assertThat(orderIdsByMemberId).hasSize(1);
    }

    @DisplayName("존재하는 OrderId 인지 체크한다.")
    @Test
    void isValidOrderId() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long orderId = orderDao.addOrders(memberId);

        assertThat(orderDao.isValidOrderId(memberId, orderId)).isTrue();
    }
}
