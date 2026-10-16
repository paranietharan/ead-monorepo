package com.teamz.product.cart;

import com.teamz.product.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public void addItemToCart(Long productId, int quantity, Long userId) {
        // Get cart from user
        Cart cart = cartRepository.findByUserId(userId);

        // If cart is not found, create a new cart for the user
        // cart not be there if user is new
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartRepository.save(cart);
        }

        // Check if the product is already in the cart
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        // If the product is already in the cart, update the quantity
        if (cartItem != null) {
            // If the quantity is negative or zero, remove the product from the cart
            if (quantity == 0) {
                cartItemRepository.delete(cartItem);
                return;
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // If the product is not in the cart, add the product to the cart
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(
                            productRepository.findById(productId)
                                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId))
                    )
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(cartItem);
        }

    }


    public List<CartResponse> getCartItems(Long userId) {
        // Get cart from user
        Cart cart = cartRepository.findByUserId(userId);

        // If cart is not found, return an empty list
        if (cart == null) {
            return List.of();
        }

        // Get cart items from the cart
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        // Convert cart items to cart response
        return cartItems.stream()
                .map(cartItem -> new CartResponse(
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getDescription(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getProductImg()
                ))
                .toList();
    }

    public void deleteItemFromCart(Long productId, Long userId) {
        // Get cart from user
        Cart cart = cartRepository.findByUserId(userId);

        // If cart is not found, return
        if (cart == null) {
            return;
        }

        // Get cart item from the cart
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        // If cart item is not found, return
        if (cartItem == null) {
            return;
        }

        // Delete the cart item
        cartItemRepository.delete(cartItem);
    }

    public void updateQuantity(Long productId, int quantity, Long userId) {
        // Get cart for the user
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user ID: " + userId);
        }

        // Check if the product exists in the cart
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (cartItem != null) {
            if (quantity > 0) {
                // Update the quantity
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);
            } else {
                // If quantity is 0, remove the item from the cart
                cartItemRepository.delete(cartItem);
            }
        } else {
            throw new IllegalArgumentException("Product not found in cart for product ID: " + productId);
        }
    }

}
