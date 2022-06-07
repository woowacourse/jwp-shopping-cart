package woowacourse.shoppingcart.dao;

/*
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final ProductDao productDao;
    private final OrdersDetailDao ordersDetailDao;
    private long ordersId;
    private long productId;
    private long customerId;

    public OrdersDetailDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.productDao = new ProductDao(jdbcTemplate);
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate, productDao);
    }

    @BeforeEach
    void setUp() {
        customerId = 1L;
        jdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (?)", customerId);
        ordersId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);

        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)"
                , "name", 1000, "imageUrl");
        productId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        int quantity = 5;

        //when
        Long orderDetailId = ordersDetailDao
                .addOrdersDetail(ordersId, productId, quantity);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            jdbcTemplate
                    .update("INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)",
                            ordersId, productId, 3);
        }

        //when
        final List<OrderDetail> ordersDetailsByOrderId = ordersDetailDao
                .findOrdersDetailsByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }
}
 */
