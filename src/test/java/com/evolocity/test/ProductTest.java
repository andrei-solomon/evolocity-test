package com.evolocity.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    private Product product = new Product();

    @Test
    public void testProduct() {
        product.setId(new Long(5));
        product.setName("Mouse");
        product.setQuantity(3);

        assertEquals(product.getId(), new Long(5));
        assertEquals(product.getName(), "Mouse");
        assertEquals(product.getQuantity(), 3);
    }
}