package beans;

import model.Employee;

import java.util.Random;

public class CallThread implements Runnable {

    Employee employeeAssigned;
    @Override
    public void run() {

        try {
            processCall();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CallThread( Employee employee) {
        employeeAssigned = employee;
    }

    //TIME TO PROCESS A CALL BETWEEN 5 aAND 10 SECONDS
    public void processCall() {
        try {

           // Employee employeeAssigned = Dispatcher.findAvailableEmployee();

            //ThreadLocalRandom time_generator = ThreadLocalRandom.current();
            //LOG Thread
            System.out.println("Start Process: " + employeeAssigned.getName_employee() + " " + Thread.currentThread().getName());
            Random random = new Random();
            //Thread.sleep(7);
            Thread.sleep((random.nextInt(6) + 5)* 1000);
            System.out.println("End Process: " + employeeAssigned.getName_employee() + " " + Thread.currentThread().getName() +" " +Thread.currentThread().getId());
            Dispatcher.countCantCall++;
            Dispatcher.enableEmployee(employeeAssigned);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    //this is the exact method, removing the log, the test unit crash
    public void processCallT() {
        try {

            //Employee employeeAssigned = Dispatcher.findAvailableEmployee();

            ThreadLocalRandom time_generator = ThreadLocalRandom.current();
            //LOG Thread
            //System.out.println("Start Process: " + employeeAssigned.getName_employee() + " " + Thread.currentThread().getName());
            Thread.sleep(time_generator.nextInt(5, 10) * 1000);
            //System.out.println("End Process: " + employeeAssigned.getName_employee() + " " + Thread.currentThread().getName() +" " +Thread.currentThread().getId());
            Dispatcher.enableEmployee(employeeAssigned);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
