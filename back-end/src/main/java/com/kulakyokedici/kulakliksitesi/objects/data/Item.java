package com.kulakyokedici.kulakliksitesi.objects.data;

import java.util.List;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import jakarta.annotation.Nullable;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items")
@Indexed
@Getter
@Setter
public class Item implements Comparable<Item>
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;
	
	// Bu değer dışarıdan değiştirilemez (updatable=false), boş olamaz ve benzersizdir.
	@Column(name = "item_uuid", updatable = false, nullable = false, unique = true)
	private String itemUuid = java.util.UUID.randomUUID().toString();
	
	@Column(name = "autoeq_id", nullable = true)
	private String autoeqId;
	
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
	
	// uygulama içi fiyat hesaplama yapılacak.
	private transient Double priceAfterTax;
	
	@Column(name = "is_recommended")
	private boolean isRecommended;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column(name = "stock")
	private Integer stock;
	
	@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;
    
	@ElementCollection
	@CollectionTable(name = "images")
	@Nullable
	private List<Image> images;
	
	@Nullable
	@Column(name = "description")
	@FullTextField
	private String description;
	
	@Override
	public int compareTo(Item other)
	{
		return this.id.compareTo(other.id);
	}
}
