package assignment1;

import assignment1.exceptions.*;
import java.util.HashMap;
import java.util.Scanner;


public class PointOfService {
	// Core class used to interface with the user. Holds methods to take input and send to kitchen class for cooking
	
	// Instantiates a new food item on opening to access attributes for sales
	
	private Burrito burrito = new Burrito();
	private Fries fries = new Fries();
	private Soda soda = new Soda();
	public double totalSales = 0.00;
	public HashMap<String, Integer> soldItems = new HashMap<String, Integer>();

	
	public PointOfService() {
		//At instantiation, no items are sold.
		burrito = new Burrito();
		fries = new Fries();
		soda = new Soda();
		totalSales = 0.00;
		soldItems = new HashMap<String, Integer>();
		soldItems.put("Fries", 0);
		soldItems.put("Burrito", 0);
		soldItems.put("Soda", 0);
		soldItems.put("Meals", 0);
	}
	
	//getter method for ability to change PointOfService price
	public Burrito getBurrito() {
		return this.burrito;
	}
	
	public void openingHours(Kitchen kitchen) {
		//Used to be an recursive function that continues during opening hours of the restaurant.
		
	    PointOfService pos = new PointOfService();
	    boolean open = true;
	    
	    while (open) {
	        String optionSelect;
	        while(true) {
	            try {
	                pos.printMenu();
	                optionSelect = PointOfService.readUserInput();
	                PointOfService.validateMenuInput(optionSelect);
	                break;
	            } catch (MenuSelectException e) {
	                System.out.println("Please enter a valid input.");
	            }
	        }
	        switch (optionSelect) {
		        case "a":
		        	pos.orderFood(kitchen);
		        	break;
		        case "b":
		            pos.printSales();
		            break;
		        case "c":
		        	pos.updatePrice();
		        	break;
		        default:
		            open = false;
		            break;
		        }
	    	}
	    pos.closingTime(kitchen);
	}
	
	private void orderFood(Kitchen kitchen) {
		//Takes an order and returns the cooking time.
		try {
            Order current = this.buildOrder();
            int timeForCook = kitchen.cookTime(current);
            current.printOrder();
            System.out.printf("Estimated time to cook: %d minutes.%n", timeForCook);
            double endSale = this.checkout(current);
            this.cashier(endSale);
            this.updatePOS(current);
        } catch (Exception e) {
            System.out.println("Error processing order: " + e.getMessage());
        }
	}
	
	
	private void updateSaleQuantities(Order order) {
		this.soldItems.put("Fries", (this.soldItems.get("Fries") + order.getFries()));
		this.soldItems.put("Burrito", (this.soldItems.get("Burrito") + order.getBurritos()));
		this.soldItems.put("Soda", (this.soldItems.get("Soda") + order.getSodas()));
	}
	

	private void updateSales(Order order) {
		// Gets current price for each food item in order and adds to sales total.
		// Allows for changing sales totals.
		double sale = this.totalSales;
		sale += order.getFries() * this.fries.getPrice();
		sale += order.getBurritos() * this.burrito.getPrice();
		sale += order.getSodas() * this.soda.getPrice();
		sale -= order.getMeals() * 3;
		this.totalSales = sale;
	}
	
	
	public void updatePOS(Order order) {
		// Calls 2 methods to handle updating quantity of food sold and monetary value.
		this.updateSaleQuantities(order);
		this.updateSales(order);
	}
	
	
	public void newSale(String item, int price) {
		soldItems.put(item, (soldItems.get(item) + price));
	}
	
	public static String readUserInput() {
		// Best practice as commented by lab workshop. Makes it easy to reuse scanner.
		Scanner sc = new Scanner(System.in);
		return sc.nextLine().toLowerCase();
	}
	
	private Order newOrder() {
		// Creates a new order with no items sold
		Order currentOrder = new Order(0, 0, 0);
		return currentOrder;
	}
	
	private int getQuantity() {
		// Gets quantity of food item required by user and holds methods for handling input exceptions.
	    String input;
	    int quantity;
	    while (true) {
	        try {
	            System.out.println("How many would you like:");
	            input = PointOfService.readUserInput(); 
	            PointOfService.validateNumber(input); 
	            quantity = Integer.parseInt(input); 
	            PointOfService.wholeNumber(quantity); 
	            break; 
	        } catch (NotANumberException e) {
	            System.out.println("Please enter a valid number.");
	        } catch (NotWholeNumber e) {
	            System.out.println("Cannot enter a decimal. Please try again.");
	        } catch (NumberFormatException e) {
	            System.out.println("Please enter a valid whole number.");
	        }
	    }
	    return quantity;
	}
	
