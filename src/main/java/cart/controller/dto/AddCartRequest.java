package cart.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddCartRequest {

    @NotNull
    @Positive
    private final Long id;

    @JsonCreator
    public AddCartRequest(@JsonProperty("id") final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
