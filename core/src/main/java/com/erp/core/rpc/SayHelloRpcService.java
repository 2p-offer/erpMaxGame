package com.erp.core.rpc;

import com.erp.rpc.game.hello.HelloGrpc;
import com.erp.rpc.game.hello.HelloWorldApi;
import io.grpc.stub.StreamObserver;


@RpcService
public class SayHelloRpcService extends HelloGrpc.HelloImplBase {

    @Override
    public void sayHello(HelloWorldApi.HelloRequest request, StreamObserver<HelloWorldApi.HelloResponse> responseObserver) {
        String data = request.getData();
        String responseData = "Channel Connected - " + data;
        HelloWorldApi.HelloResponse response = HelloWorldApi.HelloResponse.newBuilder()
                .setData(responseData)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}