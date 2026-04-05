package com.kulakyokedici.kulakliksitesi.objects.data;

import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sellers")
public class Seller extends User
{
	public Seller() {}
	
	public Seller(String username, String password)
	{
		super(username, password);
	}

	@Column(name = "company_name")
	private String companyName;
	
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Item> items = new TreeSet<>();
	
    public Set<Item> getItems() {
        return items;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
//    public void fullUpdate(Seller newSeller)
//    {
//    	super.fullUpdate(newSeller);
//    	companyName = newSeller.companyName;
//    }
}