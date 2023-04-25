package com.example.springmvc.demo;

public class ThreadDemo {
    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        new Thread(threadA).start();
        new Thread(threadA).start();
        new Thread(threadA).start();
        new Thread(threadA).start();
        new Thread(threadA).start();
        new Thread(threadA).start();
        System.out.println(ThreadA.count);
        System.out.println(ThreadA.num);
    }

}

class ThreadA implements Runnable{
    public static int count;
    public static int num;

    public ThreadA() {
        count++;
    }
    @Override
    public void run() {
        num++;
//        Thread.currentThread().setName("ThreadA");
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ThreadB implements Runnable {
    @Override
    public void run() {
//        Thread.currentThread().setName("ThreadB");
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


