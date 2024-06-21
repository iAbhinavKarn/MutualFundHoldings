package com.mfholdings.mfholdings.Wrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncWrapper {

    @Async
    public <T,R> CompletableFuture<R> execute(Function<T,R> function, T requestInput){
        return CompletableFuture.completedFuture(function.apply(requestInput));
    }
}
