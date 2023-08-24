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
    private int blockStartNum;
    private int blockLastNum;
    private int lastPageNum;

    public Pageable() {
        blockStartNum = 0;
        blockLastNum = 0;
        lastPageNum = 0;
    }

    public void of(int pageNum, long loginedId) {
        makeBlock(pageNum);
        makeLastPageNum(loginedId);
    }
    public void fixBlockStartNum(int bsn) {
        this.blockStartNum = bsn;
    }

    public void fixBlockLastNum(int bln) {
        this.blockLastNum = bln;
    }

    public void fixLastPageNum(int lpn) {
        this.lastPageNum = lpn;
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
