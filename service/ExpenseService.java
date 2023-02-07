package service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

import data.Data;
import models.Expense;
import models.SplitType;
import models.User;
import models.UserList;

public class ExpenseService {

    UserList splitdata = Data.userList;

    public void addExpense(Expense expense, String payingUser, Map<String, Float> splitMap) {
        try {
            validateUnEqualSplits(expense, splitMap);
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
        if (expense.splitType == SplitType.EQUAL) {
            divideExpenseEqually(expense.amount, payingUser);
        } else {
            divideExpenses(expense.amount, payingUser, splitMap, expense.splitType);
        }

    }

    private void validateUnEqualSplits(Expense expense, Map<String, Float> splitMap) throws Exception {
        if (expense.splitType == SplitType.EXACT) {
            if (!isExactSplitValid(expense.amount, splitMap)) {
                throw new Exception("Sum of Exact amounts should be equal to Total Amount");
            }
        } else if (expense.splitType == SplitType.PERCENT) {
            if (!isPercentSplitValid(splitMap)) {
                throw new Exception("Sum of all percentages must be equal to 100");
            }
        }
    }

    private boolean isExactSplitValid(Float amount, Map<String, Float> splitMap) {
        Float sumOfSplits = (float) 0;
        for (String userId : splitMap.keySet()) {
            sumOfSplits += splitMap.get(userId);
        }
        return (float) sumOfSplits == (float) amount;
    }

    private boolean isPercentSplitValid(Map<String, Float> splitMap) {
        Float sumOfPercentages = (float) 0;
        for (String userId : splitMap.keySet()) {
            sumOfPercentages += splitMap.get(userId);
        }
        return sumOfPercentages == 100;
    }

    private void divideExpenses(Float amount, String payingUser, Map<String, Float> splitMap,
            SplitType splitType) {
        User payingUserData = splitdata.getUserMap().get(payingUser);
        boolean isFirstUser = true;
        for (String userId : splitMap.keySet()) {
            User currentUser = splitdata.getUserMap().get(userId);
            Float share = getSplitShare(splitMap.get(userId), amount, splitType, isFirstUser);
            isFirstUser = false;
            if (!userId.equals(payingUser)) {
                // update data in current user
                Float balanceOfPayingUserInCurrentUser = currentUser.getBalances().get(payingUser);
                balanceOfPayingUserInCurrentUser -= share;
                currentUser.getBalances().put(payingUser, balanceOfPayingUserInCurrentUser);
                // update data in paying user
                Float balanceOfCurrentUserInPayingUser = payingUserData.getBalances().get(userId);
                balanceOfCurrentUserInPayingUser += share;
                payingUserData.getBalances().put(userId, balanceOfCurrentUserInPayingUser);
            }
        }
    }

    private void divideExpenseEqually(Float amount, String payingUser) {
        User payingUserData = splitdata.getUserMap().get(payingUser);
        boolean isFirstUser = true;
        for (String userId : splitdata.userMap.keySet()) {
            User currentUser = splitdata.getUserMap().get(userId);
            Float share = getSplitShare(amount / splitdata.userMap.keySet().size(), amount,
                    SplitType.EQUAL, isFirstUser);
            isFirstUser = false;
            if (!userId.equals(payingUser)) {
                // update data in current user
                Float balanceOfPayingUserInCurrentUser = currentUser.getBalances().get(payingUser);
                balanceOfPayingUserInCurrentUser -= share;
                currentUser.getBalances().put(payingUser, balanceOfPayingUserInCurrentUser);
                // update data in paying user
                Float balanceOfCurrentUserInPayingUser = payingUserData.getBalances().get(userId);
                balanceOfCurrentUserInPayingUser += share;
                payingUserData.getBalances().put(userId, balanceOfCurrentUserInPayingUser);
            }
        }
    }

    private Float getSplitShare(Float share, Float amount, SplitType splitType, boolean isFirstUser) {
        if (splitType == SplitType.PERCENT) {
            share = (share * amount) / 100;
        }
        final DecimalFormat df = new DecimalFormat("0.00");

        if (isFirstUser) {
            df.setRoundingMode(RoundingMode.UP);
        } else {
            df.setRoundingMode(RoundingMode.DOWN);
        }

        return Float.parseFloat(df.format(share));
    }

    public void getExpense(String userId) {
        User currentUser = splitdata.userMap.get(userId);
        Boolean noBalancesForCurrentUser = true;
        for (String fellowUserId : currentUser.getBalances().keySet()) {
            User fellowUser = splitdata.userMap.get(fellowUserId);
            Float fellowUserBalance = currentUser.getBalances().get(fellowUserId);
            if (fellowUserBalance != (float) 0) {
                noBalancesForCurrentUser = false;
            }
            if (fellowUserBalance < 0) {
                System.out.println(
                        currentUser.getName() + " owes " + fellowUser.getName() + ": " + Math.abs(fellowUserBalance));
            } else if (fellowUserBalance > 0) {
                System.out.println(
                        fellowUser.getName() + " owes " + currentUser.getName() + ": " + Math.abs(fellowUserBalance));
            }
        }
        if (noBalancesForCurrentUser) {
            System.out.println("No Balances");
        }
    }

    public void getExpense() {
        // change Implementation
        Set<String> userList = splitdata.userMap.keySet();
        Boolean noBalancesForUsers = true;
        for (String userId : userList) {
            User currentUser = splitdata.userMap.get(userId);
            for (String fellowUserId : currentUser.getBalances().keySet()) {
                User fellowUser = splitdata.userMap.get(fellowUserId);
                Float fellowUserBalance = currentUser.getBalances().get(fellowUserId);
                if (fellowUserBalance != (float) 0) {
                    noBalancesForUsers = false;
                }
                if (fellowUserBalance < 0) {

                    System.out.println(
                            currentUser.getName() + " owes " + fellowUser.getName() + ": "
                                    + Math.abs(fellowUserBalance));
                }

            }
        }
        if (noBalancesForUsers) {
            System.out.println("No Balances");
        }
    }

}