	public Order buildOrder() {
		/* Large method that allows a user to recursively input different food items and quantities.
		 * Adds multiple items to an order and returns and Order object.
		 */
		
	    boolean complete = true;
	    Order currentOrder = this.newOrder();
	    while (complete) {
	        String option;
	        this.printOrderOptions();
	        while (true) {
	            try {
	                option = PointOfService.readUserInput();
	                PointOfService.validateFoodInput(option);
	                break;
	            } catch (FoodSelectException e) {
	                System.out.println("Please enter the correct corresponding number.");
	            }
	        }
	        if (option.equals("5")) {
	            complete = false;
	            break;
	        }
	        int quant = 0;
	        if (!option.equals("4")) {
	        	while (true) {
	                try {
	                    quant = this.getQuantity();
	                    PointOfService.negativeInput(quant);
	                    break; 
	                } catch (InvalidNegativeNumber e) {
	                    System.out.println("Entered number cannot be negative. Please try again.");
	                }
	            }
	        } else if (option.equals("4")) {
	        	while (true) {
	                try {
	                    quant = this.getQuantity();
	                    PointOfService.negativeInput(quant);
	                    break; 
	                } catch (InvalidNegativeNumber e) {
	                    System.out.println("Entered number cannot be negative. Please try again.");
	                }
	        	}
	            this.soldItems.put("Meals", (this.soldItems.get("Meals") + quant));
	            //this.soldItems.put("Burrito", (this.soldItems.get("Burrito") + quant));
	            //this.soldItems.put("Soda", (this.soldItems.get("Soda") + quant));
	            //this.soldItems.put("Fries", (this.soldItems.get("Fries") + quant));
	            int burrito = currentOrder.getBurritos() + quant;
	            currentOrder.setBurritos(burrito);
	            int fries = currentOrder.getFries() + quant;
	            currentOrder.setBurritos(fries);
	            int soda = currentOrder.getSodas() + quant;
	            currentOrder.setFries(soda);
	            int meals = currentOrder.getMeals() + quant;
	            currentOrder.setMeals(meals);   
	        }
	        if (!option.equals("4") && !option.equals("5")) {
	            currentOrder.addToOrder(option, quant);
	        }
	    }
	    return currentOrder;
	}
	
	
	private double checkout(Order order) {
		//Calculates total price for current sale.
		double sale = 0.00;
		sale += order.getFries() * this.fries.getPrice();
		sale += order.getBurritos() * this.burrito.getPrice();
		sale += order.getSodas() * this.soda.getPrice();
		sale -= order.getMeals() * 3;
		return sale;
	}
	
	private void cashier(double saleamount) {
	    // Allows the PointOfService class to accept the appropriate amount of money and return change. 
	    double money = 0.00;
	    System.out.printf("Your total is $%.2f.%n", saleamount);
	    System.out.println("Please enter amount to pay: ");

	    while (true) {
	        try {
	            String input = PointOfService.readUserInput();
	            PointOfService.validateNumber(input); 
	            money = Double.parseDouble(input);

	            if (money < saleamount) {
	                System.out.println("Sorry, that is not enough money. Please enter a sufficient amount:");
	            } else {
	                break; 
	            }
	        } catch (NotANumberException e) {
	            System.out.println("Invalid input. Please enter a valid number:");
	        }
	    }

	    double change = money - saleamount;
	    System.out.printf("Your change is $%.2f. Thank you.%n", change);
	}
	
	public void updatePrice() {
		//Method to update price of individual food item. Recursive until valid inputs are met.
		String option;
		Double correctprice;
		
		while (true) {
            try {
            	this.printFoodOptions();
            	option = PointOfService.readUserInput();
                PointOfService.validateFoodUpdate(option);
                break;
            } catch (NotAFoodItem e) {
                System.out.println("Please enter the correct corresponding number.");
	            }
	        }
		
		while (true) {
            try {
                System.out.println("Please enter the new price: ");
            	String price = PointOfService.readUserInput();
                PointOfService.validateNumber(price);
                correctprice = Double.parseDouble(price);
                PointOfService.negativeInput(correctprice);
                break; 
            } catch (NotANumberException e) {
            	System.out.println("Please enter a valid number.");
            } catch (InvalidNegativeNumber e) {
                System.out.println("Entered number cannot be negative. Please try again.");
            	}   
			}
		
		switch (option) {
		case "1":
			this.burrito.setPrice(correctprice);
			break;
		case "2":
			this.fries.setPrice(correctprice);
			break;
		case "3":
			this.soda.setPrice(correctprice);
			break;
		default:
			System.out.println("Invalid food selection.");
			this.updatePrice();
		}
	}
	
