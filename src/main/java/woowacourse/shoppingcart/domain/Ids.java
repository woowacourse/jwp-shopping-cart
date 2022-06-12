package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Ids {

    private final List<Id> ids;

    public Ids(List<Long> ids, String fieldName) {
        Collections.sort(ids);
        this.ids = ids.stream()
                .map(id -> Id.from(id, fieldName))
                .collect(Collectors.toList());
    }

    public boolean isNotContained(List<Long> ids) {
        return this.ids.stream().anyMatch(cartId -> !ids.contains(cartId.getId()));
    }

    public Long getIdAt(int index) {
        return ids.get(index).getId();
    }
    public int getSize() {
        return ids.size();
    }
}
