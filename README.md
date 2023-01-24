Restaurant Voting Application
=============================

Java Enterprise Graduation Project

Restaurant Voting Application implements a Voting System for users making decisions
which restaurant to have lunch at.
- There are two types of users: Admins and regular Users.
- Admins can input Restaurants and theirs lunch Menus of the day (dish names with prices).
- Menus must be provided in advance for next (or any further) date.
- Users can vote for a restaurant they want to have lunch at today.
- Only one vote per user per date counts. If User votes again the same day the existing vote should be deleted.
- Sending and deleting votes is possible if the time does not exceed 11:00. After that, the existing vote can't be
changed.

### REST API documentation:

[pafolder.com](http://pafolder.com)<br>
[localhost](http://localhost)

Credentials for testing:

```
Admin: admin@mail.com / admin
User: user@mail.com / password
```
