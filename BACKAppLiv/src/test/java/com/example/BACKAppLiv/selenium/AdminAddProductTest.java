package com.example.BACKAppLiv.selenium;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AdminAddProductTest {

    @Test
    @DisplayName("Admin Add Product Test")
    @Description("Test to verify that an admin can add a product successfully")
    public void testAddProduct() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            login(driver, wait);
            addProduct(driver, wait);
        } finally {
            driver.quit();
        }
    }

    @Step("Login as admin")
    public void login(WebDriver driver, WebDriverWait wait) {
        driver.get("http://localhost:4200/login");
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("sign_In"));
        usernameField.sendKeys("admin");
        emailField.sendKeys("admin@admin.com");
        passwordField.sendKeys("admin1234");
        loginButton.click();
        wait.until(ExpectedConditions.urlContains("/admin"));
    }

    @Step("Add a new product")
    public void addProduct(WebDriver driver, WebDriverWait wait) {
        WebElement addProductButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("AddProduit")));
        addProductButton.click();
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        WebElement categoryField = driver.findElement(By.name("category"));
        WebElement addButton = driver.findElement(By.name("Add"));
        nameField.sendKeys("seleniumTest");
        priceField.sendKeys("19.99");
        categoryField.sendKeys("Test Category");
        addButton.click();
        System.out.println("Product added successfully: " + driver.getPageSource().contains("seleniumTest"));
    }
}