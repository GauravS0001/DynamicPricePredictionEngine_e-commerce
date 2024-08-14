package service;

import model.Product;
import repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	  @Autowired
	    private ProductRepository productRepository;

	    public Product getProductById(Long productId) {
	        return productRepository.findById(productId).orElse(null);
	    }
}
