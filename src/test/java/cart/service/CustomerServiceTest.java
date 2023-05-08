package cart.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.dao.CustomerDao;
import cart.entity.customer.CustomerEntity;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    @InjectMocks
    private CustomerService customerService;

    @DisplayName("모든 유저를 조회하여 반환한다.")
    @Test
    void findAll() {
        //given
        when(customerDao.findAll())
            .thenReturn(List.of(new CustomerEntity(1L, "email@email.com", "password")));

        //when
        final List<CustomerEntity> savedCustomer = customerService.findAll();

        //then
        verify(customerDao).findAll();
        assertAll(
            () -> Assertions.assertThat(savedCustomer).hasSize(1),
            () -> Assertions.assertThat(savedCustomer.get(0).getId()).isEqualTo(1L),
            () -> Assertions.assertThat(savedCustomer.get(0).getEmail()).isEqualTo("email@email.com"),
            () -> Assertions.assertThat(savedCustomer.get(0).getPassword()).isEqualTo("password")
        );
    }

    @DisplayName("이메일과 비밀번호로 고객의 아이디를 조회한다.")
    @Test
    void findByEmailAndPassword() {
        //given
        when(customerDao.findIdByEmailAndPassword(any(), any()))
            .thenReturn(Optional.of(1L));

        //when
        final Long customerId = customerService.findCertifiedMemberIdByEmailAndPassword(
            "email@email.com",
            "password"
        );

        //then
        verify(customerDao).findIdByEmailAndPassword(any(), any());
        Assertions.assertThat(customerId).isEqualTo(1L);
    }
}
