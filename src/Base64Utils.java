import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@SuppressWarnings("restriction")
class Base64Utils
{
    public static void main(String[] args) throws Exception {
        //图片--->base64
        //本地图片
        String url = "screen.png";
        String str = Base64Utils.ImageToBase64ByLocal(url);
        System.out.println(str);
    }
    public static String ImageToBase64ByLocal(String imgFile)
    {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }
}

/*
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

public static String imageToBase64(String path)
{// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try
        {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // 对字节数组Base64编码 JDK8以上
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);// 返回Base64编码过的字节数组字符串
    }
 */