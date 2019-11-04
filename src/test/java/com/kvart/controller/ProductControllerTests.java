package com.kvart.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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
        String resultStr = "{ \"id\": 32, \"name\": \"Mulo\", \"description\": \"qwerthdty\" }";

        CompletableFuture<ResponseEntity> fullResult =
                CompletableFuture.completedFuture(resultStr).thenApply(ResponseEntity::ok);

        when(controller.filters(regEx)).thenReturn(fullResult);


        MvcResult mvcResult = mvc.perform(get("/shop/product?nameFilter=" + regEx))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content()
                        .string("{ \"id\": 32, \"name\": \"Mulo\", \"description\": \"qwerthdty\" }"));

        verify(controller, times(1)).filters(regEx);
    }
}
