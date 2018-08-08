# html2wxml4J

#### 项目介绍
微信小程序端 [html2wxml](https://gitee.com/qwqoffice/html2wxml) 转换接口 Java版
基于JFinal+Jsoup+FastJson

#### 实现功能
目前仅实现解析Html并转换成JSON，将生成的JSON通过接口访问返回给微信小程序端

微信小程序端使用html2wxml组件版 按照html2wxml教程配置即可

html大部分标签已经支持，Video、Audio标签已经支持

HtmlToJson.java 核心工具类 使用FastJson

Pre标签里的代码支持代码着色高亮显示

![输入图片说明](https://images.gitee.com/uploads/images/2018/0808/104929_6d49305c_736.png "code.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005847_9030e7e4_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005924_0d71b43d_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005954_3727c431_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/010023_8f96fa8d_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/010042_a7533eeb_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005154_6bcd53eb_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/005352_8c4d2cf6_736.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0807/124214_d2e59467_736.png "屏幕截图.png")



#### 第三方库
Html解析：Jsoup
接口编写：JFinal

#### 安装和使用

 部署到服务器，按照html2wxml提供的组件版 使用方式只需要修改接口地址为部署项目的访问地址就可以了
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/011536_8d44cdaa_736.png "屏幕截图.png")

#### 支持本项目
![输入图片说明](https://images.gitee.com/uploads/images/2018/0806/010534_680bf8af_736.png "屏幕截图.png")




