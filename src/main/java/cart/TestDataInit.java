package cart;

import cart.domain.Member;
import cart.domain.Product;
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

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        memberDao.save(new Member("glenfiddich@naver.com", "123456"));
        memberDao.save(new Member("glenlivet@naver.com", "123456"));
        productDao.save(new Product("글렌피딕 12년", 10_000, "https://static.thcdn.com/images/large/original//productimg/1600/1600/12957094-4064940467862805.jpg"));
        productDao.save(new Product("글렌리벳 12년", 20_000, "https://www.willowpark.net/cdn/shop/products/21097-GlenLivet12YearOldSingleMaltScotchWhisky.png?v=1619049207"));
        productDao.save(new Product("달모어 62년", 9_999_999, "https://www.thewhiskyexchange.com/media/rtwe/uploads/product/large/5480.jpg"));
    }
}
