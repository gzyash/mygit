package com.etime.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static final int PORT = 8000;
    public static void main(String[] args) {
        //创建两个线程池，bossGroup用作接受客户端连接，workGroup用作处理客户端连接IO事件等
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        //通过netty引导类启动server
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("正在初始化服务端handler...");
                        ch.pipeline().addLast(new FirstServerHandler());
                        System.out.println("服务端handler初始化完毕...");
                    }
                });
        //绑定端口并同步等待
//        serverBootstrap.bind(PORT).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.printf("成功绑定端口: %d%n",PORT);
//            } else {
//                System.out.printf("绑定端口: %d，失败%n",PORT);
//            }
//        });
        serverBootstrap.bind(PORT).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.printf("成功绑定端口: %d%n",PORT);
                } else {
                    System.out.printf("绑定端口: %d，失败%n",PORT);
                }
            }
        });
    }
}
