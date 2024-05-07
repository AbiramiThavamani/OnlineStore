package com.pluralsight;

public class Cart {
    private Product product;
    private int quality;

    public Cart(Product product, int quality) {
        this.product = product;
        this.quality = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
    public void increaseQuality(){
        quality++;
    }
    public void decreaseQuality(){
        if (quality > 1){
            quality--;
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "product=" + product +
                ", quality=" + quality +
                '}';
    }
}
