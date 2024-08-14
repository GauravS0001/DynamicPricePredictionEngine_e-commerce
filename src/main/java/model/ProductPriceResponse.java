package model;

public class ProductPriceResponse {
	private Long productId;
    private Double newPrice;

    public ProductPriceResponse(Long productId, Double newPrice) {
        this.productId = productId;
        this.newPrice = newPrice;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Double getNewPrice() { return newPrice; }
    public void setNewPrice(Double newPrice) { this.newPrice = newPrice; }
}
