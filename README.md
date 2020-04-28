# Transfer-Station

* 下载中转站，利用云服务器，远程控制家里或公司的下载机启动Aria2下载任务
* ***导入方式： Idea - Open - 本项目根目录Pom文件*** 

## 所需环境

1. 服务器端需要有固定的公网IP，阿里云等具有固定公网ip的云主机也可以

2. 下载机需要**安装Aria2，并启用rpc**，配置rpc为**6800端口**。

## 各Module功能介绍

### ZoraCloudTransferStation 框架包

* 维护依赖版本
* 配置打包参数
* 管理Modules

### TransferStation-Base 公共依赖包

* 配置公共参数 / 公共类 / 公共工具 / 公共依赖
* 维护密钥信息
* 配置服务器端URL前缀地址

### TransferStation-Cloud 服务器端

* 依赖TransferStation-Base包

* 通过携带密钥的REST API接收任务的增删改请求
* 通过固定接口提供单个任务的信息并修改任务状态

### TransferStation-Consumer 下载机端

* 依赖TransferStation-Base包

* 通过定时任务请求服务器端获取任务
* 通过Aria2 RPC请求调用Aria2启动下载任务
