package com.kulakyokedici.kulakliksitesi.mapper;

import org.springframework.stereotype.Component;

import com.kulakyokedici.kulakliksitesi.config.s3.StorageProperties;
import com.kulakyokedici.kulakliksitesi.objects.data.Image;
import com.kulakyokedici.kulakliksitesi.objects.data.Item;
import com.kulakyokedici.kulakliksitesi.objects.data.Seller;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;

@Component
public class ItemMapper
{
	private final StorageProperties storageProperties;

    // properties'i inject ediyoruz
    public ItemMapper(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }
	
	public Item toEntity(ItemCreateRequest req, Seller seller)
	{
		Item item = new Item();
		
		item.setName(req.name());
		item.setDescription(req.description());
		item.setTitle(req.title());
		item.setBrand(req.brand());
		item.setSeller(seller);
		
		return item;
	}
	
	public ItemResponse toResponse(Item item)
	{
		return new ItemResponse(
				item.getId(),
				item.getTitle(),
				item.getPrice(),
				item.getDescription(),
				new SellerResponse(
						item.getSeller().getId(),
						item.getSeller().getUsername(),
						item.getSeller().getEmail(),
						item.getSeller().getCompanyName()),
				item.getImages(),
				item.getAutoeqId()
				);
			
	}
	
	public ItemSummaryResponse toSummaryResponse(Item item)
	{
		String thumbnailKey;
		if(!item.getImages().isEmpty())
			thumbnailKey = storageProperties.getEndpoint()
					+ "/"
					+ storageProperties.getAllBuckets().get("product-images")
					+ "/"
					+ item.getImages().stream()
					.filter(image -> image.isThumbnail())
					.findAny()
					.map(image -> image.getThumbnailKey())
					.orElse("");
		else
			thumbnailKey = "-";
		
		return new ItemSummaryResponse(
				item.getId(),
				item.getTitle(),
				item.getPrice(),
				item.isRecommended(),
				thumbnailKey
				);
	}
	
	public void updateEntity(Item item, ItemUpdateRequest req)
	{
		item.setBrand(req.brand());
		item.setName(req.name());
		item.setDescription(req.description());
		item.setTitle(req.title());
	}
}
