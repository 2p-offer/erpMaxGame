package com.erp.net.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

public class ProtoUtil {

    private ProtoUtil() {
    }

    public static String toJson(Message sourceMessage) throws InvalidProtocolBufferException {
        if (sourceMessage == null) {
            return null;
        }
        return JsonFormat.printer().omittingInsignificantWhitespace().print(sourceMessage);
    }
}
