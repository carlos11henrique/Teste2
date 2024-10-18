package com.snack.services;

import com.snack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    private ProductService productService;
    private Product product1;
    private String validImagePath = "C:\\Users\\Carlos\\Downloads\\teste2\\images";
    private String invalidImagePath = "/home/Carlos/Desktop/lasanha/InvalidaImg/maconha.jpg";

    @BeforeEach
    public void setUp() {
        productService = new ProductService();
        product1 = new Product(1, "Hotdog", 5.00f, validImagePath);
    }

    @Test
    public void TestarSalvarProdutoComImagemValida() {
        boolean result = productService.save(product1);
        assertTrue(result);
        File savedImage = new File(product1.getImage());
        assertTrue(savedImage.exists());
    }

    @Test
    public void TestarSalvarProdutoComImagemInexistente() {
        Product product2 = new Product(2, "lasanha", 50.00f, invalidImagePath);
        boolean result = productService.save(product2);
        assertFalse(result);
    }

    @Test
    public void TestarAtualizacaoDeProdutoExistente() {
        productService.save(product1);
        Product updatedProduct = new Product(1, "Lasanha dona cleide", 6.00f, validImagePath);
        productService.update(updatedProduct);
        File updatedImage = new File(updatedProduct.getImage());
        assertTrue(updatedImage.exists());
    }

    @Test
    public void TestarRemocaoDeProdutoExistente() {
        productService.save(product1);
        productService.remove(product1.getId());
        String imagePath = productService.getImagePathById(product1.getId());
        File imageFile = new File(imagePath);
        assertFalse(imageFile.exists(), "A imagem e produto removido");
    }



    @Test
    public void TestarObterCaminhoDaImagemPorId() {
        productService.save(product1);

        String imagePath = productService.getImagePathById(product1.getId());
        assertNotNull(imagePath);

        File imageFile = new File(imagePath);
        assertTrue(imageFile.exists());
    }
}
