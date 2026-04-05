package com.kulakyokedici.kulakliksitesi.objects.data;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public class Address 
{
	public Address() {}
	
	@NotNull
	@Size(
			min = 1,
			max = 50,
			message = "address line too short or long")
	private String addressLine1;
	
	@Size(
			max = 50,
			message = "address line too long")
	private String addressLine2;
	
	@NotBlank(message = "district cannot be blank")
	private String district;
	
	@NotBlank(message = "city cannot be blank")
	private String city;
	
	@NotBlank
	private String zipCode;
	
	public String getAddressLine1()
	{
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2()
	{
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2)
	{
		this.addressLine2 = addressLine2;
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}
}
