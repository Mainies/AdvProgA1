package assignment1.tests;

import static org.junit.Assert.*;

import org.junit.*;

import assignment1.*;
import assignment1.Order;
import assignment1.exceptions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class PointOfServiceTest {
	private PointOfService pos = new PointOfService();
	
	@Before
	public void setUp() {
		// reset everything, prices and food items
		pos = new PointOfService();
	}

	//update sale
	@Test
	public void updateSalesTestmeal() {
		//check update sales with a meal
		Order order = new Order(1, 1, 1, 1);
		pos.updatePOS(order);
		assertEquals((int) pos.soldItems.get("Fries"), 1);
		//assertEquals(pos.totalSales, 12.0);
	}
	
	@Test
	public void updateSalesTestnoMeal(){
	//check update sales without a meal
	Order order = new Order(1, 1, 1);
	pos.updatePOS(order);
	assertEquals((int) pos.soldItems.get("Fries"), 1);
	//assertEquals(pos.totalSales, 15.0);
	}
	
	@Test
	public void updatePriceCheck() {
		pos.getBurrito().setPrice(5);
		Order order = new Order(1, 0, 0);
		pos.updatePOS(order);
		//assertEquals((int) pos.totalSales, 5.0);
	}
	
	@Test
	public void updatePriceSalesMemory() {
		//check update sales without a meal
		Order order = new Order(1, 1, 1);
		pos.updatePOS(order);
		pos.getBurrito().setPrice(5);
		order = new Order(1, 0, 0);
		pos.updatePOS(order);
		//assertEquals(pos.totalSales, 20.0);
		assertEquals((int) pos.soldItems.get("Burrito"), 2);
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
	
	@Test
	public void testGetQuantity() {
        String simulatedUserInput = "5\n"; // User inputs '5' as the quantity
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        int result = pos.getQuantity();
        assertEquals(5, result);
    }
   
	
	@After
	public void tearDown() {
		System.setIn(System.in);
		System.setOut(System.out);
	}
}
