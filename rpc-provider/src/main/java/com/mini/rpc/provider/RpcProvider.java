package com.mini.rpc.provider;

import com.mini.rpc.codec.MiniRpcDecoder;
import com.mini.rpc.codec.MiniRpcEncoder;
import com.mini.rpc.core.RpcServiceHelper;
import com.mini.rpc.core.ServiceMeta;
import com.mini.rpc.handler.RpcRequestHandler;
import com.mini.rpc.handler.RpcResponseHandler;
import com.mini.rpc.provider.annotion.RpcService;
import com.mini.rpc.registry.RegistryService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc 服务类，用于启动netty以及根据注解发布服务
 * @date 2022/6/17 2:57 下午
 */
@Slf4j
public class RpcProvider implements InitializingBean, BeanPostProcessor {

    private String serverAddress;
    private final int serverPort;
    /** 服务注册器，用于发布服务到注册中心 */
    private final RegistryService serviceRegistry;;

    /** 用于存储已发布的服务: serviceKey->bean*/
    private final Map<String, Object> rpcServiceMap = new HashMap<>();

    public RpcProvider(int serverPort, RegistryService serviceRegistry) {
        this.serverPort = serverPort;
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * 启动netty服务
    */
    private void startRpcServer() throws Exception {

//        this.serverAddress = InetAddress.getLocalHost().getHostAddress();
        this.serverAddress = "127.0.0.1";
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MiniRpcEncoder())
                                    .addLast(new MiniRpcDecoder())
                                    .addLast(new RpcRequestHandler(rpcServiceMap));
                        }

                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(this.serverAddress, this.serverPort).sync();
            log.info("server addr {} started on port {}", this.serverAddress, this.serverPort);
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(()->{
            try {
                startRpcServer();
            } catch (Exception e) {
                log.error("Error occurred when start rpc server", e);
            }
        }).start();
    }



    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 拿到标注的 @RpcService 注解信息
        RpcService rpcServiceAnnotation = bean.getClass().getAnnotation(RpcService.class);
        if(null != rpcServiceAnnotation){
            // 若存在该注解才进行处理

            String serviceName = rpcServiceAnnotation.serviceInterface().getName();
            String serviceVersion = rpcServiceAnnotation.serviceVersion();

            try {
                // 封装 服务元数据 对象
                ServiceMeta serviceMeta = new ServiceMeta();
                serviceMeta.setServiceName(serviceName);
                serviceMeta.setServiceVersion(serviceVersion);
                serviceMeta.setServiceAddr(this.serverAddress);
                serviceMeta.setServicePort(this.serverPort);

                // 发布 服务元数据 到注册中心
                serviceRegistry.register(serviceMeta);

                // 将该服务信息保存在服务端的map中
                rpcServiceMap.put(RpcServiceHelper.buildServiceKey(serviceName, serviceVersion), bean);
            } catch (Exception e) {
                log.error("failed to register service {}#{}", serviceName, serviceVersion, e);
            }
        }

        return bean;
    }
}
