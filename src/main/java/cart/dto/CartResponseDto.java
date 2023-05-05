package cart.dto;

public class CartResponseDto {

    private Long id;
    private String email;
    private ProductResponseDto productResponseDto;

    public CartResponseDto(){
    }

    public CartResponseDto(Long id, String email, ProductResponseDto productResponseDto) {
        this.id = id;
        this.email = email;
        this.productResponseDto = productResponseDto;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ProductResponseDto getProductResponseDto() {
        return productResponseDto;
    }

}
