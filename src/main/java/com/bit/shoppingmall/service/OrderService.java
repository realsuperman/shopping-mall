package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dto.OrderItemDto;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    public OrderService() {}

    public List<OrderItemDto> makeListFromJson(BufferedReader reader) {
        String jsonBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        return gson.fromJson(jsonBody, new TypeToken<List<OrderItemDto>>(){}.getType());
    }
}
