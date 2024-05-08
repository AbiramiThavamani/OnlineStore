package com.pluralsight;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Store {
    private static ArrayList<Product> inventory = new ArrayList<>();
    private static ArrayList<CartItem> cart = new ArrayList<>();
    private static double totalAmount = 0;
    private static int change;

    public static void main(String[] args) {
        loadInventory("products.csv");

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Display menu and get user choice until they choose to exit

        while (running) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("Options: ");
            System.out.println("1. Display Products");
            System.out.println("2. Display Cart");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(scanner);
                    break;
                case 2:
                    displayCart(scanner);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        scanner.close();
    }


    public static void loadInventory(String fileName) {

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

                    Product product = new Product(id, name, price);
                    inventory.add(product);
                } else {
                    System.out.println("Invalid CSV line: " + line);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error Loading inventory: " + e.getMessage());
        }
    }

    public static void displayProducts(Scanner scanner) {
        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.
        System.out.println("Available Products: ");
        for (Product product : inventory) {
            System.out.println(product.getId() + ":" + product.getName() + "-$" + product.getPrice());
        }
        System.out.println("Options: ");
        System.out.println("1. Add to Cart");
        System.out.println("2. Back to home");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                addToCart(scanner);
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void addToCart(Scanner scanner) {
        System.out.println("enter the Id of the product to add to cart:");
        String productId = scanner.nextLine();
        Product product = findProductById(productId);
        if (product != null) {
            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProduct().getId().equals(productId)) {
                    item.increaseQuantity();
                    found = true;
                    break;
                }
            }
            if (!found) {
                cart.add(new CartItem(product));
            }
            totalAmount += product.getPrice();
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }


    public static void displayCart(Scanner scanner) {
        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.

        System.out.println("Cart: ");
        for (CartItem individualCartItem : cart) {
            Product product = individualCartItem.getProduct();
            System.out.println("ID : " + product.getId() +" Name : "+product.getName() + " Price: " +product.getPrice() + " Quantity: "+individualCartItem.getQuantity());
        }
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Options: ");
        System.out.println("1. Check Out");
        System.out.println("2. Remove Product");
        System.out.println("3. Back to Home");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                checkOut(scanner);
                break;
            case 2:
                removeProduct(scanner);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }

    }

    public static void checkOut(Scanner scanner) {
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
        System.out.println("Total amount owned: $" + totalAmount);
        System.out.println("please enter the Payment amount in cash: ");
        double payment = scanner.nextDouble();
        if (payment < totalAmount) {
            System.out.println("Insufficient payment please provide enough cash to cover the total amount.");
            return;
        }
        double change = payment - totalAmount;
        System.out.println("Change owed: $" + change);
        salesReceipt(totalAmount, payment, change);
        cart.clear();
        totalAmount = 0;
    }

    public static void removeProduct(Scanner scanner) {
        System.out.println("enter the Id of the product to remove from cart: ");
        String productId = scanner.nextLine();
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                totalAmount -= item.getProduct().getPrice() * item.getQuantity();
                cart.remove(item);
                System.out.println("Product removed from cart.");
                return;
            }

        }
        System.out.println("product not found in cart.");
    }

    private static void salesReceipt(double totalAmount, double paidAmount, double changeAmount) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            String fileName = dateFormat.format(new Date()) + ".txt";
            FileWriter writer = new FileWriter(fileName);

            System.out.println("Sales Receipt:");
            writer.write("Sales Receipt:" + "\n");

            System.out.println("Order Date:" + new Date());
            writer.write("Order Date:" + new Date() + "\n");

            System.out.println("All Line items:");
            writer.write("Line items:" + "\n");
            for (CartItem item : cart) {
                System.out.println(item.getProduct().getName() + "\t$" + item.getProduct().getPrice() + "\tQuantity:" + item.getQuantity());
                writer.write(item.getProduct().getName() + "\t$" + item.getProduct().getPrice() + "\tQuantity:" + item.getQuantity() + "\n");
            }

            System.out.println("Sales total : "+totalAmount);
            System.out.println("Amount paid : "+paidAmount);
            System.out.println("Change given : "+changeAmount);

            writer.write("Sales total : "+totalAmount+"\n");
            writer.write("Amount paid : "+paidAmount+"\n");
            writer.write("Change given : "+changeAmount+"\n");

            writer.flush();
            writer.close();
        } catch (Exception ex) {
            System.out.println("Error to generating sales receipt");
            ex.printStackTrace();
        }
    }


    private static Product findProductById(String id) {
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
            System.out.println("Product not found! ");
        }
        System.out.println("Inventory is empty. ");
        return null; //product not found
    }
}



