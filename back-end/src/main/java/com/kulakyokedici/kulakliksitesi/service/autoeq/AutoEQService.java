package com.kulakyokedici.kulakliksitesi.service.autoeq;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.objects.data.Item;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.autoeq.AutoEQSearchResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.EqualizeRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.EqualizeResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.BaseException;
import com.kulakyokedici.kulakliksitesi.objects.exception.EErrorCode;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.ItemRepository;

@Service
public class AutoEQService {
	
	private final AutoEQClientService autoEQClientService;
	private final ItemRepository itemRepository;
	
	public AutoEQService(AutoEQClientService autoeqClientService, ItemRepository itemRepository)
	{
		this.autoEQClientService = autoeqClientService;
		this.itemRepository = itemRepository;
	}
	
	/**
	 * Kullanıcının kulaklığını ürüne benzetecek EQ profilini hesaplar.
	 */
	public EqualizeResponse equalize(EqualizeRequest request)
	{
		Item item = this.itemRepository
				.findById(request.productId())
				.orElseThrow(() -> new ResourceNotFoundException("item", "id", request.productId(), EErrorCode.ITEM_NOT_FOUND));
		
		String targetAutoeqId = item.getAutoeqId();
		
		if (targetAutoeqId == null || targetAutoeqId.isBlank())
		{
			throw new BaseException(
					"Bu ürün için AutoEQ profili tanımlanmamış.",
					HttpStatus.BAD_REQUEST,
					EErrorCode.AUTOEQ_NOT_SUPPORTED_FOR_PRODUCT
			);
		}
		
		return this.autoEQClientService.equalize(request.userHeadphoneId(), targetAutoeqId);
	}
	
	/**
	 * Kulaklık arama — AutoEQ servisinden geleni doğrudan döndürür.
	 */
	public AutoEQSearchResponse searchHeadphones(String query, int limit)
	{
		return this.autoEQClientService.searchHeadphones(query, limit);
	}

}