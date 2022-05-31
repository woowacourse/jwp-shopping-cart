package woowacourse.shoppingcart.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.auth.dto.SignInDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;
import woowacourse.shoppingcart.exception.AuthorizationFailException;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long signUp(final SignUpDto signUpDto){
        final Customer newCustomer = Customer.createWithoutId(
                signUpDto.getEmail(),
                signUpDto.getPassword(),
                signUpDto.getUsername()
        );

        return customerDao.save(newCustomer);
    }

    public CustomerDto findCustomerById(final Long id) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
        return new CustomerDto(customer.getId(), customer.getEmail(), customer.getUsername());
    }

    public CustomerDto updateCustomer(final Long id, final UpdateCustomerDto updateCustomerDto){
        final Customer updateCustomer = Customer.createWithoutEmailAndPassword(
                id,
                updateCustomerDto.getUsername());
        try{
            customerDao.update(updateCustomer);
            return findCustomerById(updateCustomer.getId());
        }catch (DataIntegrityViolationException e){
            throw new DuplicateNameException("수정하려는 이름이 이미 존재합니다.");
        }
    }

    public void deleteCustomer(final Long id) {
        customerDao.deleteById(id);
    }
}
