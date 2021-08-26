package com.ak.springcloud.controller;

import com.ak.springcloud.entities.CommonResult;
import com.ak.springcloud.entities.Payment;
import com.ak.springcloud.lib.Loadbalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {
    public  static final String PAYMENT_URL="http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @Resource //自己写的轮询方法
    private Loadbalancer loadbalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return  restTemplate.postForObject(PAYMENT_URL+
                "/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id){
        return  restTemplate.getForObject(PAYMENT_URL+
                "/payment/get/"+id,CommonResult.class);
    }

   /* *//**
     * 测试轮询方法 手写 使用此方法 要把@LoadBalanced注解注释掉
     * @return
     *//*
    @GetMapping(value = "/consumer/payment/lib")
    public String getPaymentLB()
    {
        List<ServiceInstance> instances= discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <=0) return null;
        ServiceInstance serviceInstance=loadbalancer.INSTANCE(instances);
        URI uri=serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/lib",String.class);
    }*/
}
