package com.master.qianyi.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * 计算工具类
 */
public class CalculateUtils {

    //整数相除 保留一位小数
    public static String division(int a ,int b){
        if(b==0){
            return "暂无评";
        }
        String result = "";
        float num =(float)a/b;

        DecimalFormat df = new DecimalFormat("0.0");

        result = df.format(num);

        return result;
    }

    public static String yuanToFen(String amount){
        NumberFormat format = NumberFormat.getInstance();
        try{
            Number number = format.parse(amount);
            double temp = number.doubleValue() * 100.0;
            format.setGroupingUsed(false);
            // 设置返回数的小数部分所允许的最大位数
            format.setMaximumFractionDigits(0);
            amount = format.format(temp);
        } catch (ParseException e){

        }
        return amount;
    }

    /**
     * 分转元，转换为bigDecimal在toString
     * @return
     */
    public static String fenToYuan(int price) {
        return BigDecimal.valueOf(Long.valueOf(price)).divide(new BigDecimal(100)).toString();
    }

    public static void main(String[] args) {
        System.out.println("元-分==" + fenToYuan(1));
    }

}
