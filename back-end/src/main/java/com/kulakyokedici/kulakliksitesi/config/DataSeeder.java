package com.kulakyokedici.kulakliksitesi.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kulakyokedici.kulakliksitesi.objects.data.Admin;
import com.kulakyokedici.kulakliksitesi.objects.data.EUserType;
import com.kulakyokedici.kulakliksitesi.objects.data.Image;
import com.kulakyokedici.kulakliksitesi.objects.data.Item;
import com.kulakyokedici.kulakliksitesi.objects.data.Seller;
import com.kulakyokedici.kulakliksitesi.objects.data.Shopper;
import com.kulakyokedici.kulakliksitesi.objects.data.UserType;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.ItemRepository;
import com.kulakyokedici.kulakliksitesi.repository.UserRepository;
import com.kulakyokedici.kulakliksitesi.repository.UserTypeRepository;

import jakarta.transaction.Transactional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserTypeRepository userTypeRepository, 
                      UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      ItemRepository itemRepository) {
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
    }
    
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        
        createRoleIfNotFound(EUserType.ADMIN);
        createRoleIfNotFound(EUserType.SELLER);
        createRoleIfNotFound(EUserType.SHOPPER);

        if (!userRepository.existsByUsername("admin")) 
        {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setEmail("admin@admin.com");

            UserType adminRole = userTypeRepository.findByName(EUserType.ADMIN)
            		.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.ADMIN.name()));
            
            admin.setUserTypes(new HashSet<>(Set.of(adminRole))); 

            userRepository.save(admin);
        }
        
        if (!userRepository.existsByUsername("shopper"))
        {
        	Shopper shopper = new Shopper();
        	shopper.setUsername("shopper");
        	shopper.setPassword(passwordEncoder.encode("shopperpass"));
        	shopper.setEmail("shopper@shopper.com");
        	shopper.setFirstName("ali");
        	shopper.setLastName("fazaoglu");
        	
        	UserType shopperRole = userTypeRepository.findByName(EUserType.SHOPPER)
        			.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.SHOPPER.name()));
        	
        	shopper.setUserTypes(new HashSet<>(Set.of(shopperRole)));
        	
        	userRepository.save(shopper);
        }
        
        if (!userRepository.existsByUsername("seller"))
        {
        	Seller seller = new Seller();
        	seller.setUsername("seller");
        	seller.setPassword(passwordEncoder.encode("sellerpass"));
        	seller.setEmail("seller@seller.com");
        	seller.setCompanyName("ödemiş ltd şti");
        	
        	UserType sellerRole = userTypeRepository.findByName(EUserType.SELLER)
        			.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.SELLER.name()));
        	
        	seller.setUserTypes(new HashSet<>(Set.of(sellerRole)));
        	
        	userRepository.save(seller);
        	
        	Image image = new Image();
        	image.setUrl("https://cdn.jsdelivr.net/gh/huseyun/contentdeliverygeneral@0.1.0/kulaklik-satis-api/product_detail_x2_desktop_HD_600_Sennheiser_01.webp");
        	
        	Image smallImage = new Image();
        	smallImage.setUrl("https://cdn.jsdelivr.net/gh/huseyun/contentdeliverygeneral@master/kulaklik-satis-api/item_images_small/product_detail_x2_desktop_HD_600_Sennheiser_01%20(1).webp");
        	
        	Item item = new Item();
        	item.setTitle("logitek g502");
        	item.setPrice(550.0);
        	item.getImages().add(image);
        	
        	item.getSmallImages().add(smallImage);
        	
        	item.setSeller(seller);
        	
        	item.setDescription("herhangi bir eşya");
        	
        	itemRepository.save(item);
        	
        	seller.getItems().add(item);
        }
        
    }

    private void createRoleIfNotFound(EUserType roleName) {
        if (userTypeRepository.findByName(roleName).isEmpty()) {
            UserType role = new UserType();
            role.setName(roleName);
            userTypeRepository.save(role);
        }
    }
}