package com.jie.bookshare.utils;

public class LocationUtils {

    private static Double EARTH_RADIUS = 6378.137;

    private static Double rad(Double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离
     */
    public static Double getDistance(Double lat1, Double lng1, Double lat2,
                                     Double lng2) {
        Double radLat1 = rad(lat1);
        Double radLat2 = rad(lat2);
        Double a = radLat1 - radLat2;
        Double b = rad(lng1) - rad(lng2);
        Double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    /**
     * 计算中心经纬度与目标经纬度的距离（米）
     *
     * @param centerLon
     *            中心精度
     * @param centerLat
     *            中心纬度
     * @param targetLon
     *            需要计算的精度
     * @param targetLat
     *            需要计算的纬度
     * @return 米
     */
    public static Double distance(Double centerLon, Double centerLat, Double targetLon, Double targetLat) {

        Double jl_jd = 102834.74258026089786013677476285;// 每经度单位米;
        Double jl_wd = 111712.69150641055729984301412873;// 每纬度单位米;
        Double b = Math.abs((centerLat - targetLat) * jl_jd);
        Double a = Math.abs((centerLon - targetLon) * jl_wd);
        return Math.sqrt((a * a + b * b));
    }

    public static void main(String[] args) {
        Double distance = getDistance(34.2675560000, 108.9534750000,
                34.2464320000, 108.9534750000);
        System.out.println("距离" + distance / 1000 + "公里");
    }


}
