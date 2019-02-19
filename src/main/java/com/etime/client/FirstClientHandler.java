package com.etime.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now()+"  写出数据...");
        //获得数据
        ByteBuf datas = getByteBuf(ctx);
        //写出数据
        ctx.channel().writeAndFlush(datas);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf datas = (ByteBuf)msg;
        System.out.printf("%s  客户端收到数据： %s%n",LocalDateTime.now(),datas.toString(Charset.forName("utf-8")));
        //获得数据
        datas = getByteBuf(ctx);
        //写出数据
        ctx.channel().writeAndFlush(datas);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        // 2. 填充数据
        buffer.writeBytes(str.getBytes());
        return buffer;
    }
}
