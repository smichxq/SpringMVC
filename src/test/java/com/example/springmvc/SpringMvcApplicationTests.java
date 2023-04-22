package com.example.springmvc;


import com.example.springmvc.Prac.Hello;
import com.example.springmvc.util.CommonUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringMvcApplication.class)
class SpringMvcApplicationTests implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void r(){

        Hello hello = (Hello) applicationContext.getBean(Hello.class);
        hello.aMethod();
    }

    @Test
    public void rr() {
        System.out.println(CommonUtil.isNum("saff"));
        System.out.println(CommonUtil.isNum("35"));
        System.out.println(CommonUtil.isNum("45gb"));

    }




}
