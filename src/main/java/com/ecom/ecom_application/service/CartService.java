package com.ecom.ecom_application.service;

import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.model.Product;
import com.ecom.ecom_application.model.User;
import com.ecom.ecom_application.repository.CartItemRepository;
import com.ecom.ecom_application.repository.ProductRepository;
import com.ecom.ecom_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productsRepository;
    private final UserRepository usersRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt =  productsRepository.findById(request.getProductId());

        if (productOpt.isEmpty()){
            return false;
        }

        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity()){
            return false;
        }

        Optional<User> userOpt = usersRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()){
            return false;
        }
        User user = userOpt.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }
}
