package com.kza.server.client;

import com.kza.server.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kza on 2015/10/20.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        ByteBuf msg = (ByteBuf) obj; // (1)
        int len = msg.readableBytes();
        byte[] arr = new byte[len];
        msg.readBytes(arr);
        System.out.println(StringUtil.bytesToHexString(arr));
        try {
//                ctx.writeAndFlush(msg);
            ctx.close();
        } finally {
            msg.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("client channelActive..");
        String orgVal = "00 00 00 b9 02 00 00 06 01 9c 0b 00 01 00 00 00 29 63 6f 6d 2e 76 69 70 2e 6f 73 70 2e 63 61 74 65 67 6f 72 79 2e 61 70 69 2e 63 6f 6d 6d 2e 43 61 74 65 67 6f 72 79 41 50 49 0b 00 02 00 00 00 1c 67 65 74 43 61 74 65 67 6f 72 79 53 70 65 63 69 61 6c 41 74 74 72 69 62 75 74 65 73 0b 00 03 00 00 00 05 31 2e 30 2e 30 08 00 04 00 00 00 00 0a 00 0b 00 00 00 00 00 00 00 00 0a 00 0a 00 00 00 00 00 00 00 00 0a 00 0e 00 00 00 00 00 00 00 00 08 00 0f 0a c8 56 91 0d 00 10 0b 0b 00 00 00 00 0a 00 17 00 00 00 00 00 00 00 00 00 08 00 01 00 00 01 d1 08 00 02 00 00 02 3d 00 03";
        String hex = orgVal.replaceAll("\\s", "");
        byte[] arr = StringUtil.hexStringToByte(hex.toLowerCase());

        ctx.writeAndFlush(Unpooled.wrappedBuffer(arr));
    }
}
