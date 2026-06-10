package com.kulakyokedici.kulakliksitesi.objects.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shoppers")
@Getter
@Setter
public class Shopper extends User
{
	public Shopper() {}
	
	public Shopper(String username, String password)
	{
		super(username, password);
	}
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
    @Embedded
    @Nullable
    @AttributeOverrides(
    {
    @AttributeOverride(name = "addressLine1",
    column = @Column(name = "BILLING_ADDRESSLINE1")),
    @AttributeOverride(name = "addressLine2",
    column = @Column(name = "BILLING_ADDRESSLINE2")),
    @AttributeOverride(name = "zipCode",
    column = @Column(name = "BILLING_ZIPCODE", length = 5)),
    @AttributeOverride(name = "district",
    column = @Column(name = "BILLING_DISTRICT", length = 5)),
    @AttributeOverride(name = "city",
    column = @Column(name = "BILLING_CITY"))
    })
    private Address billingAddress;
    
    @Embedded
    @Nullable
    @AttributeOverrides(
    {
    @AttributeOverride(name = "addressLine1",
    column = @Column(name = "SHIPPING_ADDRESSLINE1")),
    @AttributeOverride(name = "addressLine2",
    column = @Column(name = "SHIPPING_ADDRESSLINE2")),
    @AttributeOverride(name = "zipCode",
    column = @Column(name = "SHIPPING_ZIPCODE", length = 5)),
    @AttributeOverride(name = "district",
    column = @Column(name = "SHIPPING_DISTRICT", length = 5)),
    @AttributeOverride(name = "city",
    column = @Column(name = "SHIPPING_CITY"))
    })
    private Address shippingAddress;
    
    @OneToOne(mappedBy = "shopper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;
}
