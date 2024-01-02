import java.util.HashMap;
import java.util.Map;

public class Customer {
    private String name;
    private Map<Product, Integer> purchaseHistory;

    private double totalSpent;
    private double balance;

    public Customer(String name) {
        this.name = name;
        this.purchaseHistory = new HashMap<>();
        this.totalSpent = 0;
    }

    public String toString() {
        return name + " who has spent $" + String.format("%.2f", totalSpent);
    }

    public void printPurchaseHistory() {
        System.out.println("Purchase history for " + name + ":");
        for (Map.Entry<Product, Integer> entry : purchaseHistory.entrySet()) {
            System.out.println(entry.getValue() + " x " + entry.getKey());
        }
    }
    public Map<Product, Integer> getPurchaseHistory() {
        return purchaseHistory;
    }

    public String getName() {
        return name;
    }
    public double getTotalPurchases() {
        return totalSpent;
    }

    public void addPurchase(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        int currentQuantity = purchaseHistory.getOrDefault(product, 0);
        purchaseHistory.put(product, currentQuantity + quantity);
        totalSpent += product.getPrice() * quantity;
    }


    

    public void purchase(Product product, int amount) {
    }

    public double getBalance() {
        double balance = 0;
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
