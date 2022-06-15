package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import woowacourse.shoppingcart.DocumentTest;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PhoneNumberFormat;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest extends DocumentTest {

    @MockBean
    private CustomerService customerService;

    @Test
    void get() throws Exception {

        final PhoneNumberFormat phoneNumber = new PhoneNumberFormat("010", "1234", "5678");
        final CustomerResponse customerResponse = new CustomerResponse(1L, "leo0842", "eden", "garden of eden",
                phoneNumber);

        given(customerService.getById(any(Long.class))).willReturn(customerResponse);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/customers")
                        .header("Authorization", "bearer access-token"))
                .andExpect(status().isOk())
                .andDo(document("customer-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(headerWithName("Authorization").description("access token")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("일련번호"),
                                fieldWithPath("account").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
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

    @Test
    void update() throws Exception {

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/customers")
                        .header("Authorization", "bearer access-token")
                        .content("{\n"
                                + "    \"nickname\": \"eden\",\n"
                                + "    \"address\": \"palace of eden\",\n"
                                + "    \"phoneNumber\":{\n"
                                + "        \"start\":\"010\",\n"
                                + "        \"middle\":\"1234\",\n"
                                + "        \"last\":\"5678\"\n"
                                + "    }\n"
                                + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(headerWithName("Authorization").description("access token")),
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
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

    @Test
    void delete() throws Exception {

        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/customers")
                        .header("Authorization", "bearer access-token")
                        .content("{\n"
                                + "    \"password\": \"Password123!\"\n"
                                + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("customer-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(headerWithName("Authorization").description("access token")),
                        requestFields(
                                fieldWithPath("password").type(JsonFieldType.STRING).description("Password123!")
                        )
                ));
    }
}
