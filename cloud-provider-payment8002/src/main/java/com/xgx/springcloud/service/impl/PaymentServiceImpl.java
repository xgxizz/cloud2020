package com.xgx.springcloud.service.impl;

import com.xgx.springcloud.entities.Payment;
import com.xgx.springcloud.dao.PaymentDao;
import com.xgx.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment){
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(Long id){

        return paymentDao.getPaymentById(id);

    }
}