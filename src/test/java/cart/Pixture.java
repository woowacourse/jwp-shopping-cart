package cart;

import cart.entity.AuthMember;
import cart.entity.CreateItem;
import cart.entity.Item;
import cart.entity.Member;

public class Pixture {
    public static final CreateItem CREATE_ITEM1 = new CreateItem("치킨", "a", 10000);
    public static final CreateItem CREATE_ITEM2 = new CreateItem("피자", "b", 20000);
    public static final CreateItem CREATE_ITEM3 = new CreateItem("햄버거", "c", 2000);

    public static final AuthMember AUTH_MEMBER1 = new AuthMember("gksqlsl11@khu.ac.kr", "qlalfqjsgh");
    public static final AuthMember AUTH_MEMBER2 = new AuthMember("kong@google.com", "pw");
    public static final AuthMember AUTH_MEMBER3 = new AuthMember("power@google.com", "power");

    /*
        CREATE_ITEM을 순서대로 DB에 저장했을 때 각각 이에 대응되는 ITEM
     */

    public static final Item ITEM1 = new Item(1L, "치킨", "a", 10000);
    public static final Item ITEM2 = new Item(2L, "피자", "b", 20000);
    public static final Item ITEM3 = new Item(3L, "햄버거", "c", 2000);


    /*
        AUTH_MEMBER를 순서대로 DB에 저장했을 때 각각 이에 대응되는 MEMBER
     */

    public static final Member MEMBER1 = new Member(1L, "gksqlsl11@khu.ac.kr", "qlalfqjsgh");
    public static final Member MEMBER2 = new Member(2L, "kong@google.com", "pw");
    public static final Member MEMBER3 = new Member(3L, "power@google.com", "power");
}
