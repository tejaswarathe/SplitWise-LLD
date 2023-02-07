import data.Data;
import service.ExpenseService;
import service.UserService;

class SplitwiseApplication {

    public Data splitwisData = new Data();
    private UserService userService = new UserService();
    private ExpenseService expenseService = new ExpenseService();

    public ExpenseService getExpenseService() {
        return expenseService;
    }

    public Data getSplitwisData() {
        return splitwisData;
    }

    public UserService getUserService() {
        return userService;
    }

    public SplitwiseApplication() {
        addUsers();
    }

    private void addUsers() {
        userService.addUser("u1", "User 1", "tejaswarathe@fj.com");
        userService.addUser("u2", "User 2", "kunalsatpal@jf.com");
        userService.addUser("u3", "User 3", "ayushjain@jf.com");
        userService.addUser("u4", "User 4", "shilpijain@jf.com");
    }
}

/*
 * Entities
 * 1. User
 * a. userId
 * b. name
 * c. email
 * d. phoneNumber
 * e. balances - key value pair (UserId: balance)
 * 
 * 2. SplitType enum
 * a. EQUAL
 * b. EXACT
 * c. PERCENT
 * 
 * 3. Expense
 * a. amount - Integer
 * b. splitType - enum
 * 
 * 4. UserList
 * HashMap of User Object
 * 
 * 5.
 * 
 * 
 * Services
 * 
 * 1. AddExpense
 * 2. ShowExpense
 * 3.
 * 
 * 
 * 
 * 
 * Data
 * 1. UserData map
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */