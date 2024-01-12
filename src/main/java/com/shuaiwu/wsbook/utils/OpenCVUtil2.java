package com.shuaiwu.wsbook.utils;

import com.shuaiwu.wscommon.utils.CommonUtil;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * opencv工具类
 * 2024-01-11 21:09
 */
public class OpenCVUtil2 {

    // 1.图片预处理与车牌定位
    public static Mat imagePreprocess() {
        // 加载 OpenCV 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // 读取图像
        Mat image = Imgcodecs.imread(CommonUtil.pathConcat("001.jpg"));

        // 灰度化
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // 预处理
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);
        Imgproc.Canny(gray, gray, 50, 150);

        // 轮廓检测
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 找到最大的轮廓
        double maxArea = 0;
        Rect maxRect = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                maxRect = Imgproc.boundingRect(contour);
            }
        }

        // 抠取车牌区域
        if (maxRect != null) {
            Mat licensePlate = image.submat(maxRect);
            Imgcodecs.imwrite(CommonUtil.pathConcat("002.jpg"), licensePlate);

            // 进行车牌识别
        }

        // 图像上绘制车牌边界
        Imgproc.drawContours(image, contours, -1, new Scalar(0, 255, 0), 2);
        return image;
    }

    // 车牌切割

    // 车牌字符识别

    public static void main(String[] args) {
        imagePreprocess();
    }
}
