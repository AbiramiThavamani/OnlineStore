package com.pluralsight;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }


    public int getQuantity() {
        return quantity;
    }


    public void increaseQuantity(){
        quantity++;
    }
    public void decreaseQuantity(){
        if (quantity > 1){
            quantity--;
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "product=" + product +
                ", quality=" + quantity +
                '}';
    }



}
