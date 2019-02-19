package com.etime.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf datas = (ByteBuf)msg;
        System.out.printf("%s 服务端收到数据： %s%n", LocalDateTime.now(),datas.toString(Charset.forName("utf-8")));
        datas = getByteBuf(ctx);
        ctx.channel().writeAndFlush(datas);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        ByteBuf datas = ctx.alloc().buffer();
        datas.writeBytes(str.getBytes());
        return datas;
    }
}
