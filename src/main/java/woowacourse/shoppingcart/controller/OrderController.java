package woowacourse.shoppingcart.controller;

//@Validated
//@RestController
//@RequestMapping("/api/customers/{customerName}/orders")
//public class OrderController {
//
//    private final OrderService orderService;
//
//    public OrderController(final OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    /*
//    @PostMapping
//    public ResponseEntity<Void> addOrder(@PathVariable final String customerName,
//            @RequestBody @Valid final List<OrderRequest> orderDetails) {
//        final Long orderId = orderService.addOrder(orderDetails, customerName);
//        return ResponseEntity.created(
//                URI.create("/api/" + customerName + "/orders/" + orderId)).build();
//    }
//     */
//
//    @GetMapping("/{orderId}")
//    public ResponseEntity<Orders> findOrder(@PathVariable final String customerName,
//            @PathVariable final Long orderId) {
//        final Orders order = orderService.findOrderById(customerName, orderId);
//        return ResponseEntity.ok(order);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Orders>> findOrders(@PathVariable final String customerName) {
//        final List<Orders> orders = orderService.findOrdersByCustomerName(customerName);
//        return ResponseEntity.ok(orders);
//    }
//}
