package woowacourse.shoppingcart.domain;

import java.sql.Date;

public class Orders {

    private final Long id;
    private final Date date;

    public Orders(Long id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
