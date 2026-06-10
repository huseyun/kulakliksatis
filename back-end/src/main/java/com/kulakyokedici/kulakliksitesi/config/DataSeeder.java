package com.kulakyokedici.kulakliksitesi.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kulakyokedici.kulakliksitesi.objects.data.Admin;
import com.kulakyokedici.kulakliksitesi.objects.data.Category;
import com.kulakyokedici.kulakliksitesi.objects.data.ECategory;
import com.kulakyokedici.kulakliksitesi.objects.data.EUserType;
import com.kulakyokedici.kulakliksitesi.objects.data.Item;
import com.kulakyokedici.kulakliksitesi.objects.data.Seller;
import com.kulakyokedici.kulakliksitesi.objects.data.Shopper;
import com.kulakyokedici.kulakliksitesi.objects.data.UserType;
import com.kulakyokedici.kulakliksitesi.objects.exception.EErrorCode;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public DataSeeder(UserTypeRepository userTypeRepository, 
                      UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      ItemRepository itemRepository,
                      CategoryRepository categoryRepository) {
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }
    
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        
        createRoleIfNotFound(EUserType.ADMIN);
        createRoleIfNotFound(EUserType.SELLER);
        createRoleIfNotFound(EUserType.SHOPPER);
        
        createCategoryIfNotFound(ECategory.IN_EAR);
        createCategoryIfNotFound(ECategory.ON_EAR);
        createCategoryIfNotFound(ECategory.OVER_EAR);

        if (!userRepository.existsByUsername("admin")) 
        {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setEmail("admin@admin.com");

            UserType adminRole = userTypeRepository.findByName(EUserType.ADMIN)
            		.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.ADMIN.name(), EErrorCode.USERTYPE_NOT_FOUND));
            
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
        			.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.SHOPPER.name(), EErrorCode.USERTYPE_NOT_FOUND));
        	
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
        			.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.SELLER.name(), EErrorCode.USERTYPE_NOT_FOUND));
        	
        	seller.setUserTypes(new HashSet<>(Set.of(sellerRole)));
        	
        	userRepository.save(seller);
        	
        	
        	Item item = new Item();
        	item.setTitle("hd 800");
        	item.setPrice(550.0);
        	item.setSeller(seller);
        	item.setDescription("herhangi bir eşya");
        	item.setRecommended(true);
        	
        	Category overEarCategory = categoryRepository.findByCategory(ECategory.OVER_EAR)
        			.orElseThrow(() -> new ResourceNotFoundException("category", "category name", ECategory.OVER_EAR.name(), EErrorCode.CATEGORY_NOT_FOUND));
        	
        	item.setCategory(overEarCategory);
        	
        	item.setAutoeqId("oratory1990/over-ear/Sennheiser HD 800");
        	
        	itemRepository.save(item);
        	seller.getItems().add(item);
        	
        	Item item2 = new Item();
        	item2.setTitle("jbl tune 520bt");
        	item2.setPrice(150.0);
        	item2.setSeller(seller);
        	item2.setDescription("rastgele bir eşya");
        	item2.setRecommended(true);
        	
        	Category onEarCategory = categoryRepository.findByCategory(ECategory.ON_EAR)
        			.orElseThrow(() -> new ResourceNotFoundException("category", "category name", ECategory.ON_EAR.name(), EErrorCode.CATEGORY_NOT_FOUND));
        	
        	item2.setCategory(onEarCategory);
        	item2.setAutoeqId("rtings/over-ear/Bruel & Kjaer 5128/JBL Tune 520BT");
        	
        	itemRepository.save(item2);
        	seller.getItems().add(item2);
        	
        	Item item3 = new Item();
        	item3.setTitle("akg k371");
        	item3.setPrice(450.0);
        	item3.setSeller(seller);
        	item3.setDescription("zasdh bir eşya");
        	item3.setRecommended(true);
        	
        	item3.setCategory(overEarCategory);
        	
        	item3.setAutoeqId("oratory1990/over-ear/AKG K371");
        	
        	itemRepository.save(item3);
        	seller.getItems().add(item3);
        	
        	Item item4 = new Item();
        	item4.setTitle("mdr 7506");
        	item4.setPrice(136.79);
        	item4.setSeller(seller);
        	item4.setDescription("kulaklık bir eşya");
        	item4.setRecommended(true);
        	
        	item4.setCategory(overEarCategory);
        	
        	item4.setAutoeqId("oratory1990/over-ear/Sony MDR-7506");
        	
        	itemRepository.save(item4);
        	seller.getItems().add(item4);
        	
        	Item item5 = new Item();
        	item5.setTitle("audeze lcd-2 classic");
        	item5.setPrice(478.21);
        	item5.setSeller(seller);
        	item5.setDescription("dfg fefg zzdsdfd ve famanas");
        	item5.setRecommended(true);
        	
        	item5.setCategory(overEarCategory);
        	
        	item5.setAutoeqId("oratory1990/over-ear/Audeze LCD-2 Classic");
        	
        	itemRepository.save(item5);
        	seller.getItems().add(item5);
        }
        
    }

    private void createRoleIfNotFound(EUserType roleName) {
        if (userTypeRepository.findByName(roleName).isEmpty()) {
            UserType role = new UserType();
            role.setName(roleName);
            userTypeRepository.save(role);
        }
    }
    
    private void createCategoryIfNotFound(
    		ECategory category) {
        if (categoryRepository.findByCategory(category).isEmpty()) {
            Category newCategory = new Category();
            newCategory.setCategory(category);
            categoryRepository.save(newCategory);
        }
    }
}