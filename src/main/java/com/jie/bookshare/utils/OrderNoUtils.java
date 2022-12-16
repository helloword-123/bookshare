package com.jie.bookshare.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName OrderIdUtils
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/3/19 22:02
 */
public class OrderNoUtils {

    /**
     * 获取订单号: 19位
     * @return
     */
    public static String getOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            result.append(random.nextInt(10));
        }
        return newDate + result;
    }

}
