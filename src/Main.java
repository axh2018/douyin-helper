import com.alibaba.fastjson.JSONPath;
import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

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
    public static String imageType = "BASE64";
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

        // 人脸检测
        HashMap<String, String> options = new HashMap<>();
        options.put("face_field", "beauty,age,gender");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");


        // 处理返回Json数据

        /*
        进入主循环
         */
        while (true)
        {
            //对一个视频只分析五次
            for (int i = 0; i < 5; i++)
            {
                screenShot();
                pull();
                sleep(0.5);
                //先截图并pull image
                String url = "screen.png";
                String image = Base64Util.encode(Base64Util.image2Bytes(url));
                // 图片转Base64编码
                JSONObject res = client.detect(image, imageType, options);
                //请求人脸识别API
                //如果状态码不为0,进入下一个循环,打印报错信息
                if (res.getInt("error_code") != 0)
                {
                    System.out.println("api调用失败,状态码" + res.getInt("error_code") + " 错误信息: " + res.getString("error_msg"));
                    continue;
                }
                else
                {
                    System.out.println("api调用成功,状态码" + res.getInt("error_code") + " 返回信息: " + res.getString("error_msg"));
                    Object age = JSONPath.read(res.toString(), "$.result.face_list[0].age");
                    Object gender = JSONPath.read(res.toString(), "$.result.face_list[0].gender.type");
                    Object beauty = JSONPath.read(res.toString(), "$.result.face_list[0].beauty");

                    //如果年龄,颜值,性别符合要求,点赞并关注
                    //先打印返回信息
                    System.out.print("年龄 " + age);
                    System.out.print(" 性别 " + gender);
                    System.out.print(" 颜值 " + beauty);
                    if (Integer.parseInt(age.toString()) > 16 && Double.parseDouble(beauty.toString()) > 50 && gender.equals("female"))
                    {
                        praise();
                        follow();
                        nextPage();
                        break;
                    }
                }
                //分析完后删除图片
                /*
                File picComputer = new File("screen.ong");
                if(picComputer.exists())
                    picComputer.delete();
                process = Runtime.getRuntime().exec("shell rm /sdcard/screen.png");
                 */
            }
            nextPage();
        }
    }

    /*
    截图并复制图片
     */
    public static void screenShot() throws IOException
    {
        String cmd = adbHome + "shell screencap -p /sdcard/screen.png";
        process = Runtime.getRuntime().exec(cmd);
        System.out.print("已截屏");
    }
    public static void pull() throws IOException
    {
        String cmd_1 = adbHome + "pull /sdcard/screen.png screen.png";
        process = Runtime.getRuntime().exec(cmd_1);
        System.out.println(" 复制图片 ");
    }

    /*
    点赞
     */
    public static void praise() throws IOException
    {
        String cmd = adbHome + "shell input tap 988 1321";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("已点赞");
    }
    /*
    关注
     */
    public static void follow() throws IOException
    {
        String cmd = adbHome + "shell input tap 988 1166";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("已关注");
    }
    /*
    翻页
     */
    public static void nextPage() throws IOException
    {
        String cmd = adbHome + "shell input swipe 500 900 500 200";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("下一页");
    }
    /*
    休眠
    */
    public static void sleep(double x)
    {
        try
        {
            Thread.sleep((long) (x*1000));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}