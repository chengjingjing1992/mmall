package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVo {

    private List<CartProductListVo> cartProductListVos;
    private boolean allChecked;
    private BigDecimal cartTotalPrice;
    private String imageHost;

    public List<CartProductListVo> getCartProductListVos() {
        return cartProductListVos;
    }

    public void setCartProductListVos(List<CartProductListVo> cartProductListVos) {
        this.cartProductListVos = cartProductListVos;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
