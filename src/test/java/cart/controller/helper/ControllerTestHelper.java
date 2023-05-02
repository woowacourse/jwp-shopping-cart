package cart.controller.helper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.common.auth.AdminAuthInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

public class ControllerTestHelper {

    @MockBean
    protected AdminAuthInterceptor adminAuthInterceptor;

    @BeforeEach
    void setUp() {
        when(adminAuthInterceptor.preHandle(any(), any(), any()))
            .thenReturn(true);
    }
}
