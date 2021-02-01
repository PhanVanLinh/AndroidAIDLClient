package com.example.androidaidlserver;

interface IRemoteService {
     String getServerAppName();

     void doSomeThing(int action);
}