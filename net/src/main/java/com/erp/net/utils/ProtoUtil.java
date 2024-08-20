package com.erp.net.utils;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ProtoUtil {

    private static final Logger logger = LogManager.getLogger(ProtoUtil.class);

    private ProtoUtil() {
    }

    public static String toJson(Message sourceMessage) {
        return toJson(sourceMessage, true);
    }

    public static String toJson(Message sourceMessage, boolean prettyFormat) {
        if (sourceMessage == null) {
            return null;
        }
        try {
            JsonFormat.Printer printer = JsonFormat.printer();
            if (!prettyFormat) {
                printer = printer.omittingInsignificantWhitespace();
            }
            return printer.print(sourceMessage);
        } catch (IOException e) {
            logger.error("proto 序列化为json失败 {} {}", sourceMessage, e);
        }
        return null;
    }
}
