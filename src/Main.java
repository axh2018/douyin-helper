import com.alibaba.fastjson.JSONPath;
import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

import javax.swing.text.html.Option;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main
{
    //设置APPID/AK/SK
    public static final String APP_ID = "19205497";
    public static final String API_KEY = "5OAmdU9UiMQc5Zwbx0zOxKGU";
    public static final String SECRET_KEY = "2kU7cIpiUP9ADpjPFjCcMOIYV4IGEMZd";
    //设置ADB
    public static final String adbHome = "src\\Lib\\platform-tools\\adb ";
    public static Process process = null;
    Random r = new Random();
    public static void main(String[] args) throws IOException
    {

        /*
        判断手机是否连接成功，失败则退出程序
         */
        String adbAccess = "";

        process = Runtime.getRuntime().exec(adbHome+"devices");
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = input.readLine()) != null)
            adbAccess += line;
        System.out.println(adbAccess);
        if(adbAccess.equals("List of devices attached"))
        {
            System.out.println("未检测到移动设备，请连接你的移动设备");
            System.exit(-1);
        }

        /*
        获取当前设备分辨率，不是2340*1080，则退出
         */

        String screenSize = "";
        String lines = "";
        process = Runtime.getRuntime().exec(adbHome+"shell wm size");
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((lines = in.readLine()) != null)
            screenSize = lines;
        System.out.println("当前设备分辨率"+screenSize);
        if(!screenSize.equals("Physical size: 1080x2340"))
        {
            System.out.println("请在2340*1080的设备下运行");
            System.exit(-1);
        }


        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 图片转Base64
        String url = "screen.png";
        String image = Base64Utils.ImageToBase64ByLocal(url);
        String imageType = "BASE64";

        // 人脸检测
        HashMap<String, String> options = new HashMap<>();
        options.put("face_field", "beauty,age,gender");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");


        JSONObject res = client.detect(image, imageType,options);

        // 处理返回Json数据
        Object age = JSONPath.read(res.toString(), "$.result.face_list[0].age");
        Object gender = JSONPath.read(res.toString(), "$.result.face_list[0].gender.type");
        Object beauty = JSONPath.read(res.toString(), "$.result.face_list[0].beauty");
        System.out.println("年龄"+age);
        System.out.println("性别"+gender);
        System.out.println("颜值"+beauty);


        int errorCode = res.getInt("error_code");
        if (errorCode == 0)
        {
            System.out.println("返回结果成功");
        }
        else
            System.out.println(res.getString("error_msg"));



        /*
        进入主循环
         */

        while (true)
        {
            screenShot();
            pullscreen();
            praise();
            follow();
            nextPage();
        }

    }

    /*
    截图
    */
    public static void screenShot() throws IOException
    {
        String cmd = adbHome + "shell screencap /sdcard/screen.png";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("已截屏");
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    /*
    复制图片
     */
    public static void pullscreen() throws IOException
    {
        String cmd = adbHome + "pull /sdcard/screen.png";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("正在拉取图片");
        try
        {
            Thread.sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    /*
    删除图片
     */
    public static void deleteScreen()
    {
        File screen = new File("screen.png");
        if(screen.exists())
            screen.delete();
    }
    /*
    点赞
     */
    public static void praise() throws IOException
    {
        String cmd = adbHome + "shell input tap 987 1368";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("已点赞");
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    /*
    关注
     */
    public static void follow() throws IOException
    {
        String cmd = adbHome + "shell input tap 987 1174";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("已关注");
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    /*
    翻页
     */
    public static void nextPage() throws IOException
    {
        String cmd = adbHome + "shell input swipe 500 700 500 200";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("下一页");
    }

}