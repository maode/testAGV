
测试上位机与AGV下位机各模块通信情况的客户端程序。

## 包结构:

client 基于java.net.socket实现的"模拟客户端主动链接AGV"

clientSh 基于java.net.socket实现的"模拟客户端主动链接上海的AGV"

nettybak 基于netty实现的一个测试版本

通信测试入口：com.his.net.agv.Main

## PLC测试
![testPLC](/testPLC.png)

## Q600测试
![testQ600](/testQ600.png)
