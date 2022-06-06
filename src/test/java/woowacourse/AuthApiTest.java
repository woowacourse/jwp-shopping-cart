package woowacourse;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class AuthApiTest extends TestSupport {

    @Test
    void login_customer_test() throws Exception {
        mockMvc.perform(
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(readJson("/json/auth-api/login.json"))
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    requestFields(
                        fieldWithPath("loginId").description("loginId"),
                        fieldWithPath("password").description("password")
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("accessToken"),
                        fieldWithPath("name").description("name")
                    )
                )
            );
    }
}
