package woowacourse.shoppingcart.controller;

/*
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    private final Customer customer = new Customer(Email.of("test@gmail.com"), Password.ofWithEncryption("password1!"), Username.of("aki"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        final Long cartId = 1L;
        final int quantity = 5;
        final Long cartId2 = 1L;
        final int quantity2 = 5;
        final String customerName = "pobi";
        final List<OrderRequest> requestDtos =
                Arrays.asList(new OrderRequest(cartId, quantity), new OrderRequest(cartId2, quantity2));

        final Long expectedOrderId = 1L;
        when(orderService.addOrder(any(), eq(customerName)))
                .thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(post("/api/customers/" + customerName + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(requestDtos))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location",
                        "/api/" + customerName + "/orders/" + expectedOrderId));
    }

    @DisplayName("사용자 이름과 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
    @Test
    void findOrder() throws Exception {

        // given
        final String customerName = "pobi";
        final Long orderId = 1L;
        final Orders expected = new Orders(orderId,
                customer,
                Collections.singletonList(new OrderDetail(new Product(2L, "banana", 1_000, 20, new Image("imageUrl", "imageAlt")), 2)));

        when(orderService.findOrderById(customerName, orderId))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/" + customerName + "/orders/" + orderId)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderId))
                .andExpect(jsonPath("orderDetails[0]..product[0].id").value(2L))
                .andExpect(jsonPath("orderDetails[0]..product[0].price").value(1_000))
                .andExpect(jsonPath("orderDetails[0]..product[0].name").value("banana"))
                .andExpect(jsonPath("orderDetails[0]..product[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("orderDetails[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final String customerName = "pobi";
        final List<Orders> expected = Arrays.asList(
                new Orders(1L, customer, Collections.singletonList(
                        new OrderDetail(new Product(1L, "banana", 1_000, 20, new Image("imageUrl", "imageAlt")), 2))),
                new Orders(2L, customer, Collections.singletonList(
                        new OrderDetail(new Product(2L, "apple", 2_000, 20, new Image("imageUrl2", "imageAlt")), 4)))
        );

        when(orderService.findOrdersByCustomerName(customerName))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/" + customerName + "/orders/")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].product[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].product[0].price").value(1_000))
                .andExpect(jsonPath("$[0].orderDetails[0].product[0].name").value("banana"))
                .andExpect(jsonPath("$[0].orderDetails[0].product[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$[0].orderDetails[0].quantity").value(2))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].product[0].id").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].product[0].price").value(2_000))
                .andExpect(jsonPath("$[1].orderDetails[0].product[0].name").value("apple"))
                .andExpect(jsonPath("$[1].orderDetails[0].product[0].imageUrl").value("imageUrl2"))
                .andExpect(jsonPath("$[1].orderDetails[0].quantity").value(4));
    }
}
*/
