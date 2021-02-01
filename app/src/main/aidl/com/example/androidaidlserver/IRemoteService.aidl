package com.example.androidaidlserver;

interface IRemoteService {
     String getServerAppName();

     void doSomeThing(int action);

    oneway void onewayFunctionCall(int value);

     void noneOneWayFunctionCall(int value);
}