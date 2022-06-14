package woowacourse.shoppingcart.domain;

import java.time.LocalDateTime;

public class Orders {

    private final Long id;
    private final LocalDateTime date;

    public Orders(Long id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
