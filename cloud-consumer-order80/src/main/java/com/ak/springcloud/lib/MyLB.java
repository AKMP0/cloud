package com.ak.springcloud.lib;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements Loadbalancer {

    //当前下标 原子类整数
    private AtomicInteger atomicInteger=new AtomicInteger(0);

    private int  getAndIncrement(){
        for(;;){
            int current=atomicInteger.get();
            int next=current > Integer.MAX_VALUE? 0:current+1;
            if (this.atomicInteger.compareAndSet(current,next)){
                return next;
            }

        }
    }
    @Override
    public ServiceInstance INSTANCE(List<ServiceInstance> serviceInstances) {

            int index =getAndIncrement()% serviceInstances.size();
        return serviceInstances.get(index);
    }
}
