package com.example.model; 

public class Product {

	private int id;
	private String name;
	private int count;

    public Product(int id, String name, int count) {
		this.id = id;
		this.name = name;
		this.count = count;
    }

	public void setId(int id) {
    	this.id = id;
	}

	public int getId() {
    	return id;
	}

	public void setName(String name) {
    	this.name = name;
	}

	public String getName() {
    	return name;
	}

	public void setCount(int count) {
    	this.count = this.count + count;
	}

	public int getCount() {
    	return count;
	}

    @Override
    public String toString() {
        return "ID: " + this.id + " Name: " + this.name + " Count: " + this.count;
    }


}
