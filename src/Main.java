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
    public static String imageType = "BASE64";
    public static void main(String[] args) throws IOException
    {

        //判断手机是否连接成功，失败则退出程序
        Oprations.AdbConnection();

        //获取当前设备分辨率
        Oprations.getSize();

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
                Oprations.screenShot();

                Oprations.pull();
                //Oprations.sleep();
                //截图并pull image
                String url = "screen.png";
                String image = Base64Util.encode(Base64Util.image2Bytes(url));
                // 图片转Base64编码
                JSONObject res = client.detect(image,imageType,options);
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
                    System.out.println(" 颜值 " + beauty);
                    if (Integer.parseInt(age.toString()) > 16 && Double.parseDouble(beauty.toString()) > 60 && gender.equals("female"))
                    {
                        Oprations.praise();
                        Oprations.follow();
                        break;
                    }
                }
            }
            Oprations.nextPage();
        }
    }
}