package beans;

import model.Employee;

import java.util.concurrent.ThreadLocalRandom;

public class CallThread implements Runnable {

    private Employee assingEmployee;
    @Override

    public void run() {

       // if (MainController.employeeAvailability())
            processCall();

    }

    public CallThread(Employee assingEmployee) {
        this.setAssingEmployee(assingEmployee);
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
            System.out.println("End Process: " + employeeAssigned.getName_employee() + " " + Thread.currentThread().getName());
            Dispacher.enableEmployee(employeeAssigned);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Employee getAssingEmployee() {
        return assingEmployee;
    }

    public void setAssingEmployee(Employee assingEmployee) {
        this.assingEmployee = assingEmployee;
    }
}
