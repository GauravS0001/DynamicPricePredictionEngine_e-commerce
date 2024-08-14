package repository;

import model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT COUNT(o) FROM Order o WHERE o.product.id = :productId AND o.orderDate >= CURRENT_DATE - 7")
    int findSalesLastWeek(Long productId);

    @Query("SELECT COUNT(pv) FROM ProductView pv WHERE pv.product.id = :productId AND pv.viewDate >= CURRENT_DATE - 7")
    int findProductViewsLastWeek(Long productId);

    @Query("SELECT COUNT(ps) FROM ProductSearch ps WHERE ps.product.id = :productId AND ps.searchDate >= CURRENT_DATE - 7")
    int findSearchFrequencyLastWeek(Long productId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.product.id = :productId AND o.orderDate >= CURRENT_DATE - 180")
    int findTotalPurchasesLastSixMonths(Long productId);

    @Query("SELECT COUNT(DISTINCT o.customer.id) FROM Order o WHERE o.product.id = :productId AND o.orderDate >= CURRENT_DATE - 180")
    int findTotalUniqueCustomersLastSixMonths(Long productId);
}

