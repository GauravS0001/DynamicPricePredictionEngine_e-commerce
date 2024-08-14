package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private String category;
	    private Double basePrice;
	    private Double currentPrice;
	    private Double customerRating;

	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getCategory() { return category; }
	    public void setCategory(String category) { this.category = category; }

	    public Double getBasePrice() { return basePrice; }
	    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }

	    public Double getCurrentPrice() { return currentPrice; }
	    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }

	    public Double getCustomerRating() { return customerRating; }
	    public void setCustomerRating(Double customerRating) { this.customerRating = customerRating; }
}
