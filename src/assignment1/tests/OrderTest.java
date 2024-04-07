package assignment1.tests;

import assignment1.Order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {
	private Order order;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		order = new Order(0, 0, 0);
	}


	@Test
	void MealTest() {
		order.addToOrder("4", 3);
		assertEquals(order.getBurritos(), 3);
		assertEquals(order.getSodas(), 3);
		assertEquals(order.getFries(), 3);
		System.out.println("Meals adding to Order as Intended");
	}
	
}
