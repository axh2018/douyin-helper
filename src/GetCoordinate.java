import com.alibaba.fastjson.*;

import java.io.*;

public class GetCoordinate
{
    //将json文件转为字符串
    public static String path = "screenConfig\\default.json";
    public static String json2String() throws IOException
    {
        StringBuilder result = new StringBuilder();
        InputStream in = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(in,"UTF-8");
        BufferedReader bufr = new BufferedReader(isr);
        String line = null;
        while ((line = bufr.readLine()) != null)
        {
            result.append(System.lineSeparator()+line);
        }
        isr.close();
        return result.toString();
    }
    //获取关注的x坐标
    public static int getFollowX() throws IOException
    {
        Object follow_bottomX = JSONPath.read(json2String(), "$.follow_bottom.x");
        return (int) follow_bottomX;
    }
    //获取关注的y坐标
    public static int getFollowY() throws IOException
    {
        Object follow_bottomY = JSONPath.read(json2String(), "$.follow_bottom.y");
        return (int) follow_bottomY;
    }
    //获取点赞的x坐标
    public static int getStarX() throws IOException
    {
        Object star_bottomX = JSONPath.read(json2String(), "$.star_bottom.x");
        return (int) star_bottomX;
    }
    //获取点赞的y坐标
    public static int getStarY() throws IOException
    {
        Object star_bottomY = JSONPath.read(json2String(), "$.star_bottom.y");
        return (int) star_bottomY;
    }
}
