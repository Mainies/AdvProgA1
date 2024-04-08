Functionality:
* Object Oriented Design (Part B)
* Sufficient Unit Tests created
* Ability to keep adding food to a single order until the user wishes to checkout
* Correct change/money handling
* Custom exception handling of all tested inputs (Negative numbers, invalid character strings, intended numerical inputs)
* Running sales reports available
* Recurring menu that only exits during a customer input
* Implementation of meals and accurate sales totals from meals
* Ability to order multiple meals at once
* Ability to update price within menu
* Running sales total that maintains the correct price of old sales even after price changes
* Correct cooking time calculations
* Ongoing memory of cooked fries, correct cooking time calculations with held fries and correct return of wasted fries on close.


Design:

Food is an abstract class that is extended by Burrito, Soda and Fries. This was done to implement polymorphism into the design.
Order is an abstract data type that is build through user input via the Point of Service. It maintains the quantity of Burritos, fries, sodas and meals.
The order is sent to the kitchen to calculate cooking time and used from the PointOfService to update its sales.
The Kitchen maintains the current cooked food and has methods that calculate the cooking time of an order (finding the time for fries and burritos and returning the largest as the limiting factor).
The PointOfService is the main user interface that handles user inputs, maintains the running sales totals, and contains the printer methods to interact with the user.
