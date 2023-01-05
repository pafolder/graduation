<p align=center> Restaurant Voting Application
===============================
</p>
<p align=center>
Java Enterprise Graduation Project
</p>
Restaurant Voting Application (RVA) implements a Voting System for customers making their decisions which restaurant to have lunch at.

There are 2 types of users: Admins and regular Users (Customers).
Admins can input Restaurants and theirs lunch Menus of the day (dish names with prices).
The Menus can be provided in advance for any further date.
Users (Customers) can vote for a restaurant they want to have lunch at today (or any other day provided that Admin has
already input the menu).
Only one vote per user per date counts.
If the User votes again the same day, the vote will be overridden unless it was sent after 11:00.
In this case, the existing vote cannot be changed.

[REST API documentation (Swagger)](http://localhost:8080/swagger-ui/index.html)

[Login page](http://localhost:8080/login) 
