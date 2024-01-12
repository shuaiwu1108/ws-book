package com.shuaiwu.wsbook.utils;

import com.shuaiwu.wscommon.utils.CommonUtil;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * opencv工具类
 * 2024-01-11 21:09
 */
public class OpenCVUtil {

    // 1.图片预处理与车牌定位
    public static Mat imagePreprocess() {
        // 加载 OpenCV 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = Imgcodecs.imread(CommonUtil.pathConcat("001.jpg"));

        // 将图像转换为 hsv 图像空间
        Mat hsv = new Mat();
        Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

        // 定义 hsv 蓝色范围
        Scalar lowerBlue = new Scalar(90, 80, 80);
        Scalar upperBlue = new Scalar(130, 255, 255);

        // 提取蓝色区域
        Mat mask = new Mat();
        Core.inRange(hsv, lowerBlue, upperBlue, mask);

        // 去除噪声
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, kernel);

        // 查找连接的轮廓
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 过滤小的轮廓
        List<MatOfPoint> plates = new ArrayList<>();
        for (MatOfPoint m : contours){
            double v = Imgproc.contourArea(m);
            if (v > 1000) {
                plates.add(m);
            }
        }

        // 图像上绘制车牌边界
        Imgproc.drawContours(image, plates, -1, new Scalar(0, 255, 0), 2);
        return image;
    }

    // 车牌切割

    // 车牌字符识别

    public static void main(String[] args) {
        imagePreprocess();
    }
}
