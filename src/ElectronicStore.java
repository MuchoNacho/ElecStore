import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectronicStore {
  private String name;
  private ArrayList<Product> stock; // ArrayList to hold all products
  private ArrayList<Customer> customers; // ArrayList to hold all customers
  private double revenue;

  public ElectronicStore(String initName) {
    revenue = 0.0;
    name = initName;
    stock = new ArrayList<Product>();
    customers = new ArrayList<Customer>();
  }

  public String getName() {
    return name;
  }

  // Adds a product and returns true if successful
  public boolean addProduct(Product newProduct) {
    for (Product p : stock) {
      if (p.toString().equals(newProduct.toString())) {
        // Product with the same string representation already exists
        return false;
      }
    }
    stock.add(newProduct);
    return true;
  }

  // Registers a customer and returns true if successful
  public boolean registerCustomer(Customer newCustomer) {
    for (Customer c : customers) {
      if (c.getName().equals(newCustomer.getName())) {
        // Customer with the same name already exists
        return false;
      }
    }
    customers.add(newCustomer);
    return true;
  }

  // Returns a List of Customer objects registered with the ElectronicStore
  public List<Customer> getCustomers() {
    ArrayList<Customer> uniqueCustomers = new ArrayList<Customer>();
    for (Customer c : customers) {
      if (!uniqueCustomers.contains(c)) {
        uniqueCustomers.add(c);
      }
    }
    return uniqueCustomers;
  }

  // Returns a List of Product objects that match the search query and price range
  public List<Product> searchProducts(String x, double minPrice, double maxPrice) {
    ArrayList<Product> matchingProducts = new ArrayList<Product>();
    for (Product p : stock) {
      if (p.toString().toLowerCase().contains(x.toLowerCase())) {
        if ((minPrice < 0 || p.getPrice() >= minPrice) && (maxPrice < 0 || p.getPrice() <= maxPrice)) {
          matchingProducts.add(p);
        }
      }
    }
    return matchingProducts;
  }

  // Increases the stock amount of a product by the included amount
  // Returns true if the stock was updated, false otherwise
  public boolean addStock(Product p, int amount) {
    for (Product product : stock) {
      if (product.equals(p)) {
        product.addQuantity(amount);
        return true;
      }
    }
    return false;
  }

  // Sells a product to a customer and updates revenue and customer purchase history
  // Returns true if the sale was successful, false otherwise
  public boolean sellProduct(Product p, Customer c, int amount) {
    // Check if the product is in stock
    if (!stock.contains(p)) {
      return false;
    }

    // Check if the customer is registered
    if (!customers.contains(c)) {
      return false;
    }

    // Check if there are enough units in stock to complete the sale
    if (p.getStockQuantity() < amount) {
      return false;
    }

    // Sell the product to the customer and update revenue
    double saleAmount = p.sellUnits(amount);
    if (saleAmount > 0) {
      revenue += saleAmount;
      c.addPurchase(p, amount);
      return true;
    } else {
      return false;
    }
  }

  public List<Customer> getTopXCustomers(int x) {
    List<Customer> allCustomers = new ArrayList<>(customers);

    // Sort customers in descending order of their total purchases
    allCustomers.sort((c1, c2) -> Double.compare(c2.getTotalPurchases(), c1.getTotalPurchases()));

    // Return empty list if x <= 0 or no customers exist
    if (x <= 0 || allCustomers.isEmpty()) {
      return new ArrayList<>();
    }

    // Return all customers if x is greater than the number of customers in the store
    if (x > allCustomers.size()) {
      return allCustomers;
    }

    // Return top x customers
    return allCustomers.subList(0, x);
  }

  public boolean saveToFile(String filename) {
    try {
      StringBuilder sb = new StringBuilder();
      // Append store name and revenue
      sb.append("Store Name: ").append(name).append(System.lineSeparator());
      sb.append("Revenue: ").append(String.format("%.2f", revenue)).append(System.lineSeparator());

      // Append all products
      sb.append(System.lineSeparator()).append("Products:").append(System.lineSeparator());
      for (Product product : stock) {
        sb.append(product.toString()).append(System.lineSeparator());
      }

      // Append all customers and their purchase history
      sb.append(System.lineSeparator()).append("Customers:").append(System.lineSeparator());
      for (Customer customer : customers) {
        sb.append(customer.toString()).append(System.lineSeparator());
        Map<Product, Integer> purchaseHistory = customer.getPurchaseHistory();
        if (!purchaseHistory.isEmpty()) {
          sb.append("Purchase History:").append(System.lineSeparator());
          for (Map.Entry<Product, Integer> entry : purchaseHistory.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            sb.append(product.toString()).append(" x ").append(quantity).append(System.lineSeparator());
          }
        }
      }

      // Write data to file
      Files.write(Paths.get(filename), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

}