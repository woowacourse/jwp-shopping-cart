package cart;

import cart.domain.Member;
import cart.domain.Product;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.repository.CartDao;
import cart.repository.MemberDao;
import cart.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("local")
public class TestDataInit {
    @Autowired
    MemberDao memberDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    CartDao cartDao;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        MemberEntity member1 = memberDao.save(new Member("glenfiddich@naver.com", "123456"));
        MemberEntity member2 = memberDao.save(new Member("glenlivet@naver.com", "123456"));
        ProductEntity product1 = productDao.save(new Product("글렌피딕 12년", 10_000,
                "https://static.thcdn.com/images/large/original//productimg/1600/1600/12957094-4064940467862805.jpg"));
        ProductEntity product2 = productDao.save(new Product("글렌리벳 12년", 20_000,
                "https://www.willowpark.net/cdn/shop/products/21097-GlenLivet12YearOldSingleMaltScotchWhisky.png?v=1619049207"));
        ProductEntity product3 = productDao.save(new Product("달모어 62년", 9_999_999,
                "https://www.thewhiskyexchange.com/media/rtwe/uploads/product/large/5480.jpg"));
        cartDao.save(member1.getId(), product1.getId());
        cartDao.save(member1.getId(), product2.getId());
        cartDao.save(member2.getId(), product1.getId());
        cartDao.save(member2.getId(), product2.getId());
        cartDao.save(member2.getId(), product3.getId());
    }
}
