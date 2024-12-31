package com.example.BACKAppLiv.dto;

public class CartItemDto {

    private Long productId;
    private String name;
    private Long price;
    private String category;
    private String image;
    private int quantity;

    // Constructeur, getters et setters
    public CartItemDto(Long productId, String name, Long price, String category, String image, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
    }



    // Getters et setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return name;
    }

    public void setProductName(String productName) {
        this.name = productName;
    }

    public Long getProductPrice() {
        return price;
    }

    public void setProductPrice(Long productPrice) {
        this.price = productPrice;
    }

    public String getProductCategory() {
        return category;
    }

    public void setProductCategory(String productCategory) {
        this.category = productCategory;
    }

    public String getProductImage() {
        return image;
    }

    public void setProductImage(String productImage) {
        this.image = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}