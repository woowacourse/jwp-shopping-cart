package cart.controller.product;

import static cart.domain.product.ProductFixture.NUNU_ID_PRODUCT;
import static cart.domain.product.ProductFixture.ODO_ID_PRODUCT;
import static cart.dto.product.ResponseFixture.NUNU_RESPONSE;
import static cart.dto.product.ResponseFixture.ODO_RESPONSE;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.AbstractControllerTest;
import cart.domain.product.Product;
import cart.dto.product.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@SuppressWarnings({"NonAsciiCharacters"})
class ViewControllerTest extends AbstractControllerTest {

    @Test
    void 상품_조회_테스트() throws Exception {
        //given
        final List<Product> products = List.of(NUNU_ID_PRODUCT, ODO_ID_PRODUCT);
        given(productQueryService.findAll()).willReturn(products);
        final List<ProductResponse> expected = List.of(NUNU_RESPONSE, ODO_RESPONSE);

        //when
        mockMvc.perform(get("/"))

                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("products", equalTo(expected)));
    }

    @Test
    void 어드민_페이지_조회_테스트() throws Exception {
        //given
        final List<Product> products = List.of(NUNU_ID_PRODUCT, ODO_ID_PRODUCT);
        given(productQueryService.findAll()).willReturn(products);
        final List<ProductResponse> expected = List.of(NUNU_RESPONSE, ODO_RESPONSE);

        //when
        mockMvc.perform(get("/admin"))

                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("products", equalTo(expected)));
    }
}