	private void closingTime(Kitchen kitchen) {
		//Method for closing Point of Service and restaurant. Prints fries in kitchen and outputs final sales total.
		System.out.println("End of Day Totals.");
		System.out.printf("Number of Fries to throw out: %d.%n", kitchen.cooked.get("Fries"));
		this.printSales();
		System.out.println("Thank you for coming to Burrito King.");
	}
	
	// Printers
	
	private void printMenu() {
		// Prints generic option menu to user
		System.out.printf("%-10s%n", "=".repeat(60));
		System.out.println("Burrito King");
		System.out.printf("%-10s%n", "=".repeat(60));
		System.out.println("a) Order");
		System.out.println("b) Show sales report");
		System.out.println("c) Update Prices");
		System.out.printf("\t d) Exit%n");
		System.out.println("Enter your selection: ");
	}
	
	public void printSales() {
	    System.out.printf("%n%-10s%n", "=".repeat(60));
	    System.out.println("Burrito King Sales Report");
	    System.out.printf("%-12s%n", "=".repeat(40));
	    System.out.printf("%-12s| %-10s%n", "Item", "Quantity");
	    System.out.printf("%-12s%n", "=".repeat(40));
	    System.out.printf("%-12s| %-10d%n", "Burritos", this.soldItems.get("Burrito"));
	    System.out.printf("%-12s| %-10d%n", "Fries", this.soldItems.get("Fries"));
	    System.out.printf("%-12s| %-10d%n", "Soda", this.soldItems.get("Soda"));
	    System.out.printf("%-12s| %-10d%n", "Meals", this.soldItems.get("Meals"));
	    System.out.printf("%-12s%n", "=".repeat(40));
	    System.out.printf("Total Sales | %.2f%n", this.totalSales);
	    System.out.printf("%-12s%n", "=".repeat(40));
	}
	
	private void printOrderOptions() {
		// Prints food item option select for user.
		System.out.printf("\t Please select a food item.%n");
		System.out.println("1. Burrito");
		System.out.println("2. Fries");
		System.out.println("3. Soda");
		System.out.println("4. Meal Deal (1x Burrito, Fries, Soda)");
		System.out.println("5. Checkout");
	}
	
	private void printFoodOptions() {
		System.out.printf("\t Please select a food item.%n");
		System.out.println("1. Burrito");
		System.out.println("2. Fries");
		System.out.println("3. Soda");
	}
	
	
	// Exceptions Section
	
	public static void validateMenuInput(String input) throws MenuSelectException{
	    if (!input.matches("[abcdeABCDE]")) {
	        throw new MenuSelectException(input);
	    }
	}
	
	
	public static void validateFoodInput(String input) throws FoodSelectException{
		if (!input.matches("[12345]")) {
			throw new FoodSelectException(input);
		}
	}
	
	public static void validateFoodUpdate(String input) throws NotAFoodItem{
		if (!input.matches("[123]")) {
			throw new NotAFoodItem(input);
		}
	}
	
	public static void negativeInput(int input) throws InvalidNegativeNumber{
		if (input < 0) {
			throw new InvalidNegativeNumber(input);
		}
	}
	
	public static void negativeInput(double input) throws InvalidNegativeNumber{
		if (input < 0) {
			throw new InvalidNegativeNumber(input);
		}
	}
	
	public static void wholeNumber(double input) throws NotWholeNumber {
	    int intPart = (int) input;
	    if (input != intPart) { throw new NotWholeNumber(input);}
	}
	
	public static void wholeNumber(int input) throws NotWholeNumber {
	    int intPart = (int) input;
	    if (input != intPart) {throw new NotWholeNumber(input);
	    }
	}
	
	public static void validateNumber(String input) throws NotANumberException {
	    try {
	        Double.parseDouble(input);
	    } catch (NumberFormatException e) {throw new NotANumberException(input);}
	}

	
}	
