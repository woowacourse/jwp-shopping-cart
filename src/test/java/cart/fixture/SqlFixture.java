package cart.fixture;

public class SqlFixture {

    public static final String PRODUCT_INSERT_SQL = "insert into product(id, name, price, image_url) values (?,?,?,?)";
    public static final String PRODUCT_INSERT_SQL_NO_ID = "insert into product(name, price, image_url) values (?,?,?)";

    public static final String MEMBER_INSERT_SQL = "insert into member(id, email, password) values (?, ?,?)";
    public static final String MEMBER_INSERT_SQL_NO_ID = "insert into member(email, password) values (?, ?)";

}
