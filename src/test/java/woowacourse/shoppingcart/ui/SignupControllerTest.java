package woowacourse.shoppingcart.ui;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.RequestTokenContext;
import woowacourse.shoppingcart.application.CustomerService;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "13.125.246.196")
@WebMvcTest(SignupController.class)
@Import({RequestTokenContext.class, JwtTokenProvider.class})
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void create() throws Exception {

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/signup")
                        .content("{\n"
                                + "    \"account\": \"leo0842\",\n"
                                + "    \"nickname\": \"eden\",\n"
                                + "    \"password\": \"Password123!\",\n"
                                + "    \"address\": \"garden of eden\",\n"
                                + "    \"phoneNumber\":{\n"
                                + "        \"start\":\"010\",\n"
                                + "        \"middle\":\"1234\",\n"
                                + "        \"last\":\"5678\"\n"
                                + "    }\n"
                                + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("customer-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("account").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.OBJECT).description("휴대폰 번호"),
                                fieldWithPath("phoneNumber.start").type(JsonFieldType.STRING)
                                        .description("휴대폰 번호 첫 자리"),
                                fieldWithPath("phoneNumber.middle").type(JsonFieldType.STRING)
                                        .description("휴대폰 번호 중간 자리"),
                                fieldWithPath("phoneNumber.last").type(JsonFieldType.STRING).description("휴대폰 번호 끝 자리")
                        )
                ));
    }
}
