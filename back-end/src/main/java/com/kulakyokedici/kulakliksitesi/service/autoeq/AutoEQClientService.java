package com.kulakyokedici.kulakliksitesi.service.autoeq;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.BiquadFilter;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.autoeq.AutoEQEqualizeRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.autoeq.AutoEQEqualizeResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.autoeq.AutoEQSearchResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.EqualizeResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.BaseException;
import com.kulakyokedici.kulakliksitesi.objects.exception.EErrorCode;

@Service
public class AutoEQClientService
{
	private final RestClient restClient;
	
	public AutoEQClientService(RestClient restClient)
	{
		this.restClient = restClient;
	}
	
	public EqualizeResponse equalize(String sourceId, String targetId)
	{
		AutoEQEqualizeRequest request = new AutoEQEqualizeRequest(sourceId, targetId);
		
		try
		{
			AutoEQEqualizeResponse response = this.restClient
					.post()
					.uri("/equalize")
					.body(request)
					.retrieve()
					.body(AutoEQEqualizeResponse.class);
			
			if (response == null)
			{
				throw new BaseException(
						"AutoEQ servisinden boş yanıt alındı.",
						HttpStatus.NOT_FOUND,
						EErrorCode.AUTOEQ_SERVICE_UNAVAILABLE
				);
			}
			
			return this.toPublicResponse(response);
		}
		catch (HttpClientErrorException.NotFound ex)
		{
			throw new BaseException(
					"Kulaklık ölçümü bulunamadı: " + sourceId + " veya " + targetId,
					HttpStatus.NOT_FOUND,
					EErrorCode.AUTOEQ_HEADPHONE_NOT_FOUND
			);
		}
		catch (HttpClientErrorException.BadRequest ex)
		{
			throw new BaseException(
					"Geçersiz kulaklık ID formatı.",
					HttpStatus.BAD_REQUEST,
					EErrorCode.AUTOEQ_INVALID_ID
			);
		}
		catch (HttpServerErrorException | ResourceAccessException ex)
		{
			throw new BaseException(
					"AutoEQ servisi şu anda kullanılamıyor.",
					HttpStatus.BAD_GATEWAY,
					EErrorCode.AUTOEQ_SERVICE_UNAVAILABLE
			);
		}
	}
	
	/**
	 * Kulaklık arama. Frontend autocomplete'i bunu çağırır.
	 * AutoEQ'nin response'unu doğrudan döndürüyoruz (adapter yok),
	 * çünkü zaten temiz/snake_case değil.
	 * 
	 * @param query arama sorgusu (null veya boşsa ilk N alfabetik)
	 * @param limit maks sonuç sayısı (1-50)
	 */
	public AutoEQSearchResponse searchHeadphones(String query, int limit)
	{
		try
		{
			AutoEQSearchResponse response = this.restClient
					.get()
					.uri(uriBuilder -> uriBuilder
							.path("/headphones")
							.queryParamIfPresent("q", java.util.Optional.ofNullable(query))
							.queryParam("limit", limit)
							.build())
					.retrieve()
					.body(AutoEQSearchResponse.class);
			
			if (response == null)
			{
				return new AutoEQSearchResponse(List.of(), 0);
			}
			
			return response;
		}
		catch (HttpServerErrorException | ResourceAccessException ex)
		{
			throw new BaseException(
					"AutoEQ servisi şu anda kullanılamıyor.",
					HttpStatus.BAD_GATEWAY,
					EErrorCode.AUTOEQ_SERVICE_UNAVAILABLE
			);
		}
	}
	
	// ---------------------------------------------------------------------
	// Mapping
	// ---------------------------------------------------------------------
	
	private EqualizeResponse toPublicResponse(AutoEQEqualizeResponse internal)
	{
		List<BiquadFilter> filters = internal
				.filters()
				.stream()
				.map(f -> new BiquadFilter(f.type(), f.fc(), f.q(), f.gain()))
				.toList();
		
		return new EqualizeResponse(internal.fs(), internal.preampDb(), filters);
	}
}
