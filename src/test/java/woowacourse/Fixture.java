package woowacourse;

import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;

public class Fixture {
    public static final String 페퍼_아이디 = "pepper@woowacourse.com";
    public static final String 페퍼_이름 = "pepper";
    public static final String 페퍼_비밀번호 = "Qwe1234!";
    public static final Customer 페퍼 = new Customer(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

    public static final String 다른_아이디 = "change@woowacoures.com";
    public static final String 다른_이름 = "changeName";
    public static final String 다른_비밀번호 = "Fake1234!";

    public static final Product 치킨 = new Product("치킨", 10_000, "http://example.com/chicken.jpg");
    public static final Product 맥주 = new Product("맥주", 20_000, "http://example.com/beer.jpg");
}
