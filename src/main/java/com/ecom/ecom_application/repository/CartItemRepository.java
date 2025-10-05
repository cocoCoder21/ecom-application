package com.ecom.ecom_application.repository;

import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.model.Product;
import com.ecom.ecom_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    @Query("SELECT ci FROM cart_items ci WHERE ci.user = ?1 AND ci.product = ?2")
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);
}
