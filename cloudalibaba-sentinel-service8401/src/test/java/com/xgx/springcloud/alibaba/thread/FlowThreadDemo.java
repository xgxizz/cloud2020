package com.xgx.springcloud.alibaba.thread;/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @description: 摘自spring cloud alibaba -- sentinel流量控制demo,感觉计时器用的很妙。
 * @author: xgx
 * @date: 2020/9/9 18:54
 */


/**
 * @author jialiang.linjl
 */
public class FlowThreadDemo {

    private static AtomicInteger pass = new AtomicInteger();
    private static AtomicInteger block = new AtomicInteger();
    private static AtomicInteger total = new AtomicInteger();
    private static AtomicInteger activeThread = new AtomicInteger();

    private static volatile boolean stop = false;
    private static final int threadCount = 100;

    private static int seconds = 60 + 40;
    private static volatile int methodBRunningTime = 2000;

    public static void main(String[] args) throws Exception {
        System.out.println(
                "MethodA will call methodB. After running for a while, methodB becomes fast, "
                        + "which make methodA also become fast ");
        initFlowRule();//初始化流控规则
        Entry methodA = null;
        try {
            methodA = SphU.entry("methodA");
        } catch (BlockException e) {
            e.printStackTrace();
        } finally {
            if (methodA != null) {


                methodA.exit();
            }
        }

//        TimeUnit.SECONDS.sleep(5);
//        tick();
        //methodA有限流规则，methodB没有限流规则
        //大体逻辑：创建100个线程，每个线程都会调用methodA 以及methodB;
        //当执行完成methodB后，activeThread减1.
        //同时伴随着一个计时器，当seconds = 40时，methodB加速执行，同时会伴随着methodA也变快执行
//        for (int i = 0; i < threadCount; i++) {
//            Thread entryThread = new Thread(() -> {
//                while (true) {
//                    Entry methodA = null;
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(5);
//                        methodA = SphU.entry("methodA");//Checking all {@link Rule}s about the resource.
//                        /*MethodA业务逻辑 - 开始*/
//                        activeThread.incrementAndGet();
//                        Entry methodB = SphU.entry("methodB");
//                        TimeUnit.MILLISECONDS.sleep(methodBRunningTime);
//                        methodB.exit();
//                        pass.addAndGet(1);
//                        /*MethodA业务逻辑 - 结束*/
//                    } catch (BlockException e1) {
//                        block.incrementAndGet();
//                    } catch (Exception e2) {
//                        // biz exception
//                    } finally {
//                        total.incrementAndGet();
//                        if (methodA != null) {
//                            methodA.exit();
//                            activeThread.decrementAndGet();
//                        }
//                    }
//                }
//            });
//            entryThread.setName("working thread");
//            entryThread.start();
//        }
    }

    private static void initFlowRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("methodA");
        // set limit concurrent thread for 'methodA' to 20
        rule1.setCount(20);
        rule1.setGrade(RuleConstant.FLOW_GRADE_THREAD);//限流阈值类型为并发线程数
        rule1.setLimitApp("default");

        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    private static void tick() {
        Thread timer = new Thread(new TimerTask());
        timer.setName("sentinel-timer-task");
        timer.start();
    }

    static class TimerTask implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            System.out.println("begin to statistic!!!");

            long oldTotal = 0;
            long oldPass = 0;
            long oldBlock = 0;

            while (!stop) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                long globalTotal = total.get();
                long oneSecondTotal = globalTotal - oldTotal;
                oldTotal = globalTotal;

                long globalPass = pass.get();
                long oneSecondPass = globalPass - oldPass;
                oldPass = globalPass;

                long globalBlock = block.get();
                long oneSecondBlock = globalBlock - oldBlock;
                oldBlock = globalBlock;

                System.out.println(seconds + " total qps is: " + oneSecondTotal);
                System.out.println(TimeUtil.currentTimeMillis() + ", total:" + oneSecondTotal
                        + ", pass:" + oneSecondPass
                        + ", block:" + oneSecondBlock
                        + " activeThread:" + activeThread.get());
                if (seconds-- <= 0) {
                    stop = true;
                }
                if (seconds == 40) {
                    System.out.println("method B is running much faster; more requests are allowed to pass");
                    methodBRunningTime = 20;
                }
            }

            long cost = System.currentTimeMillis() - start;
            System.out.println("time cost: " + cost + " ms");
            System.out.println("total:" + total.get() + ", pass:" + pass.get()
                    + ", block:" + block.get());
            System.exit(0);
        }
    }
}