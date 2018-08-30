package beans;

import model.Employee;

import java.util.concurrent.ThreadLocalRandom;

public class CallThread implements Runnable {

    @Override
    public void run() {

            processCall();
    }

    public CallThread() {
    }

    //TIME TO PROCESS A CALL BETWEEN 5 aAND 10 SECONDS
    public void processCall() {
        try {

            Employee employeeAssigned = Dispacher.findAvailableEmployee();

            ThreadLocalRandom time_generator = ThreadLocalRandom.current();
            //LOG Thread
            System.out.println("Start Process: " + employeeAssigned.getName_employee() + " " + Thread.currentThread().getName());
            Thread.sleep(time_generator.nextInt(5, 10) * 1000);
            System.out.println("End Process: " + employeeAssigned.getName_employee() + " " + Thread.currentThread().getName() +" " +Thread.currentThread().getId());
            Dispacher.enableEmployee(employeeAssigned);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
