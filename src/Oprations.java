import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Oprations
{
    public static final String adbHome = "src\\Lib\\platform-tools\\adb ";
    public static Process process = null;

    //检测手机是否连接
    public static void  AdbConnection() throws IOException
    {
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
    }

    //获取手机分辨率
    public static void  getSize() throws IOException
    {
        String screenSize = "";
        String lines = "";
        process = Runtime.getRuntime().exec(adbHome+"shell wm size");
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((lines = in.readLine()) != null)
            screenSize = lines;
        System.out.println("当前设备分辨率"+screenSize);
    }

    //截图并复制图片
    public static void screenShot() throws IOException
    {
        String cmd = adbHome + "shell screencap -p /sdcard/screen.png";
        process = Runtime.getRuntime().exec(cmd);
        System.out.print("截屏--->");
    }
    public static void pull() throws IOException
    {
        String cmd_1 = adbHome + "pull /sdcard/screen.png screen.png";
        process = Runtime.getRuntime().exec(cmd_1);
        System.out.println(" 复制图片---> ");
    }


    //点赞
    public static void praise() throws IOException
    {
        sleep();
        String cmd = adbHome + "shell input tap "+
                     String.valueOf(GetCoordinate.getStarX()) + " "
                     + String.valueOf(GetCoordinate.getStarY());
        process = Runtime.getRuntime().exec(cmd);
        System.out.print("已点赞 ");
        sleep();
    }

    //关注
    public static void follow() throws IOException
    {
        sleep();
        String cmd = adbHome + "shell input tap "+
                     String.valueOf(GetCoordinate.getFollowX()) + " "
                     + String.valueOf(GetCoordinate.getFollowY());
        process = Runtime.getRuntime().exec(cmd);
        System.out.println(" 已关注");
        sleep();
    }

    //翻页
    public static void nextPage() throws IOException
    {
        sleep();
        String cmd = adbHome + " shell input swipe 540 1300 540 480";
        process = Runtime.getRuntime().exec(cmd);
        System.out.println("下一页");
        sleep();
    }
    //休眠
    public static void sleep()
    {
        try
        {
            Thread.sleep(150);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
