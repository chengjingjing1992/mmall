package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Const {

    public static final String CURRENT_USER = "currentUser";
    public static final Integer COMMON_CUSTOMER=0;
    public static final Integer ADMIN=0;
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PHONE = "phone";

    public interface ProductListOrderBy{
        Set PRICE_ASC_DESC= Sets.newHashSet("price_desc","price_asc");
    }
    public interface CartChecked{
          int CHECKED=1;
          int UN_CHECKED=0;

          String LIMIT_NUM_SUCCESS="LIMIT_NUM_SUCCESS";
          String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL";


    }



    public enum productStatusOnSale{
        ON_SALE(1,"在线");
        private int code;
        private String desc;

        productStatusOnSale(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

}
