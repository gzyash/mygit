package com.etime.client;

import com.etime.server.FirstServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    public static final int MAX_RETRY = 4;
    public static final int PORT = 8000;
    public static final String HOST = "127.0.0.1";
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)//待测试，不求起作用呢
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("正在初始化客户端handler...");
                        ch.pipeline().addLast(new FirstClientHandler());
                        System.out.println("客户端handler初始化完毕...");
                    }
                });
        connection(bootstrap,HOST,PORT,MAX_RETRY);
    }
    //每隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接
    private static void connection(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(HOST,PORT).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("连接成功...");
                }else if(retry == 0){
                    System.err.println("超过重试次数...");
                    System.exit(0);
                }else{
                    int order = (MAX_RETRY - retry)+1;
                    int delay = 1 << order;
                    System.out.printf("%s :连接失败，第 [%d] 次重连%n", LocalDateTime.now(),order);
                    bootstrap.config().group().schedule(() -> connection(bootstrap,HOST,PORT,retry-1)
                                                        ,delay, TimeUnit.SECONDS);
                }
            }
        });
    }
}
