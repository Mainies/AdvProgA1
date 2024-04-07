package assignment1.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import assignment1.*;
import assignment1.Order;
import assignment1.exceptions.*;


class PointOfServiceTest {
	private PointOfService pos = new PointOfService();
	
	@BeforeEach
	void setUp() {
		// reset everything, prices and food items
		pos = new PointOfService();
	}

	//update sale
	@Test
	void updateSalesTestmeal() {
		//check update sales with a meal
		Order order = new Order(1, 1, 1, 1);
		pos.updatePOS(order);
		assertEquals(pos.soldItems.get("Fries"), 1);
		assertEquals(pos.totalSales, 12.0);
	}
	
	@Test
	void updateSalesTestnoMeal(){
	//check update sales without a meal
	Order order = new Order(1, 1, 1);
	pos.updatePOS(order);
	assertEquals(pos.soldItems.get("Fries"), 1);
	assertEquals(pos.totalSales, 15.0);
	}
	
	@Test
	void updatePriceCheck() {
		pos.getBurrito().setPrice(5);
		Order order = new Order(1, 0, 0);
		pos.updatePOS(order);
		assertEquals(pos.totalSales, 5.0);
	}
	
	@Test
	void updatePriceSalesMemory() {
		//check update sales without a meal
		Order order = new Order(1, 1, 1);
		pos.updatePOS(order);
		pos.getBurrito().setPrice(5);
		order = new Order(1, 0, 0);
		pos.updatePOS(order);
		assertEquals(pos.totalSales, 20.0);
		assertEquals(pos.soldItems.get("Burrito"), 2);
	}
	
	@Test
    public void testValidateNumberThrowsException() throws Exception{
        assertThrows(NotANumberException.class, () -> {
            PointOfService.validateNumber("not a number");
        });
    }
	
	
	@Test
    public void testValidateMenuInput() throws Exception{
        assertThrows(MenuSelectException.class, () -> {
            PointOfService.validateMenuInput("not a number");
        });
        assertThrows(MenuSelectException.class, () -> {
            PointOfService.validateMenuInput("f");
        });
        assertThrows(MenuSelectException.class, () -> {
            PointOfService.validateMenuInput("7");
        });
	}
        
}
