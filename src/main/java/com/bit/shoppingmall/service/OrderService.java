package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.dto.OrderItemDto;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequiredArgsConstructor
public class OrderService {

    private final OrderDetailDao orderDetailDao;
    private final OrderSetDao orderSetDao;
    private final CartDao cartDao;
    private final CargoDao cargoDao;

    // orderSetDao.insert()

    // orderDetailDao.insert() *** using <foreach/>***

    // orderDetailDao.updateStatus()

    // cargoDao.updateStatus()

    // cartDao.deleteByCartId()
}
