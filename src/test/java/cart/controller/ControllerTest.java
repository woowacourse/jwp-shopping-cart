package cart.controller;

import cart.auth.AuthenticationService;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        AdminController.class,
        HomeController.class
})
public abstract class ControllerTest {

    @MockBean
    protected ProductService productService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected AuthenticationService authenticationService;

    @Autowired
    protected MockMvc mockMvc;
}
