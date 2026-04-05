package com.kulakyokedici.kulakliksitesi.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.mapper.ItemMapper;
import com.kulakyokedici.kulakliksitesi.mapper.SellerMapper;
import com.kulakyokedici.kulakliksitesi.objects.data.Image;
import com.kulakyokedici.kulakliksitesi.objects.data.Item;
import com.kulakyokedici.kulakliksitesi.objects.data.Seller;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemImageCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.ItemRepository;
import com.kulakyokedici.kulakliksitesi.repository.SellerRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class ItemService
{
	private final ItemRepository itemRepository;
	private final SellerRepository sellerRepository;
	private final ItemMapper itemMapper;
	private final SellerMapper sellerMapper;
	private final EntityManager entityManager;
	
	public ItemService(
			ItemRepository itemRepository,
			SellerRepository sellerRepository,
			SellerMapper sellerMapper,
			ItemMapper itemMapper,
			EntityManager entityManager)
	{
		this.itemRepository = itemRepository;
		this.itemMapper = itemMapper;
		this.sellerMapper = sellerMapper;
		this.entityManager = entityManager;
		this.sellerRepository = sellerRepository;
	}
	
	public ItemResponse getById(Long id)
	{
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("item", "id", id));
		
		return itemMapper.toResponse(item);
	}
	
	public SellerResponse getSellerById(Long id)
	{
	    Item item = itemRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("item", "id", id));
		
		return sellerMapper.toResponse(item.getSeller());
	}
	
	@Transactional
	public void update(
			Long id,
			ItemUpdateRequest req)
	{
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("item", "id", id));
		
		itemMapper.updateEntity(item, req);
	}
	
	public List<ItemSummaryResponse> getSummaryAll()
	{
		List<Item> items = itemRepository.findAll();
		
		List<ItemSummaryResponse> responseItems = items.stream()
				.map(i -> itemMapper.toSummaryResponse(i))
				.collect(Collectors.toList());
		
		return responseItems;
	}
	
	public Set<ItemSummaryResponse> getSummaryAllBySellerId(Long sellerId)
	{
		Set<Item> items = itemRepository.findBySellerId(sellerId);
		
		Set<ItemSummaryResponse> responseItems = items.stream()
				.map(i -> itemMapper.toSummaryResponse(i))
				.collect(Collectors.toSet());
		return responseItems;
	}
	
	@Transactional
    public List<ItemSummaryResponse> search(String keyword) {
        // hibernate search oturumunu başlat 
        SearchSession searchSession = Search.session(entityManager);

        List<Item> hits = searchSession.search(Item.class)
            .where(f -> f.match()
                .fields("title", "description", "brand")
                .matching(keyword)
                .fuzzy(2)) // 2 harfe kadar yazım yanlışlarını tolere et
            .fetchHits(20); // En alakalı ilk 20 sonucu getir
        
        List<ItemSummaryResponse> response = hits.stream()
        		.map(i -> itemMapper.toSummaryResponse(i))
        		.collect(Collectors.toList());
        
        return response;
	}
	
	@Transactional
	public ItemResponse add(ItemCreateRequest req, String username)
	{
		Seller seller = sellerRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "username", username));
		
		Item item = itemMapper.toEntity(req, seller);
		
		seller.getItems().add(item);
		
		return itemMapper.toResponse(item);
	}
	
	@Transactional
	public void addImage(ItemImageCreateRequest req, Long id)
	{
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("item", "id", id));
		
		Image smallImage = new Image(req.smallImageUrl());
		Image image = new Image(req.imageUrl());
		item.getImages().add(image);
		item.getSmallImages().add(smallImage);
	}
	
	public void delete(Long id)
	{
		itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "id", id));
		
		itemRepository.deleteById(id);
		
	}
}
