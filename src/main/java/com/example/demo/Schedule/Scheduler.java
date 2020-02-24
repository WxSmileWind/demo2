package com.example.demo.Schedule;

import com.example.demo.DemoApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Scheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    protected static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    //每隔2秒执行一次
    @Scheduled(fixedRate = 2000)
    public void testTasks() {

//        logger.info("定时任务执行时间："+ dateFormat.format(new Date()));
//        logger.error("定时任务执行时间："+ dateFormat.format(new Date()));
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
        //logger.info("进行查询实体 ID为"+id);
     //   System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));

    }

//    //每天3：05执行
//    @Scheduled(cron = "0 05 03 ? * *")
//    public void testTasks() {
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
//    }
}
