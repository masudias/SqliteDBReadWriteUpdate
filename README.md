# Sqlite DB Read/Write/Update
This is a skeleton project for sqlite database operations in Android. This project contains a `RecyclerView` which shows a list of users. The users are stored in the sqlite database. A user can be added, deleted or can be updated. 

Each user has two information: 
* User name
* User creation time

These two informations are stored in the user table of sqlite databse. The `RecyclerView` shows the list of the user stored in the user table. There is a delete button in each of the rows in the `RecyclerView`. By pressing the delete button, the user will be removed from the user table and the list will be updated. 

If you want to add a new user, you need to click the floating action button which will navigate to another activity, just to take the new user name as input. After adding the new user from the `AddNewUserActivity`, when you return back to the `MainActivity`, the user list will be updated automatically with the newly added user. The purpose of adding a new user in a different activity is to show, registering to the content of the user table is working smoothly. 

If you want to update a user, you might also go to another activity which is `UpdateUserActivity` by clicking the edit button in the user list. The name can be updated inside another activity, when returned to `MainActivity` after updating the name, the list will be updated accordingly. 

The whole purpose is to show how the database operations can be done using sqlite database along with an implementation of content observer, so that the update in the user table can be seen immediately in the list while you are registering for the content observer to the user table using the following command from your `MainActivity`. 

`this.registerContentObserver(cursor, DBConstants.DB_TABLE_USER_URI);`
