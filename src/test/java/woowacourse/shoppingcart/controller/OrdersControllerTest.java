package woowacourse.shoppingcart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static woowacourse.fixture.Fixture.BEARER;
import static woowacourse.fixture.Fixture.PRICE;
import static woowacourse.fixture.Fixture.PRODUCT_NAME;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.THUMBNAIL_URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.OrdersDetailDto;
import woowacourse.shoppingcart.dto.OrdersDetailProductResponseDto;
import woowacourse.shoppingcart.dto.OrdersRequestDto;
import woowacourse.shoppingcart.dto.OrdersResponseDto;
import woowacourse.shoppingcart.service.OrdersService;

class OrdersControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrdersService ordersService;
    @MockBean
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
    }

    @Test
    @DisplayName("사용자 장바구니에서 주문할 목록을 선택해 주문을 요청한다.")
    void ordersRequest() throws Exception {
        when(ordersService.order(any(), any())).thenReturn(1L);

        final OrdersRequestDto ordersRequestDto = new OrdersRequestDto(List.of(1L, 2L));
        final MockHttpServletResponse response = mockMvc.perform(post("/api/customers/1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                        .content(objectMapper.writeValueAsString(ordersRequestDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertAll(
                () -> assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.getHeader("Location")).isEqualTo("/api/customers/1/orders/1")
        );
    }

    @Test
    @DisplayName("사용자의 전체 주문 내역을 조회한다.")
    void getCustomerOrders() throws Exception {
        // given
        final OrdersDetailDto ordersDetailDto1 = new OrdersDetailDto(
                new OrdersDetailProductResponseDto(1L, THUMBNAIL_URL, PRODUCT_NAME, PRICE), 1
        );
        final OrdersDetailDto ordersDetailDto2 = new OrdersDetailDto(
                new OrdersDetailProductResponseDto(2L, "testUrl2", "test2", PRICE), 2
        );
        final List<OrdersResponseDto> orders = List.of(
                new OrdersResponseDto(List.of(ordersDetailDto1, ordersDetailDto2))
        );
        when(ordersService.findCustomerOrders(any())).thenReturn(orders);

        // when
        final MockHttpServletResponse response = mockMvc.perform(get("/api/customers/1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken))
                .andDo(print())
                .andReturn()
                .getResponse();

        // then
        final List<OrdersResponseDto> ordersResponseDtos = Arrays.asList(
                objectMapper.readValue(response.getContentAsString(), OrdersResponseDto[].class));

        final List<OrdersDetailDto> ordersDetails = ordersResponseDtos.get(0).getOrdersDetails();
        final List<Long> productIds = ordersDetails.stream()
                .map(ordersDetail -> ordersDetail.getProduct().getProductId())
                .collect(Collectors.toList());
        assertAll(
                () -> assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(ordersResponseDtos.size()).isEqualTo(1),
                () -> assertThat(ordersDetails)
                        .extracting("count").contains(1, 2),
                () -> assertThat(productIds).contains(1L, 2L)
        );
    }

    @Test
    @DisplayName("사용자의 주문을 단건 조회한다.")
    void getOrders() throws Exception {
        // given
        final OrdersDetailDto ordersDetailDto1 = new OrdersDetailDto(
                new OrdersDetailProductResponseDto(1L, THUMBNAIL_URL, PRODUCT_NAME, PRICE), 1
        );
        final OrdersDetailDto ordersDetailDto2 = new OrdersDetailDto(
                new OrdersDetailProductResponseDto(2L, "testUrl2", "test2", PRICE), 2
        );
        final OrdersResponseDto orders = new OrdersResponseDto(List.of(ordersDetailDto1, ordersDetailDto2));

        when(ordersService.findOrders(1L)).thenReturn(orders);

        // when
        final MockHttpServletResponse response = mockMvc.perform(get("/api/customers/1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken))
                .andDo(print())
                .andReturn()
                .getResponse();

        // then
        final OrdersResponseDto ordersResponseDto
                = objectMapper.readValue(response.getContentAsString(), OrdersResponseDto.class);
        final List<OrdersDetailDto> ordersDetails = ordersResponseDto.getOrdersDetails();
        final List<Long> productIds = ordersDetails.stream()
                .map(ordersDetail -> ordersDetail.getProduct().getProductId())
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(ordersDetails)
                        .extracting("count").contains(1, 2),
                () -> assertThat(productIds).contains(1L, 2L)
        );
    }
}