package assignment1.tests;

import assignment1.Order;

import static org.junit.Assert.*;
import org.junit.*;

public class OrderTest {
	private Order order;

	@Before
	public void setUpBeforeClass() throws Exception {
		order = new Order(0, 0, 0);
	}


	@Test
	public void MealTest() {
		order.addToOrder("4", 3);
		assertEquals(order.getBurritos(), 3);
		assertEquals(order.getSodas(), 3);
		assertEquals(order.getFries(), 3);
		System.out.println("Meals adding to Order as Intended");
	}
	
}
