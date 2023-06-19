package com.jetbrains.aliye.pizza.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class) // Enable Mockito support for JUnit 5
@SpringBootTest(classes = {PizzaController.class})
public class PizzaControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PizzaController pizzaController;

	@Test
	public void testRegisterCustomer() throws Exception {
		Customer customer = new Customer(1, "Jane Doe", "jane_doe@gmail.com", "password123");

		//Send a POST request to the '/api/pizzas/register' endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void testLoginCustomer() throws Exception {
		Customer customer = new Customer(1, "Jane Doe", "jane_doe@gmail.com", "password123");
		String username = "Jane Doe";
		String password = "password123";

		//Send a POST request to the '/api/pizzas/login' endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer))
						.param("username", username)
						.param("password", password))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetAllPizzas() throws Exception {
		//Send a GET request to the '/api/pizzas' endpoint
		mockMvc.perform(MockMvcRequestBuilders.get("/api/pizzas"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetPizzasByCategory() throws Exception {
		boolean vegetarian = true;
		Category category = new Category(vegetarian);

		//Send a GET request to the '/api/pizzas' endpoint with 'category' parameter
		mockMvc.perform(MockMvcRequestBuilders.get("/api/pizzas")
						.param("category", category.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testSearchPizzasByKeyword() throws Exception {
		String keyword = "pepperoni";

		//Send a GET request to the '/api/pizzas' endpoint with 'keyword' parameter
		mockMvc.perform(MockMvcRequestBuilders.get("/api/pizzas")
						.param("keyword", keyword))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testAddToCart() throws Exception {
		byte[] jsonData = Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("data.json").toURI()));

		//Send a POST request to the '/api/pizzas/add-to-cart' endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/add-to-cart")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonData))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void testRemoveFromCart() throws Exception {
		Customer customer = new Customer(1, "Jane Doe", "jane_doe@gmail.com", "password123");
		Pizza pizza = new Pizza(1, "Pizza Marinara", 23.95, "A type of Neapolitan Pizza featuring tomatoes, garlic, oregano, and extra virgin olive",
						new Category(true),
						Arrays.asList(
								new Topping("Oregano", "FRESH OREGANO"),
								new Topping("Extra Virgin Olive Oil", "QUALITY MEDITERANIAN OLIVE OIL"),
								new Topping("Roasted Garlic", "PEELED GARLIC, CANOLA OIL"),
								new Topping("Tomato Sauce", "VINE-RIPENED TOMATOES, FRESH BASIL LEAF, SALT, CALCIUM CHLORIDE, NATURALLY DERIVED CITRIC ACID, EXTRA VIRGIN OLIVE OIL, SEA SALT")
						));

		//Send a POST request to the '/api/pizzas/remove-from-cart' endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/remove-from-cart")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer))
						.content(objectMapper.writeValueAsString(pizza)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testUpdateCart() throws Exception {
		Customer customer = new Customer(1, "Jane Doe", "jane_doe@gmail.com", "password123");
		Pizza pizza = new Pizza(1, "Pizza Marinara", 23.95, "A type of Neapolitan Pizza featuring tomatoes, garlic, oregano, and extra virgin olive",
				new Category(true),
				Arrays.asList(
						new Topping("Oregano", "FRESH OREGANO"),
						new Topping("Extra Virgin Olive Oil", "QUALITY MEDITERANIAN OLIVE OIL"),
						new Topping("Roasted Garlic", "PEELED GARLIC, CANOLA OIL"),
						new Topping("Tomato Sauce", "VINE-RIPENED TOMATOES, FRESH BASIL LEAF, SALT, CALCIUM CHLORIDE, NATURALLY DERIVED CITRIC ACID, EXTRA VIRGIN OLIVE OIL, SEA SALT")
				));
		int quantity = 2;

		//Send a POST request to the '/api/pizzas/update-cart' endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/update-cart")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer))
						.content(objectMapper.writeValueAsString(pizza))
						.param("quantity", String.valueOf(quantity)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testLogoutCustomer() throws Exception {
		Customer customer = new Customer(1, "Jane Doe", "jane_doe@gmail.com", "password123");

		//Send a POST request to the '/api/pizzas/logout' endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/logout")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testCheckoutCart() throws Exception {
		Customer customer = new Customer(1, "Jane Doe", "jane_doe@gmail.com", "password123");

		//Send a POST request to the '/api/pizzas/checkout' endpoint
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/checkout")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private List<Pizza> readPizzasFromJsonFile() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream inputStream = getClass().getResourceAsStream("data.json");
		return objectMapper.readValue(inputStream, new TypeReference<>() {
		});
	}

}

