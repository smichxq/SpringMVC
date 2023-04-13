package com.example.springmvc.Prac;

import com.example.springmvc.demo.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/prac")
public class Hello {
    @Autowired
    private A a;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello";
    }

    @RequestMapping(path = "/value", method = RequestMethod.GET)
    public void getReqValues(@RequestParam(value = "str", required = false, defaultValue = "sfg") String str,
                                 @RequestParam(value = "num" ,required = false, defaultValue = "1") int num) {
        System.out.println("-------------------");
        System.out.println(str + num);
        System.out.println(a.getStr());
        System.out.println("-------------------");
    }

    @RequestMapping(path = "/values/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getId(@PathVariable("id") int id){
//        System.out.println();
        return "id:" + id;
    }


    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public String getSignup(String name, String age){

        return name + ":" + age;
    }

    @RequestMapping(path = "/getcatty", method = RequestMethod.GET)
    public ModelAndView sendPerson() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name","cat");
        modelAndView.addObject("age","1岁");
        modelAndView.setViewName("demo/catty");
        return modelAndView;
    }

    @RequestMapping(path = "/getcatty1", method = RequestMethod.GET)
    public String sendCatty(Model model) {

        model.addAttribute("name","cat");
        model.addAttribute("age","1岁");
        return "demo/catty";
    }

    @RequestMapping(path = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> getJson(){
        Map<String,String> map = new HashMap();
        map.put("name:","catty1");
        map.put("name1:","catty2");
        return map;
    }
    public void aMethod() {
        System.out.println(a.getStr());
    }


}
