package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.argumentresolver.basicauthorization.BasicAuthInfo;
import cart.entity.customer.CustomerEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
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

    @DisplayName("이메일과 비밀번호로 고객의 아이디를 조회한다.")
    @Test
    void findByEmailAndPassword() {
        //given
        //when
        final Long firstCustomerId = customerService.findCustomerIdByBasicAuthInfo(
            new BasicAuthInfo("split@wooteco.com", "dazzle")
        );
        final Long secondCustomerId = customerService.findCustomerIdByBasicAuthInfo(
            new BasicAuthInfo("dazzle@wooteco.com", "split")
        );

        //then
        final List<CustomerEntity> customerEntities = customerService.findAll();
        final List<Long> customerIds = customerEntities.stream()
            .map(CustomerEntity::getId)
            .collect(Collectors.toList());
        Assertions.assertThat(customerIds).containsExactly(firstCustomerId, secondCustomerId);
    }
}
