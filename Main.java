import data.Data;

import service.InputParser;

public class Main {
    static SplitwiseApplication splitwise = new SplitwiseApplication();
    static InputParser inputParser = new InputParser(splitwise.getExpenseService(), splitwise.getUserService());

    private static void showBalances() {
        System.out.println(Data.getUserList().getUserMap().get("u1").getBalances());
        System.out.println(Data.getUserList().getUserMap().get("u2").getBalances());
        System.out.println(Data.getUserList().getUserMap().get("u3").getBalances());
    }

    public static void main(String[] args) {

        System.out.println("Splitwise Works");
        inputParser.readInput("SHOW");
        inputParser.readInput("SHOW u1");
        inputParser.readInput("EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL");
        inputParser.readInput("SHOW u4");
        inputParser.readInput("SHOW u1");
        inputParser.readInput("EXPENSE u1 1250 2 u2 u3 EXACT 370 880");
        inputParser.readInput("SHOW");
        inputParser.readInput("EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20");
        inputParser.readInput("SHOW u1");
        inputParser.readInput("SHOW");

        showBalances();
    }

}
