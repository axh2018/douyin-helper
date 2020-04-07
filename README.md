### 在抖音上找到漂亮小姐姐帅气小哥哥以及一键移除收藏   

## 原理

- 打开《抖音短视频》APP，进入主界面
- 获取手机截图 
- 请求 百度人脸识别与分析 API
- 解析返回的人脸 Json 信息，对人脸检测
- 当颜值和年龄大于设定值时，点赞并关注

## 教程

* `JDK `1.8+
* 获取源码：`git clone https://github.com/axh2018/douyin-helper.git`
* 解压源码，用集成开发环境打开压缩后的项目
* 将`/src/Lib`中的五个`jar`包全部`Build Path`
* 手机数据线连接到你的机器，并打开抖音
* 运行`Main.java`

## 注意

- 如果不能运行，请进入手机设置，查看是否开启了`开发者模式`
- 某些手机`开发者模式`默认是没有的，需要进入`手机设置`->`关于手机`->`Android版本`,连续多次点击`Android版本`才会激活`开发者模式`
- 同时确保将下图中的`开发者模式`中的几个选项全部勾选
- 分辨率目前只支持`2340x1080`

![](https://raw.githubusercontent.com/axh2018/picgo_picture/master/20200331204402.png)

## ToDo

* 一键移除全部收藏
* 直播场景不能辨别
* 适配`微视`，`抖音极速版`
* 视频为`相关热点`时不能处理

## 贡献

* 可以改进欢迎`pr`，有`bug`欢迎提`issues`。

## Lisence

* [MIT](https://github.com/axh2018/douyin-helper/blob/master/LICENSE)