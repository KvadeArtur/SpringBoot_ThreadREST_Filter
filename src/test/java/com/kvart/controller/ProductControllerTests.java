package com.kvart.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    public ProductController productController;

    @Test
    public void testGetFilters() throws Exception {

        String rexet = "^.*[eva].*$";
        String result = "{ \"id\": 32, \"name\": \"Mulo\", \"description\": \"qwerthdty\" }";
        CompletableFuture<ResponseEntity> resultString =
                CompletableFuture.completedFuture(result).thenApply(ResponseEntity::ok);

        when(productController.filters(rexet)).thenReturn(resultString);

        mvc.perform(get("/shop/product?nameFilter=^.*[eva].*$"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(32)))
                .andExpect(jsonPath("$.name", is("Mulo")))
                .andExpect(jsonPath("$.description", is("qwerthdty")));

        verify(productController, times(1)).filters(rexet);





//        mvc.perform(get("/shop/product?nameFilter=^.*[eva].*$")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().
//                        string(equalTo("{ \"id\": 32, \"name\": \"Mulo\", \"description\": \"qwerthdty\" }")));
    }
}
