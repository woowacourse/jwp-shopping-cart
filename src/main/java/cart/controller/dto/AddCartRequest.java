package cart.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCartRequest {

    private final Long id;

    @JsonCreator
    public AddCartRequest(@JsonProperty("id") final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
