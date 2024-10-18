package com.snack.facade;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private ProductFacade productFacade;
    private ProductApplication productApplication;
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setup() {
        productApplication = new ProductApplication(productRepository, productService);
        productService = new ProductService();
        productRepository = new ProductRepository();
        productFacade = new ProductFacade(productApplication);
    }

    @Test
    public void deveRetornarListaCompletaDeProdutos() {
        Product produto1 = new Product(1, "Hotdog", 5.00f, "hotdog.jpg");
        Product produto2 = new Product(2, "Burger", 7.50f, "burger.jpg");

        productFacade.append(produto1);
        productFacade.append(produto2);
        List<Product> listaProdutos = productFacade.getAll();
        assertEquals(2, listaProdutos.size());
        assertEquals("Hotdog", listaProdutos.get(0).getDescription());
        assertEquals("Burger", listaProdutos.get(1).getDescription());
    }

    @Test
    public void deveRetornarProdutoCorretoParaIdValido() {
        Product produto = new Product(1, "Hotdog", 5.00f, "hotdog.jpg");

        productFacade.append(produto);
        Product resultado = productFacade.getById(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Hotdog", resultado.getDescription());
    }

    @Test
    public void deveRetornarTrueParaIdExistenteEFalseParaIdInexistente() {
        Product produto = new Product(1, "Hotdog", 5.00f, "hotdog.jpg");
        productFacade.append(produto);
        assertTrue(productFacade.exists(1));
        assertFalse(productFacade.exists(999));
    }

    @Test
    public void deveAdicionarProdutoCorretamente() {
        Product produto = new Product(1, "Hotdog", 5.00f, "hotdog.jpg");
        productFacade.append(produto);
        Product resultado = productFacade.getById(1);
        assertNotNull(resultado);
        assertEquals("Hotdog", resultado.getDescription());
    }

    @Test
    public void deveRemoverProdutoExistentePorIdValido() {
        Product produto = new Product(1, "Hotdog", 5.00f, "hotdog.jpg");
        productFacade.append(produto);
        productFacade.remove(1);
        assertNull(productFacade.getById(1));
    }
}
