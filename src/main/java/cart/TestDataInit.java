package cart;

import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductName;
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
        MemberEntity member1 = memberDao.save(new Member(new Email("glenfiddich@naver.com"), new Password("123456")));
        MemberEntity member2 = memberDao.save(new Member(new Email("glenlivet@naver.com"), new Password("123456")));
        ProductEntity product1 = productDao.save(new Product(new ProductName("글렌피딕 12년"), new Price(10_000),
                ImageUrl.from(
                        "https://static.thcdn.com/images/large/original//productimg/1600/1600/12957094-4064940467862805.jpg")));
        ProductEntity product2 = productDao.save(new Product(new ProductName("글렌리벳 12년"), new Price(20_000),
                ImageUrl.from(
                        "https://www.willowpark.net/cdn/shop/products/21097-GlenLivet12YearOldSingleMaltScotchWhisky.png")));
        ProductEntity product3 = productDao.save(new Product(new ProductName("달모어 62년"), new Price(9_999_999),
                ImageUrl.from(
                        "https://www.thewhiskyexchange.com/media/rtwe/uploads/product/large/5480.jpg")));
        cartDao.save(member1.getId(), product1.getId());
        cartDao.save(member1.getId(), product2.getId());
        cartDao.save(member2.getId(), product1.getId());
        cartDao.save(member2.getId(), product2.getId());
        cartDao.save(member2.getId(), product3.getId());
    }
}
