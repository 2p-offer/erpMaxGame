package com.erp.core.rpc;

import com.erp.rpc.game.hello.HelloGrpc;
import com.erp.rpc.game.hello.HelloWorldApi;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Random;


@RpcService
public class SayHelloRpcService extends HelloGrpc.HelloImplBase {


    @Override
    public void sayHello(HelloWorldApi.HelloRequest request, StreamObserver<HelloWorldApi.HelloResponse> responseObserver) {
        try {

            String data = request.getData();
            String responseData = "Channel Connected - " + data;
            HelloWorldApi.HelloResponse response = HelloWorldApi.HelloResponse.newBuilder()
                    .setData(responseData)
                    .build();
            if (new Random().nextBoolean()) {
                Metadata metadata = new Metadata();
                metadata.put(RpcConstant.CUSTOM_ERROR_CODE_KEY, "802");
                throw Status.INTERNAL
                        .withCause(new RuntimeException("这是50%概率出现的业务异常"))
                        .withDescription("这是一个描述")
                        .augmentDescription("这是一个增强描述")
                        .asRuntimeException(metadata);
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}