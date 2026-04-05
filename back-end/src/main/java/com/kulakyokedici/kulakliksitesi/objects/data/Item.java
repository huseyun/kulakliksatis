package com.kulakyokedici.kulakliksitesi.objects.data;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "items")
@Indexed
public class Item implements Comparable<Item>
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	@Nullable
	private String name;
	
	@Column(name = "title")
	@FullTextField
	@Nullable
	private String title;
	
	@Column(name = "brand")
	@KeywordField
	private String brand;
	
	@Nullable
	@Column(name = "item_price")
	private Double price;
	
	private transient Double priceAfterTax;
	
	@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;
    
	@ElementCollection
	@CollectionTable(name = "images")
	@Nullable
	@AttributeOverride(
			name = "url",
			column = @Column(name = "image_url"))
	private Set<Image> images = new HashSet<>();
	
	@ElementCollection
	@CollectionTable(name = "small_images")
	@Nullable
	@AttributeOverride(
			name = "url",
			column = @Column(name = "small_image_url"))
	private Set<Image> smallImages = new HashSet<>();
	
	@Nullable
	@Column(name = "description")
	@FullTextField
	private String description;
	
	@Override
	public int compareTo(Item other)
	{
		return this.id.compareTo(other.id);
	}
	
	public Long getId()
	{
		return id;
	}
	
	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public Set<Image> getImages()
	{
		return images;
	}

	public void setImages(Set<Image> images)
	{
		this.images = images;
	}

	public void setPrice(Double itemPrice)
	{
		this.price = itemPrice;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String itemName)
	{
		this.name = itemName;
	}
    
    public Double getPrice()
    {
    	return price;
    }
	
    public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public Set<Image> getSmallImages()
	{
		return smallImages;
	}

	public void setSmallImages(Set<Image> smallImages)
	{
		this.smallImages = smallImages;
	}
	
	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}
}
