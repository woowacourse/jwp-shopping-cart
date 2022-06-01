package woowacourse;

import woowacourse.shoppingcart.domain.Customer;

public class Fixture {
    public static final String 페퍼_아이디 = "pepper@woowacourse.com";
    public static final String 페퍼_이름 = "pepper";
    public static final String 페퍼_비밀번호 = "Qwe1234!";
    public static final Customer 페퍼 = new Customer(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

    public static final String 바꿀_아이디 = "change@woowacoures.com";
    public static final String 바꿀_이름 = "changeName";
    public static final String 바꿀_비밀번호 = "Fake1234!";
}
