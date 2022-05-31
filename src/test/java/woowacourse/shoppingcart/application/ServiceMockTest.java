package woowacourse.shoppingcart.application;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceMockTest {

    protected static MockedStatic<BCrypt> bcrypt;

    @Mock
    protected CustomerDao customerDao;

    @Mock
    protected CustomerService customerService;

    @Mock
    protected JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        bcrypt = mockStatic(BCrypt.class);
    }

    @AfterEach
    void close() {
        bcrypt.close();
    }
}
