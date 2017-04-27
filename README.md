Begin by forking [this](https://github.com/TIY-Charlotte-Java/Sales-System) repository.

Your task is to write a web-based sales system. 

But first, enjoy this gif that basically summarizes everything you need to know about kids:

![kid](http://i.imgur.com/8xlbAb1.gif)

Okay, let's do this.

## Requirements
This system should:

- Login users
   - This can be a form on your homepage
   - It can submit a username to `POST /login`.
   - You can have a predefined set of users beforehand.
- Allow a user to build a "cart" of items.
   - This can be a form post to `/add-to-cart`.
   - The "cart" is basically a list of the items in the user's current order.
   - You can just have three boxes here which comprise an item (quantity, name, and amount)
   - The user gets to pick (not like in real life) the item, its cost, and its quantity before it gets added to the cart.
- Allow a user to "checkout" the cart (via a button, perhaps, on the homepage)
   - This can be a `GET` request to `/checkout`.
   - "Checking Out" is basically showing them an order summary page, while clearing their cart.
   - This summary page should show the user data, and the details of their order (the quantities, amount, and total amount)
- Use Maven or Gradle
- Be deployed to Heroku

All of this information (the users, the items in their cart, and the orders) should be stored in a SQL database.

There should be 3 SQL tables that store this data:
1. `users`
2. `items` (will have an order_id column)
3. `orders` (will have a user_id column)

Users have orders, orders have items. 
