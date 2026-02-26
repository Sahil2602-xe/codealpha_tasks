import java.io.*;
import java.util.*;

// Stock class
class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

// Transaction class
class Transaction {
    String type;
    String symbol;
    int quantity;

    Transaction(String type, String symbol, int quantity) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
    }
}

// User class
class User {
    double balance = 10000; // starting balance
    HashMap<String, Integer> portfolio = new HashMap<>();
    ArrayList<Transaction> history = new ArrayList<>();
}

public class StockTradingPlatform {

    static ArrayList<Stock> market = new ArrayList<>();
    static User user = new User();

    public static void main(String[] args) {

        market.add(new Stock("AAPL", 150));
        market.add(new Stock("GOOG", 2800));
        market.add(new Stock("TSLA", 700));

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save Portfolio");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1 -> showMarket();
                case 2 -> buyStock(sc);
                case 3 -> sellStock(sc);
                case 4 -> showPortfolio();
                case 5 -> savePortfolio();
            }

        } while (choice != 6);

        sc.close();
    }

    static void showMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock s : market) {
            System.out.println(s.symbol + " : $" + s.price);
        }
    }

    static void buyStock(Scanner sc) {
        System.out.print("Enter stock symbol: ");
        String symbol = sc.next().toUpperCase();

        Stock stock = findStock(symbol);
        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        double cost = qty * stock.price;
        if (cost > user.balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        user.balance -= cost;
        user.portfolio.put(symbol,
                user.portfolio.getOrDefault(symbol, 0) + qty);
        user.history.add(new Transaction("BUY", symbol, qty));

        System.out.println("Stock purchased successfully!");
    }

    static void sellStock(Scanner sc) {
        System.out.print("Enter stock symbol: ");
        String symbol = sc.next().toUpperCase();

        if (!user.portfolio.containsKey(symbol)) {
            System.out.println("You don't own this stock!");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        int owned = user.portfolio.get(symbol);
        if (qty > owned) {
            System.out.println("Not enough shares!");
            return;
        }

        Stock stock = findStock(symbol);
        user.balance += qty * stock.price;
        user.portfolio.put(symbol, owned - qty);
        user.history.add(new Transaction("SELL", symbol, qty));

        System.out.println("Stock sold successfully!");
    }

    static void showPortfolio() {
        System.out.println("\n--- Portfolio ---");
        System.out.println("Balance: $" + user.balance);

        for (String symbol : user.portfolio.keySet()) {
            System.out.println(symbol + " : " + user.portfolio.get(symbol));
        }
    }

    static void savePortfolio() {
        try {
            PrintWriter writer = new PrintWriter("portfolio.txt");
            writer.println("Balance: " + user.balance);

            for (String symbol : user.portfolio.keySet()) {
                writer.println(symbol + " : " + user.portfolio.get(symbol));
            }

            writer.close();
            System.out.println("Portfolio saved to file!");

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    static Stock findStock(String symbol) {
        for (Stock s : market) {
            if (s.symbol.equals(symbol)) return s;
        }
        return null;
    }
}