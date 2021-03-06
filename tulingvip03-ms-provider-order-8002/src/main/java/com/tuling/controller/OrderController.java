package com.tuling.controller;

import com.tuling.entity.OrderVo;
import com.tuling.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smlz on 2019/3/26.
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    private ServiceInstance serviceInstance;

    @RequestMapping("/queryOrdersByUserId/{userId}")
    public List<OrderVo> queryOrdersByUserId(@PathVariable("userId") Integer userId) throws InterruptedException {
        log.info("测试降级。。。。。。。");

        //超时降级 -1超时跳闸
        Thread.sleep(4000);
        //2异常跳闸
        if (userId == 2) {
            throw new InterruptedException();
        }
        //3宕机跳闸

        List<OrderVo> list = new ArrayList<>();
        OrderVo orderVo = new OrderVo();
        orderVo.setUserId(1);
        orderVo.setOrderId(1);
        orderVo.setOrderMoney(new BigDecimal(200));
        orderVo.setDbSource("tuling_source01");
        list.add(orderVo);
        return list;

        //return orderServiceImpl.queryOrdersByUserId(userId);
    }

    @RequestMapping("/queryAll")
    public List<OrderVo> queryAll() throws InterruptedException {

        //超时降级
        //Thread.sleep(4000);
        if(true) {
            throw new RuntimeException("不存在的用户");
        }

        List<OrderVo> list = new ArrayList<>();
        OrderVo orderVo = new OrderVo();
        orderVo.setUserId(1);
        orderVo.setOrderId(1);
        orderVo.setOrderMoney(new BigDecimal(200));
        orderVo.setDbSource("tuling_source01");
        list.add(orderVo);
        return list;

    }



    @RequestMapping("/getRegisterInfo")
    public String info() {
        return serviceInstance.getHost()+":"+serviceInstance.getPort();
    }
}
