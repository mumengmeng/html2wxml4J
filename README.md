# html2wxml4J

#### 项目介绍
微信小程序端 [html2wxml](https://gitee.com/qwqoffice/html2wxml) 转换接口 Java版
基于JFinal+Jsoup

#### 实现功能
目前仅实现解析Html并转换成JSON，将生成的JSON通过接口访问返回给微信小程序端，
微信小程序端使用html2wxml组件版 按照html2wxml教程配置即可

![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005847_9030e7e4_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005924_0d71b43d_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005954_3727c431_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/010023_8f96fa8d_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/010042_a7533eeb_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005154_6bcd53eb_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005352_8c4d2cf6_736.png "屏幕截图.png")


#### TODO List
1、实现pre标签代码高亮，计划使用Highlight.js 的Java版实现
2、实现video和audio标签解析

#### 第三方库
Html解析：Jsoup
接口编写：JFinal

#### 安装和使用

 部署到服务器，按照html2wxml提供的组件版 使用方式只需要修改接口地址为部署项目的访问地址就可以了




