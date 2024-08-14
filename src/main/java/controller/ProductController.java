package controller;

import model.Product;
import model.ProductPriceResponse;
import service.PricingAlgorithmService;
import service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
    private ProductService productService;

    @Autowired
    private PricingAlgorithmService pricingAlgorithmService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductPriceResponse> getProductPrice(@PathVariable Long productId) {
        // Retrieve the product details
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        
        Double newPrice = pricingAlgorithmService.calculateNewPrice(product);

        ProductPriceResponse response = new ProductPriceResponse(productId, newPrice);
        return ResponseEntity.ok(response);
    }
}
