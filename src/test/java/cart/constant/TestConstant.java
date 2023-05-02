package cart.constant;

public class TestConstant {
    public static final String MEMBER_ID_INIT_SQL = "ALTER TABLE MEMBER ALTER COLUMN id RESTART WITH 1";
    public static final String PRODUCT_ID_INIT_SQL = "ALTER TABLE PRODUCT ALTER COLUMN id RESTART WITH 1";
    public static final String CART_ID_INIT_SQL = "ALTER TABLE CART ALTER COLUMN id RESTART WITH 1";
}
