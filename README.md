# Plugin-in插件化
<<<<<<< HEAD
Shadow：https://github.com/Tencent/Shadow

 V2.3.0 Latest
 ![image](https://github.com/gaoleicoding/Plug-in/raw/master/pics/1.png)

## 1、Shadow兼容：
targetsdk 31 
Gradle 7.2与AGP 7.1.1

## 2、Shadow支持特性
 ![image](https://github.com/gaoleicoding/Plug-in/raw/master/pics/2.png)

## 3、Shadow的不足之处：
（1）官方文档描述不详细
（2）官方Demo有点混乱
（3）官方demo中sample/maven/manager-project中FastPluginManager获取so有误，报错Caused by: java.lang.UnsatisfiedLinkError
。so找不到，原因是projects/sample/maven/manager-project，此demo使用v2.2.1中FastPluginManager获取so有误：https://github.com/Tencent/Shadow/blob/master/projects/sample/maven/manager-project/sample-manager/src/main/java/com/tencent/shadow/sample/manager/FastPluginManager.java

应该使用projects/sample/source/manager-project，此demo使用v2.3.0：https://github.com/Tencent/Shadow/blob/master/projects/sample/source/sample-manager/src/main/java/com/tencent/shadow/sample/manager/FastPluginManager.java


##4、此项目使用Shadow实现了如下功能：
（1）完成插件化Demo，包含三个插件
（2）完成宿主和插件的交互
（3）完成插件之间的跳转
（4）有插件调用宿主能力


##5、使用Shadow开发避坑指南：
（1）加载多个插件，需要对应多个manger和ProcessService，如下图：
 ![image](https://github.com/gaoleicoding/Plug-in/raw/master/pics/3.png)
（2）插件使用宿主能力，避免重复冗余代码。需要在插件中用到hostWhiteList和compileOnly，如下图：
 ![image](https://github.com/gaoleicoding/Plug-in/raw/master/pics/4.png)
 ![image](https://github.com/gaoleicoding/Plug-in/raw/master/pics/5.png)



