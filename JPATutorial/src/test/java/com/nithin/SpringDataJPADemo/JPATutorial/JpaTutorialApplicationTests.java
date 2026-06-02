package com.nithin.SpringDataJPADemo.JPATutorial;

import com.nithin.SpringDataJPADemo.JPATutorial.entities.ProductEntity;
import com.nithin.SpringDataJPADemo.JPATutorial.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaTutorialApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	@Test
	void contextLoads() {
	}

	@Test
	void saveProduct(){
		ProductEntity product = ProductEntity.builder()
				.sku("nestle234")
				.title("Gammaggi")
				.price(BigDecimal.valueOf(23.45))
				.build();
		productRepository.save(product);
	}

	@Test
	void generalTests(){
//		List<ProductEntity> productEntities = productRepository.findByTitle("maggi");
//		List<ProductEntity> productEntities =  productRepository.findByCreatedAtAfter(LocalDateTime.of(2024,5,5,0,0,0));
		Optional<ProductEntity> productEntity = productRepository.findByTitleAndPrice("maggi",BigDecimal.valueOf(23.45));
//		List<ProductEntity> productEntities = productRepository.findByQuantityGreaterThanAndPriceLessThan(2,BigDecimal.valueOf(2500));
//		List<ProductEntity> productEntities = productRepository.findByTitleContainingIgnoreCase("gam");
		System.out.println(productEntity);
	}

}
