package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(final CustomerDto newCustomer) {
        final Customer customer = CustomerDto.toCustomer(newCustomer);
        return customerDao.createCustomer(customer);
    }

    public TokenResponse signIn(final TokenRequest tokenRequest) {
        // 이메일, 비밀번호를 도메인에서 검증한다.
        // 이메일을 통해 디비에서 사용자 정보를 가져온다,
        // 패스워드, 약관 동의를 제외한 나머지 정보를 JSON -> String -> 토큰으로 생성.
        // 생성한 토큰을 반환한다.
        return null;
    }
}
