package woowacourse.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerNameResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class CustomerDocumentTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CustomerService customerService;

    @Test
    void save() throws Exception {
        CustomerResponse customerResponse = new CustomerResponse(
                1L, "email@email.com", "name", "010-1234-5678", "address");
        when(customerService.save(any(CustomerRequest.class))).thenReturn(customerResponse);

        CustomerRequest customerRequest = new CustomerRequest(
                "email@email.com", "Pw123456!", "name", "010-1234-5678", "address");

        this.mockMvc.perform(post("/customers")
                        .content(objectMapper.writeValueAsString(customerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("customer/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void findByName() throws Exception {
        CustomerNameResponse customerNameResponse = new CustomerNameResponse("name");
        when(customerService.findCustomerName(anyLong())).thenReturn(customerNameResponse);

        String token = jwtTokenProvider.createToken(String.valueOf(anyLong()));
        this.mockMvc.perform(get("/customers/name")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer/name",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void validateEmail() throws Exception {
        doNothing().when(customerService).validateEmailDuplication(anyString());

        this.mockMvc.perform(post("/customers/email/validate")
                        .content(objectMapper.writeValueAsString(new EmailRequest("pobi@woowahan.com")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer/email",
                        preprocessRequest(prettyPrint())
                ));
    }

    @Test
    void update() throws Exception {
        doNothing().when(customerService).update(anyLong(), any(CustomerRequest.class));

        String token = jwtTokenProvider.createToken(String.valueOf(1L));
        CustomerRequest customerRequest = new CustomerRequest(
                "email@email.com", "Pw123456!", "name", "010-1234-5678", "address");

        this.mockMvc.perform(put("/customers")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(customerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(customerService).delete(anyLong());

        String token = jwtTokenProvider.createToken(String.valueOf(anyLong()));
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/customers")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("customer/delete"));
    }
}
