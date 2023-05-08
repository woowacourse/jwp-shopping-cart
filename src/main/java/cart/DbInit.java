package cart;

import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.entity.Member;
import cart.entity.Product;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DbInit {

    private final ProductDao productDao;
    private final MemberDao memberDao;

    public DbInit(ProductDao productDao, MemberDao memberDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @PostConstruct
    private void saveDummyData() {
        productDao.save(new Product(
                "피자",
                "https://cdn.dominos.co.kr/admin/upload/goods/20200311_x8StB1t3.jpg",
                13000));
        productDao.save(new Product(
                "샐러드",
                "https://m.subway.co.kr/upload/menu/K-%EB%B0%94%EB%B9%84%ED%81%90-%EC%83%90%EB%9F%AC%EB%93%9C-%EB%8B%A8%ED%92%88_20220413025007802.png",
                20000));
        productDao.save(new Product(
                "치킨",
                "https://cdn.thescoop.co.kr/news/photo/202010/41306_58347_1055.jpg",
                10000));

        memberDao.save(new Member(
                        "boxster@email.com",
                        "boxster"
                )
        );
        memberDao.save(new Member(
                        "memeber@email.com",
                        "member"
                )
        );
    }
}
