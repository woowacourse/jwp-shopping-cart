package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.customer.CustomerEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomerServiceTest {


    @Autowired
    private CustomerService customerService;

    @DisplayName("모든 유저를 조회하여 반환한다 ( 어플리케이션 실행시 존재하는 2명의 더미 유저를 조회한다.)")
    @Test
    void findAll() {
        //given
        //when
        final List<CustomerEntity> customers = customerService.findAll();

        //then
        assertAll(
            () -> assertThat(customers).hasSize(2),
            () -> assertThat(customers.get(0).getId()).isEqualTo(1L),
            () -> assertThat(customers.get(0).getEmail()).isEqualTo("split@wooteco.com"),
            () -> assertThat(customers.get(0).getPassword()).isEqualTo("dazzle"),
            () -> assertThat(customers.get(1).getId()).isEqualTo(2L),
            () -> assertThat(customers.get(1).getEmail()).isEqualTo("dazzle@wooteco.com"),
            () -> assertThat(customers.get(1).getPassword()).isEqualTo("split")
        );
    }
}
