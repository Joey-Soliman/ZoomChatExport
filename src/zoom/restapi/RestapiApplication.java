package com.example.restapi.restapi;

//import com.example.restapi.restapi.chat.Authorization;
import com.example.restapi.restapi.model.Product;
import com.example.restapi.restapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestapiApplication implements CommandLineRunner {

	private ProductRepository productRepository;

	@Autowired
	public void productRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

	public void run(String... strings) throws Exception {

		Product testProduct = new Product();
		testProduct.setName("Simple Product");
		testProduct.setDescription("This is a tester product");
		testProduct.setType("Custom");
		testProduct.setCategory("SPECIAL");

		productRepository.save(testProduct);

	}
}
