package com.mmall.exercise;

import org.apache.commons.lang3.StringUtils;

public class AboutStringUtils {
    public static void main(String[] args) {
        System.out.println(StringUtils.isNotBlank(""));
        System.out.println(StringUtils.isNotBlank("     "));
        System.out.println(StringUtils.isNotBlank(null));
    }
}
