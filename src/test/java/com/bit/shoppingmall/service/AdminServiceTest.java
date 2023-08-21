package com.bit.shoppingmall.service;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.CargoDao;
import org.junit.jupiter.api.Test;

class AdminServiceTest extends RootTest {
    private AdminService adminService;
    private CargoDao cargoDao;

    @Test
    void selectAll() {
        adminService.selectAll();
    }

    @Override
    public void beforeHook() {
        super.beforeHook();
        adminService = new AdminService(new CargoDao());
    }
}
