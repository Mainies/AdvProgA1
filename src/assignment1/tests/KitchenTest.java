package assignment1.tests;

import assignment1.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KitchenTest{
	Kitchen kitchen = new Kitchen();
	
	@BeforeEach
	public void setUp(){
		Kitchen kitchen = new Kitchen();
		kitchen.cooked.clear();
		kitchen.cooked.put("Fries", 0);
        kitchen.cooked.put("Burritos", 0);
        kitchen.cooked.put("Soda", 0);
	}
	
	@Test
    void kitchenStartsWithNoFoodCooked() {
        assertEquals(0, kitchen.cooked.get("Fries"));
        assertEquals(0, kitchen.cooked.get("Burritos"));
        assertEquals(0, kitchen.cooked.get("Soda"));
        System.out.println("EmptyKitchenPass");
    }
    
	@Test
    void cookTimeForMoreBurritos(){
        Order order = new Order(3, 2, 0);
        int expectedFriesCookTime = 8;
        int expectedBurritosCookTime = 18;
        int cookTime = kitchen.cookTime(order);

        assertEquals(Math.max(expectedFriesCookTime, expectedBurritosCookTime), cookTime, "Expected Equal CookTime");
        System.out.println("Cook OrderTime Burritos Correct");
    }
	
	@Test
    void cookTimeForMoreFries(){
        Order order = new Order(1, 6, 0);
        int expectedFriesCookTime = 16;
        int expectedBurritosCookTime = 9;
        int cookTime = kitchen.cookTime(order);

        assertEquals(Math.max(expectedFriesCookTime, expectedBurritosCookTime), cookTime, "Expected Equal CookTime");
        System.out.println("Cook OrderTime Fries Correct");
    }
	
	@Test
    void checkLeftOverFries(){
        Order order = new Order(3, 2, 0);
        int cookTime = kitchen.cookTime(order);
        assertEquals(kitchen.cooked.get("Fries"), 3, "Cooked Fries not Entered Properly");
    }
	
	@Test
    void cookTimeWithFries(){
		kitchen.cooked.put("Fries", 2);
        Order order = new Order(0, 2, 0);
        int expectedFriesCookTime = 0;
        int expectedBurritosCookTime = 0;
        int cookTime = kitchen.cookTime(order);

        assertEquals(Math.max(expectedFriesCookTime, expectedBurritosCookTime), cookTime, "Expected Equal CookTime");
        System.out.println("Fries Tray with Cooking TimeSuccessful");
	}
	
    @Test
    void endOfDayReportsLeftoverFriesCorrectly() {
        kitchen.cooked.put("Fries", 5); 
        int leftovers = kitchen.endOfDay();
        assertEquals(5, leftovers);
        System.out.println("FriesTray Works");
    }
}
	
