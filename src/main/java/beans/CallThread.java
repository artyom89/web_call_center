package beans;

import model.Employee;

import java.util.Random;

/**
 * CallTread is de runnable class who is going to run the call
 */
public class CallThread implements Runnable {

    //Employee employeeAssigned;
    @Override
    public void run() {

        try {
            processCall();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TIME TO PROCESS A CALL BETWEEN 5 aAND 10 SECONDS
    public void processCall() {
        try {


            Employee employeeAssigned = Dispatcher.findAvailableEmployee();

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

}
