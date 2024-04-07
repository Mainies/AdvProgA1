package assignment1;

public class Order {
	/* Public class used to hold various food quantities
 	Order is used to retrieve quantities of food to be cooked or processed for sales
 	*/
	
	//Attributes
	private int numBurritos;
	private int numFries;
	private int numSoda;
	private int numMeals = 0;
	
	
	public static void main(String args[]) {
		Order myorder = new Order(3, 5, 2);
		myorder.printOrder();
	}
	
	public Order(int Burritos, int Fries, int Soda) {
		//instantiates a new order to build on.
		numBurritos = Burritos;
		numFries = Fries;
		numSoda = Soda;
		numMeals = 0;
	}
	
	public Order(int Burritos, int Fries, int Soda, int Meals) {
		//instantiates a new order to build on.
		numBurritos = Burritos;
		numFries = Fries;
		numSoda = Soda;
		numMeals = Meals;
	}
	
	public void addToOrder(String food, int number) {
		//Takes an input that corresponds to a menu with a quantity to update.
		if (food.equals("1")) {
			this.numBurritos += number;
		}
		
		else if (food.equals("2")) {
			this.numFries += number;
		}
		
		else if (food.equals("4")) {
			//caters for adding a meal (1x each food item)
			this.numBurritos += 1;
			this.numFries += 1;
			this.numSoda += 1;
			this.numMeals += 1;
		}
		else {
			//adds soda
			this.numSoda += number;
		}
	}
	
	public void printOrder() {
		//Printing method for order.
		System.out.printf("Your order is %d Burritos, %d Fries and %d Sodas.%n", 
				this.numBurritos, this.numFries, this.numSoda);
		if (this.numMeals > 0) {
			System.out.printf("You have ordered %d meal deal.%n", this.numMeals);
		}
	}
	
	public int getBurritos() {
		return numBurritos;
	}
	
	public int getFries() {
		return numFries;
	}
	
	public int getSodas() {
		return numSoda;
	}
	
	public int getMeals() {
		return numMeals;
	}
	
	public void setBurritos(int burritos) {
		numBurritos = burritos;
	}
	
	public void setFries(int fries) {
		numFries = fries;
	}

	
	public void setSodas(int sodas) {
		numSoda = sodas;
	}
	
	public void setMeals(int meals) {
		numMeals = meals;
	}

	
	
}
