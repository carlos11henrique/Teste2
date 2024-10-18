package com.snack.repositories;

import com.snack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private ProductRepository productRepository;
    private Product produto1;
    private Product produto2;

    @BeforeEach
    public void configurarMassaDeTeste() {
        productRepository = new ProductRepository();
        produto1 = new Product(1, "Hotdog", 4.00f, "C:\\Images\\HotDog.jpg");
        produto2 = new Product(2, "Burger", 5.00f, "C:\\Images\\Burger.jpg");
    }

    @Test
    public void deveAdicionarProdutoCorretamenteAoRepositorio() {
        productRepository.append(produto1);
        assertTrue(productRepository.getAll().contains(produto1));
    }

    @Test
    public void deveRecuperarProdutoCorretamentePorId() {
        productRepository.append(produto1);
        Product produtoRecuperado = productRepository.getById(1);
        assertEquals(produto1, produtoRecuperado);
    }

    @Test
    public void deveAtualizarProdutoComSucesso() {
        productRepository.append(produto1);
        Product produtoAtualizado = new Product(1, "Hotdog 2.0", 4.50f, "C:\\Images\\UpdatedHotDog.jpg");
        productRepository.update(1, produtoAtualizado);

        Product produtoRecuperado = productRepository.getById(1);
        assertEquals("Hotdog 2.0", produtoRecuperado.getDescription());
        assertEquals(4.50f, produtoRecuperado.getPrice());
        assertEquals("C:\\Images\\UpdatedHotDog.jpg", produtoRecuperado.getImage());
    }

    @Test
    public void deveRemoverProdutoExistentePorIdValido() {
        productRepository.append(produto1);
        productRepository.remove(1);
        assertFalse(productRepository.exists(1));
    }

    @Test
    public void deveRecuperarTodosOsProdutos() {
        productRepository.append(produto1);
        productRepository.append(produto2);
        List<Product> produtos = productRepository.getAll();
        assertEquals(2, produtos.size());
        assertTrue(produtos.contains(produto1));
        assertTrue(produtos.contains(produto2));
    }

    @Test
    public void deveValidarExistenciaDeProdutoNoRepositorio() {
        productRepository.append(produto1);
        assertTrue(productRepository.exists(1));
    }

    @Test
    public void deveManterTamanhoCorretoAoRemoverProdutoInexistente() {
        productRepository.append(produto1);
        productRepository.remove(99);
        assertEquals(1, productRepository.getAll().size());
    }

    @Test
    public void deveLidarCorretamenteComListaVaziaAoInicializarRepositorio() {
        List<Product> produtos = productRepository.getAll();
        assertTrue(produtos.isEmpty());
    }

    @Test
    public void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        assertThrows(RuntimeException.class, () -> {
            Product produtoInexistente = new Product(99, "Inexistente", 10.00f, "C:\\Images\\lasanha.jpg");
            productRepository.update(99, produtoInexistente);
        });
    }

    @Test
    public void devePermitirAdicaoDeProdutoComIdDuplicado() {
        productRepository.append(produto1);
        Product produtoDuplicado = new Product(1, "Hotdog 2.0", 4.00f, "C:\\Images\\hotdog2.0.jpg");
        productRepository.append(produtoDuplicado);

        List<Product> produtos = productRepository.getAll();
        assertEquals(2, produtos.size());
    }
}
