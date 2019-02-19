package com.etime.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9,100);

        print("allocate ByteBuf(9, 100)",byteBuf);

        byteBuf.writeBytes(new byte[]{1,2,3,4});
        print("writeBytes(1,2,3,4)",byteBuf);

        byteBuf.writeInt(12);
        print("writeInt(12)",byteBuf);

        byteBuf.writeBytes(new byte[]{5});
        print("writeBytes(5)",byteBuf);

        byteBuf.writeBytes(new byte[]{6});
        print("writeBytes(6)",byteBuf);

        System.out.println("getByte(3) return："+byteBuf.getByte(3));
        System.out.println("getShort(3) return："+byteBuf.getShort(3));
        System.out.println("getInt(3) return："+byteBuf.getInt(3));
        print("getByte()", byteBuf);

        byteBuf.setByte(byteBuf.readableBytes()+1,0);
        print("setByte()", byteBuf);

        byte[] dst = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(dst);
        print("readBytes(" + dst.length + ")", byteBuf);
    }

    private static void print(String s, ByteBuf byteBuf) {
        System.out.println("after============="+s+"=============");
        System.out.println("capacity()："+byteBuf.capacity());
        System.out.println("maxCapacity()："+byteBuf.maxCapacity());
        System.out.println("readIndex()："+byteBuf.readerIndex());
        System.out.println("readableBytes()："+byteBuf.readableBytes());
        System.out.println("isReadable()："+byteBuf.isReadable());
        System.out.println("writeIndex()："+byteBuf.writerIndex());
        System.out.println("writableBytes()："+byteBuf.writableBytes());
        System.out.println("isWritable()："+byteBuf.isWritable());
        System.out.println("maxWritableBytes()："+byteBuf.maxWritableBytes());
        System.out.println();
    }
}
