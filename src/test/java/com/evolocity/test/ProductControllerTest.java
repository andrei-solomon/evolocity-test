package com.evolocity.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {

    @MockBean
    private ProductController productControllerMock;

    @Autowired
    private MockMvc mockMvc;

    private Product product1;
    private Product product2;

    @Before
    public void setUp() throws Exception {
        product1 = new Product();
        product1.setId(1L);
        product1.setName("Mouse");
        product1.setQuantity(50);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Keyboard");
        product2.setQuantity(5);
    }

    @Test
    public void listAll() throws Exception {
        when(productControllerMock.listAll()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Mouse")))
                .andExpect(jsonPath("$[0].quantity", is(50)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Keyboard")))
                .andExpect(jsonPath("$[1].quantity", is(5)));
    }

    @Test
    public void getProduct() throws Exception {
        when(productControllerMock.get(1L)).thenReturn(product1);
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Mouse")))
                .andExpect(jsonPath("quantity", is(50)));
    }

    @Test
    public void getProductNotFound() throws Exception {
        when(productControllerMock.get(3L)).thenThrow(new ProductNotFoundException());

        mockMvc.perform(get("/products/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void saveProduct() throws Exception {
        when(productControllerMock.save(any())).thenReturn(product2);

        mockMvc.perform(post("/products")
                .content("{\"name\":\"Keyboard\",\"quantity\":5}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Keyboard")))
                .andExpect(jsonPath("quantity", is(5)));;
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }
}