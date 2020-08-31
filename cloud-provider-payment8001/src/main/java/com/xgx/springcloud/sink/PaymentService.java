package com.xgx.springcloud.sink;

import com.xgx.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    int create(Payment payment); //写

    Payment getPaymentById(@Param("id") Long id);  //读取
}
 
