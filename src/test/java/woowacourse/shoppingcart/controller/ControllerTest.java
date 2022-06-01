package woowacourse.shoppingcart.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ControllerTest {

    public final static long CUSTOMER_ID = 1L;
    public final static String TEST_EMAIL = "test@test.com";
    public final static String TEST_PASSWORD = "testtest";
    public final static String TEST_USERNAME = "테스트";

}
