package service;

import java.util.HashMap;
import java.util.Map;

import models.Expense;
import models.SplitType;

public class InputParser {

    ExpenseService expenseService;
    UserService userService;

    public InputParser(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    public void readInput(String inputString) {
        String[] inputParams = inputString.split(" ", 0);
        String action = inputParams[0];
        if (action.equals("SHOW")) {
            readShowAction(inputParams);
        } else {
            readExpenseAction(inputParams);
        }
    }

    private void readShowAction(String[] input) {
        if (input.length > 1) {
            String userId = input[1];
            expenseService.getExpense(userId);
        } else {
            expenseService.getExpense();
        }
    }

    private void readExpenseAction(String[] input) {
        String payingUser = input[1];
        Float amount = Float.parseFloat(input[2]);
        Integer numberOfUsers = Integer.parseInt(input[3]);
        String splitType = input[4 + numberOfUsers];
        Map<String, Float> splitMap = new HashMap<>();

        if (SplitType.valueOf(splitType) == SplitType.EQUAL) {
            expenseService.addExpense(new Expense(amount, SplitType.EQUAL), payingUser, null);
        } else {
            for (int i = 4; i < 4 + numberOfUsers; i++) {
                int indexCorrespondingToCurrentUser = i + numberOfUsers + 1;
                splitMap.put(input[i], Float.valueOf(input[indexCorrespondingToCurrentUser]));
            }
            expenseService.addExpense(new Expense(amount, SplitType.valueOf(splitType)), payingUser, splitMap);
        }
    }
}
