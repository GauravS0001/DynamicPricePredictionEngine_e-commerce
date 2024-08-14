package service;

import repository.ProductRepository;
import utils.MachineLearningModelLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import model.Product;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

@Service
public class PricingAlgorithmService {

	@Autowired
    private ProductRepository productRepository;

    private SavedModelBundle modelBundle;
    private Session session;

    public PricingAlgorithmService() {
        MachineLearningModelLoader loader = new MachineLearningModelLoader();
        this.modelBundle = loader.getModel();
        this.session = modelBundle.session();
    }

    public Double calculateNewPrice(Product product) {
        // Extract features
        Double basePrice = product.getBasePrice();
        Double demandScore = getDemandScore(product);
        Double competitionPrice = getCompetitionPrice(product);
        Double customerRating = product.getCustomerRating();
        Double customerPurchaseFrequency = getCustomerPurchaseFrequency(product);

        // Create feature vector
        float[] features = {basePrice.floatValue(), demandScore.floatValue(), competitionPrice.floatValue(), customerRating.floatValue(), customerPurchaseFrequency.floatValue()};

        // Predict price using the ML model
        double predictedPrice = predictPrice(features);
        return predictedPrice;
    }

    private Double getDemandScore(Product product) {
        int salesLastWeek = getSalesLastWeek(product.getId());
        int viewsLastWeek = getProductViewsLastWeek(product.getId());
        int searchesLastWeek = getSearchFrequencyLastWeek(product.getId());

        double salesWeight = 0.5;
        double viewsWeight = 0.3;
        double searchesWeight = 0.2;

        return (salesWeight * salesLastWeek) + (viewsWeight * viewsLastWeek) + (searchesWeight * searchesLastWeek);
    }

    private int getSalesLastWeek(Long productId) {
        // Query the database for sales data in the last week
        return productRepository.findSalesLastWeek(productId);
    }

    private int getProductViewsLastWeek(Long productId) {
        // Query the database for the number of views in the last week
        return productRepository.findProductViewsLastWeek(productId);
    }

    private int getSearchFrequencyLastWeek(Long productId) {
        // Query the database for the search frequency in the last week
        return productRepository.findSearchFrequencyLastWeek(productId);
    }

    private Double getCompetitionPrice(Product product) {
        List<Double> competitorPrices = getCompetitorPrices(product.getCategory(), product.getName());
        return competitorPrices.stream().mapToDouble(Double::doubleValue).average().orElse(product.getBasePrice());
    }

    private List<Double> getCompetitorPrices(String category, String productName) {
        // Query an external API or a database for competitor prices
        return Arrays.asList(85.0, 92.0, 88.0); // Placeholder values; implement actual API calls here
    }

    private Double getCustomerPurchaseFrequency(Product product) {
        int totalPurchases = getTotalPurchasesLastSixMonths(product.getId());
        int totalCustomers = getTotalUniqueCustomersLastSixMonths(product.getId());

        return totalCustomers > 0 ? (double) totalPurchases / totalCustomers : 0.0;
    }

    private int getTotalPurchasesLastSixMonths(Long productId) {
        // Query the database for the total number of purchases in the last six months
        return productRepository.findTotalPurchasesLastSixMonths(productId);
    }

    private int getTotalUniqueCustomersLastSixMonths(Long productId) {
        // Query the database for the total number of unique customers in the last six months
        return productRepository.findTotalUniqueCustomersLastSixMonths(productId);
    }

    private double predictPrice(float[] features) {
        // Create a Tensor for the input features
        Tensor<Float> inputTensor = Tensor.create(new long[]{1, features.length}, FloatBuffer.wrap(features));

        // Run the TensorFlow model to get the prediction
        Tensor<?> outputTensor = session.runner()
                .feed("input_tensor_name", inputTensor)
                .fetch("output_tensor_name")
                .run()
                .get(0);

        // Extract the predicted price from the output Tensor
        float[] predictedPriceArray = new float[1];
        outputTensor.copyTo(predictedPriceArray);

        // Cleanup the Tensor objects
        inputTensor.close();
        outputTensor.close();

        return predictedPriceArray[0];
    }
}
