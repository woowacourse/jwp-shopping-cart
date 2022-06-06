package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(final TokenRequest tokenRequest) {
        customerDao.findByEmailAndPassword(
                        tokenRequest.toEmail(),
                        tokenRequest.toPassword())
                .orElseThrow(() -> new InvalidCustomerException("아이디나 비밀번호를 잘못 입력했습니다."));

        return jwtTokenProvider.createToken(tokenRequest.getEmail());
    }

    public String getNickname(final TokenRequest tokenRequest) {
        final CustomerEntity customerEntity = customerDao.findByEmailAndPassword(
                        tokenRequest.toEmail(),
                        tokenRequest.toPassword())
                .orElseThrow(() -> new InvalidCustomerException("아이디나 비밀번호를 잘못 입력했습니다."));

        return customerEntity.getCustomer()
                .getNickname();
    }
}
