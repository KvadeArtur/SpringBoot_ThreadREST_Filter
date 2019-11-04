package com.kvart.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.CompletableFuture;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    ProductController controller;

    @Test
    public void testGetFilters() throws Exception {

        String regEx = "^.*[eva].*$";
        String result = "{ \"id\": 32, \"name\": \"Mulo\", \"description\": \"qwerthdty\" }";
        CompletableFuture<ResponseEntity> resultString =
                CompletableFuture.completedFuture(result).thenApply(ResponseEntity::ok);

        when(controller.filters(regEx)).thenReturn(resultString);

        mvc.perform(get("/shop/product?nameFilter=^.*[eva].*$")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().
                        string(equalTo("{ \"id\": 32, \"name\": \"Mulo\", \"description\": \"qwerthdty\" }")));
    }
}
