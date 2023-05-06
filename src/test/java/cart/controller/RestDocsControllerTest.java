package cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.common.property.RestDocsProperties;
import cart.controller.helper.ControllerTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RestDocsController.class)
class RestDocsControllerTest extends ControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestDocsProperties restDocsProperties;

    @DisplayName("REST-API Docs 페이지를 조회한다.")
    @Test
    void apiDocs() throws Exception {
        // given
        when(restDocsProperties.getFilePath()).thenReturn("docs/index.html");

        // when, then
        mockMvc.perform(get("/api-docs")
                .contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }
}
