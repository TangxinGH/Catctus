package com.zhu.cactus.location

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private const val EARTH_RADIUS = 6378137.0 //赤道半径

fun rad(d: Double): Double {
    return d * Math.PI / 180.0
}

fun GetDistance(
    lon1: Double,
    lat1: Double,
    lon2: Double,
    lat2: Double
): Double {
    val radLat1 = rad(lat1)
    val radLat2 = rad(lat2)
    val a = radLat1 - radLat2
    val b = rad(lon1) - rad(lon2)
    var s = 2 * Math.asin(
        sqrt(
            sin(a / 2).pow(2.0) + cos(radLat1) * cos(radLat2) * Math.sin(
                b / 2
            ).pow(2.0)
        )
    )
    s *= EARTH_RADIUS
    return s //单位米
}


/*
class calculateLineDistance {

    private static final  double EARTH_RADIUS = 6378137;//赤道半径
    private static double rad(double d){
        return d * Math.PI / 180.0;
    }
    public static double GetDistance(double lon1,double lat1,double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 *Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        return s;//单位米
    }
}*/