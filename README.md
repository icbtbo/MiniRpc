# MiniRpc

## 各模块功能

- rpc-provider，服务提供者。负责发布 RPC 服务，接收和处理 RPC 请求。
- rpc-consumer，服务消费者。使用动态代理发起 RPC 远程调用，帮助使用者来屏蔽底层网络通信的细节。
- rpc-registry，注册中心模块。提供服务注册、服务发现、负载均衡的基本功能。
- rpc-protocol，网络通信模块。包含 RPC 协议的编解码器、序列化和反序列化工具等。
- rpc-core，基础类库。提供通用的工具类以及模型定义，例如 RPC 请求和响应类、RPC 服务元数据类等。
- rpc-facade，RPC 服务接口。包含服务提供者需要对外暴露的接口，本模块主要用于模拟真实 RPC 调用的测试。



## 启动项目

1. 在 rpc-consumer 的 controller 包下定义 api 接口

2. 启动 rpc-consumer 和 rpc-provider

3. 访问接口

   例如：

   ``` bash
   $ curl http://localhost:8080/hello
   
   hellomini rpc
   ```

   
