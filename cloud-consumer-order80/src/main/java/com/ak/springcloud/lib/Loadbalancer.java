package com.ak.springcloud.lib;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface Loadbalancer {

    ServiceInstance INSTANCE(List<ServiceInstance> serviceInstances);
}
