package com.kulakyokedici.kulakliksitesi.service;

import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.objects.data.Cart;
import com.kulakyokedici.kulakliksitesi.objects.data.CartItem;
import com.kulakyokedici.kulakliksitesi.objects.data.Item;
import com.kulakyokedici.kulakliksitesi.objects.exception.EErrorCode;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.CartRepository;
import com.kulakyokedici.kulakliksitesi.repository.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService 
{
	
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;
	
	public CartService(
			CartRepository cartRepository,
			ItemRepository itemRepository)
	{
		this.cartRepository = cartRepository;
		this.itemRepository = itemRepository;
	}
	
	@Transactional
	public void addToCart(Long cartId, Long itemId, int quantity) 
	{
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "cart id", cartId, EErrorCode.CART_NOT_FOUND));
		
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("item", "item id", itemId, EErrorCode.ITEM_NOT_FOUND));
		
		CartItem existingCartItem = cart.getItems().stream()
				.filter(cartItem -> cartItem.getItem().getItemUuid().equals(item.getItemUuid()))
				.findFirst()
				.orElse(null);
		
		if (existingCartItem != null)
			existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
		else 
		{
			CartItem newCartItem = new CartItem();
			newCartItem.setCart(cart);
			newCartItem.setItem(item);
			newCartItem.setQuantity(quantity);
			cart.getItems().add(newCartItem);
		}
		
		item.setStock(item.getStock() - quantity);
	}
	
	@Transactional
	public void setItemQuantity(Long cartId, Long itemId, int quantity) 
	{
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "cart id", cartId, EErrorCode.CART_NOT_FOUND));
		
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("item", "item id", itemId, EErrorCode.ITEM_NOT_FOUND));
		
		CartItem existingCartItem = cart.getItems().stream()
				.filter(cartItem -> cartItem.getItem().getItemUuid().equals(item.getItemUuid()))
				.findFirst()
				.orElse(null);
		
		if (existingCartItem != null)
			existingCartItem.setQuantity(quantity);
		else
		{
			CartItem newCartItem = new CartItem();
			newCartItem.setCart(cart);
			newCartItem.setItem(item);
			newCartItem.setQuantity(quantity);
			cart.getItems().add(newCartItem);
		}
	}
	
	@Transactional
	public void incrementCartItem(Long cartId, Long itemId)
	{
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "cart id", cartId, EErrorCode.CART_NOT_FOUND));
		
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("item", "item id", itemId, EErrorCode.ITEM_NOT_FOUND));
		
		CartItem existingCartItem = cart.getItems().stream()
				.filter(cartItem -> cartItem.getItem().getItemUuid().equals(item.getItemUuid()))
				.findFirst()
				.orElse(null);
		
		if (existingCartItem != null)
		{
			existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
			item.setStock(item.getStock() - 1);
		}
	}
	
	@Transactional
	public void removeFromCart(Long cartId, Long itemId, int quantity)
	{
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "cart id", cartId, EErrorCode.CART_NOT_FOUND));
		
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("item", "item id", itemId, EErrorCode.ITEM_NOT_FOUND));
		
		CartItem existingCartItem = cart.getItems().stream()
				.filter(cartItem -> cartItem.getItem().getItemUuid().equals(item.getItemUuid()))
				.findFirst()
				.orElse(null);
		
		if (existingCartItem != null) {
			int newQuantity = existingCartItem.getQuantity() - quantity;
			
			if (newQuantity > 0)
				existingCartItem.setQuantity(newQuantity);
			else
				cart.getItems().remove(existingCartItem);
			
			item.setStock(item.getStock() + quantity);
		}
	}

}
