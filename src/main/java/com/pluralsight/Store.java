package com.pluralsight;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;



        loadInventory("products.csv", inventory);


        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {

        // This method should read a CSV file with product information and
        // populate the inventory ArrayList with Product objects. Each line
        // of the CSV file contains product information in the following format:
        //
        // id,name,price
        //
        // where id is a unique string identifier, name is the product name,
        // price is a double value representing the price of the product
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    inventory.add(new Product(id, name, price));
                } else {
                    System.out.println("products.csv: " + line);
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
            System.out.println("Error inventory: " + e.getMessage());

        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.
       /* System.out.println("List of products: ");
        for (Product product : inventory) {
            System.out.println(product.getId() + "|" + product.getName() + "|" + product.getPrice());
        }
        System.out.println("add items to your cart- ");
        System.out.println("Enter the ID of the product. ");
        System.out.println("Enter the selected product to the cart list. ");
        System.out.println("type 'done' to Finish shopping. ");

        String choice = scanner.nextLine();
        while (!choice.equalsIgnoreCase("done")) {
            boolean found = false;
            for (Product product : inventory) {
                if (product.getId().equalsIgnoreCase(choice)) {
                    cart.add(product);
                    System.out.println("Added" + product.getName() + " to your cart. ");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("product not found. please enter a valid ID or type 'done' to finish shopping. ");
            }
            choice = scanner.nextLine();

        }

    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.
      /*  System.out.println(" your cart list : ");
        if (cart.isEmpty()) {
            System.out.println("your cart list is empty. ");
        } else {
            for (Product product : cart) {
                System.out.println(product);
            }
            System.out.println("Total Amount: $ " + totalAmount);
            System.out.println("Enter the ID of the product you want to remove from cart 0r type 'done' to finish shopping: ");
            String choice = scanner.nextLine();
            while (!choice.equalsIgnoreCase("done")) {
                boolean found = false;
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getId().equalsIgnoreCase(choice)) {
                        totalAmount -= cart.get(i).getPrice();
                        cart.remove(i);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("product not found in your cart list .please enter a valid ID or type 'done' to finish shopping.");
                } else {
                    System.out.println("product removed from your cart list. ");
                }
                System.out.println("your cart list : ");
                for (Product product : cart) {
                    System.out.println(product);
                }
                System.out.println("Total Amount: $ " + totalAmount);
                System.out.println("Enter the ID of the product you want to remove from cart 0r type 'done' to finish shopping:");
                choice = scanner.nextLine();
            }
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
      /*  System.out.println("cart summary: ");
        if (cart.isEmpty()) {
            System.out.println("your cart is empty. no purchase made. ");
            return;
        }
        for (Product product : cart) {
            System.out.println(product);
            System.out.println("Total Amount: $ " + totalAmount);
            Scanner scanner = new Scanner(System.in);
            System.out.println("confirm purchase? (yes/no)");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                System.out.println("purchase confirmed. Total amount deducted from account: $" + totalAmount);
                cart.clear(); // clear cart after purchase
            } else {
                System.out.println("purchase canceled. no amount deducted from your account");
            }
        }

    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding Product object. If
        // no product with the specified ID is found, the method should return
        // null.

        System.out.println("Searching for product with ID: " + id);
        for (Product product : inventory) {
            if (product.getId().equalsIgnoreCase(id)) {
                System.out.println("Product found: " + product);
                return product;
            }
            System.out.println("Product not found. ");
        }
        System.out.println("Inventory is empty. ");
        return null; //product not found
    }
}



