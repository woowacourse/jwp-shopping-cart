package cart.cartitems.dao;

import cart.member.dao.MemberDao;
import cart.product.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import static cart.TestUtils.*;
@TestComponent
public class CartItemTestConfig {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    public void setMembersAndProducts() {
        memberDao.save(NO_ID_MEMBER1);
        memberDao.save(NO_ID_MEMBER2);
        productDao.save(NO_ID_PRODUCT1);
        productDao.save(NO_ID_PRODUCT2);
        productDao.save(NO_ID_PRODUCT3);
    }
}
