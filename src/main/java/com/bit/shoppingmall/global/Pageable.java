package com.bit.shoppingmall.global;

import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.service.CartService;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Getter
public class Pageable {
    private CartService cartService = new CartService(new CartDao());
    private static final int pageCnt = 5;
    private int curPage;
    private int blockStartNum;
    private int blockLastNum;
    private int lastPageNum;
    private int pageLastCartItem;
    private int pageStartCartItem;

    public Pageable() {
        curPage = 1;
        blockStartNum = 0;
        blockLastNum = 0;
        pageLastCartItem = 5;
        pageStartCartItem = 0;
        lastPageNum = 0;
    }

    public void of(int pageNum, long loginedId) {
        makeBlock(pageNum);
        makeLastPageNum(loginedId);
    }

    public void fixCurPage(int cur) {
        this.curPage = cur;
    }

    public void fixPageLastCartItem(int pageLastCartItem) {
        this.pageLastCartItem = pageLastCartItem;
    }

    public void fixPageStartCartItem(int pageStartCartItem) {
        this.pageStartCartItem = pageStartCartItem;
    }

    public void makeBlock(int curPage) {
        int blockNum = 0;
        blockNum = (int)Math.floor((curPage-1)/pageCnt);
        blockStartNum = (pageCnt * blockNum) + 1;
        blockLastNum = blockStartNum + (pageCnt-1);
    }

    public void makeLastPageNum(long loginedId) {
        int size = cartService.get(loginedId).size();
        if(size % pageCnt == 0) {
            lastPageNum = (int)Math.floor(size/pageCnt);
        } else {
            lastPageNum = (int)Math.floor(size/pageCnt) + 1;
        }

    }

}
