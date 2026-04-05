package com.kulakyokedici.kulakliksitesi.objects.data;

import jakarta.persistence.Embeddable;

@Embeddable
public class Image
{
	private String url;
	
	public Image() {}
	
	public Image(String url)
	{
		this.url = url;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
}
