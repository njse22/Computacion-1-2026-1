package com.example.model; 

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private List<Product> products; 

    public Warehouse() {
        products = new ArrayList<>();
        // Populate with some initial data
        products.add(new Product(1, "Laptop", 10));
        products.add(new Product(2, "Smartphone", 25));
        products.add(new Product(3, "Tablet", 15));
        products.add(new Product(4, "Monitor", 8));
    }
    
    public synchronized String getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product.toString();
            }
        }
        return "Product with ID " + id + " not found.";
    }

    public synchronized String updateProduct(int id, int count) {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setCount(count);
                return String.valueOf(product.getCount());
            }
        }
        return "Product with ID " + id + " not found.";
    }
}
