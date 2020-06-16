package com.demoapp.sport.data.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Service to make http request
 */

/*
 In Java the Singleton pattern will ensure that there is only one instance of a class is created in the Java Virtual Machine.
 It is used to provide global point of access to the object.
 In terms of practical use Singleton patterns are used in logging, caches, thread pools, configuration settings, device driver objects.
  managing access to a resource which is shared by the entire application,
   and it would be destructive to potentially have multiple instances of the same class
 */

public class Api {
    private static Api instance;
    public static final String BASE_URL = "https://www.thesportsdb.com/";

    private SearchService searchService;

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    private Api() {
        build(BASE_URL);
    }

    public SearchService getService() {
        return searchService;
    }

    private void build(String url) {

        /*
        WHY AN OKHTTP Client?
         This default behavior leads to at least two separate OkHttp instances,
         which each keep their own request pooling, disk cache, routing logic, etc..
         Because they don't share these, your overall app will unnecessarily lose performance. So create a OKHttpclient and then
         share them across various retrofit instances.
        */

        /*
        In this tutorial you've learned that it's important to share the OkHttp instance,
        or at least its core, and converters between various Retrofit instances.
        This optimizes your app's performance with a very little implementation detail!
         */



        OkHttpClient client = new OkHttpClient.Builder()
                //A connect timeout defines a time period in which our client
                //should establish a connection with a target host.
                //By default, for the OkHttpClient, this timeout is set to 10 seconds.
                .connectTimeout(10, TimeUnit.SECONDS)
                //A read timeout is applied from the moment the connection between a client and
                // a target host has been successfully established
                //It defines a maximum time of inactivity between two data packets when waiting for the server's response.
                .readTimeout(30, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build();

        //Retrofit turns your HTTP API into a Java interface.
        //The Retrofit class generates an implementation of the SearchService interface.
        searchService = retrofit.create(SearchService.class);
    }
}