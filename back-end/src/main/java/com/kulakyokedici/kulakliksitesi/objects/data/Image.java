package com.kulakyokedici.kulakliksitesi.objects.data;

import jakarta.persistence.Embeddable;

@Embeddable
public class Image
{
	private String originalKey;
	
	private String thumbnailKey;
	
	private String standardKey;
	
	private boolean isThumbnail;

	private int displayOrder;
	
	public Image() {}
	
	public String getOriginalKey()
	{
		return originalKey;
	}

	public void setOriginalKey(String originalKey)
	{
		this.originalKey = originalKey;
	}

	public String getThumbnailKey()
	{
		return thumbnailKey;
	}

	public void setThumbnailKey(String thumbnailKey)
	{
		this.thumbnailKey = thumbnailKey;
	}

	public String getStandartKey()
	{
		return standardKey;
	}

	public void setStandartKey(String standartKey)
	{
		this.standardKey = standartKey;
	}

	public boolean isThumbnail()
	{
		return isThumbnail;
	}

	public void setThumbnail(boolean isThumbnail)
	{
		this.isThumbnail = isThumbnail;
	}

	public int getDisplayOrder()
	{
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder)
	{
		this.displayOrder = displayOrder;
	}
	
}
