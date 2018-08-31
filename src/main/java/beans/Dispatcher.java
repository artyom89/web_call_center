package beans;

import model.Employee;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * This is the main class
 */
@ManagedBean
@ViewScoped
public class Dispatcher implements Serializable {

    RejectedExecutionHandlerImpl rejectionHandler;
    //Get the ThreadFactory implementation to use
    ThreadFactory threadFactory;
    ThreadPoolExecutor executorPool;
    MyMonitorThread monitor;

    public static List<Employee> busySyncEmployeeList;
    public List<Employee> busyEmployeeList;
    public static List<Employee> availableSyncEmployeeList;
    public List<Employee> availableEmployeeList;

    //this type of integer allows the used by many threads concurrently
    public static int countCantCall = 0;
    public static int countCantCallRejected = 0;
    public List<Employee> getAvailableEmployeeList() {
        return availableEmployeeList;
    }

    public List<Employee> getBusyEmployeeList() {
        return busyEmployeeList;
    }

    private ArrayList<Runnable> callThreadArrayList;

    public ThreadPoolExecutor getExecutorPool() {
        return executorPool;
    }

    public void startGeneratingCalls() {

        try {
            callThreadArrayList.clear();
            //countCantCallRejected=0;
            //countCantCall=0;
            ThreadLocalRandom time_generator = ThreadLocalRandom.current();
            int cantCalls =   time_generator.nextInt(5, 20) ;

            System.out.println("This many calls:------------------ " +cantCalls);
            for (int i = 0; i < cantCalls; i++) {

                Runnable newCall = new CallThread( findAvailableEmployee());
                //Runnable newCall = new CallThread();
                callThreadArrayList.add(newCall);
            }

            monitor = new MyMonitorThread(executorPool, 5);
            Thread monitorThread = new Thread(monitor);
            monitorThread.start();

            dispatchCall(callThreadArrayList);

        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
//
    public void startGeneratingCallsP(int cantOfCalls) {

        try {
            callThreadArrayList.clear();
           for (int i = 0; i < cantOfCalls; i++) {

                Runnable newCall = new CallThread( findAvailableEmployee());
               // Runnable newCall = new CallThread();
                callThreadArrayList.add(newCall);
            }

            dispatchCall(callThreadArrayList);

            }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void stopGeneratingCalls(){
        executorPool.shutdown();
        monitor.shutdown();

    }


    public synchronized static Employee findAvailableEmployee() {

       try {
        //Employee selectedEmployee;
        Optional<Employee> selectedEmployeeOptional;
        synchronized (availableSyncEmployeeList) {

            selectedEmployeeOptional = availableSyncEmployeeList.stream().filter(x -> x.getRank().equals("Operator")).findFirst();

            if (selectedEmployeeOptional.isPresent()) {
                busySyncEmployeeList.add(selectedEmployeeOptional.get());
                availableSyncEmployeeList.remove(selectedEmployeeOptional.get());
                return selectedEmployeeOptional.get();
            }
            if (!selectedEmployeeOptional.isPresent()) {
                selectedEmployeeOptional = availableSyncEmployeeList.stream().filter(x -> x.getRank().equals("Supervisor")).findFirst();
                if (selectedEmployeeOptional.isPresent()) {
                    busySyncEmployeeList.add(selectedEmployeeOptional.get());
                    availableSyncEmployeeList.remove(selectedEmployeeOptional.get());
                    return selectedEmployeeOptional.get();
                }
            }
            if (!selectedEmployeeOptional.isPresent()) {
                selectedEmployeeOptional = availableSyncEmployeeList.stream().filter(x -> x.getRank().equals("Director")).findFirst();
                if (selectedEmployeeOptional.isPresent()) {
                    busySyncEmployeeList.add(selectedEmployeeOptional.get());
                    availableSyncEmployeeList.remove(selectedEmployeeOptional.get());
                    return selectedEmployeeOptional.get();
                }

            }

        }}
        catch (Exception e) {
       System.out.println(e.getMessage());
        }
        return null;
    }


    public synchronized static void enableEmployee(Employee employee) {
        try {
            synchronized (busySyncEmployeeList) {
                availableSyncEmployeeList.add(employee);
                busySyncEmployeeList.remove(employee);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public Dispatcher() {
        rejectionHandler = new RejectedExecutionHandlerImpl();
        threadFactory = Executors.defaultThreadFactory();
        executorPool = new ThreadPoolExecutor(10, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), threadFactory, rejectionHandler);
        callThreadArrayList = new ArrayList<>();
        availableSyncEmployeeList = Collections.synchronizedList(new ArrayList<>());
        busySyncEmployeeList = Collections.synchronizedList(new ArrayList<>());

        seedEmployees();

        availableEmployeeList = availableSyncEmployeeList;
        busyEmployeeList = busySyncEmployeeList;

    }


/*
    @PostConstruct
    void init() {

        rejectionHandler = new RejectedExecutionHandlerImpl();
        threadFactory = Executors.defaultThreadFactory();
        executorPool = new ThreadPoolExecutor(10, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), threadFactory, rejectionHandler);
        callThreadArrayList = new ArrayList<>();
        availableSyncEmployeeList = Collections.synchronizedList(new ArrayList<>());
        busySyncEmployeeList = Collections.synchronizedList(new ArrayList<>());
        availableEmployeeList = availableSyncEmployeeList;
        busyEmployeeList = busySyncEmployeeList;
        seedEmployees();

        //startGeneratingCalls();
    }

*/

    //creating the ThreadPoolExecutor
    public void dispatchCall(ArrayList<Runnable> incomingCalls) {
        try {
            for (Runnable incomingCall : incomingCalls) {
                executorPool.execute(incomingCall);

            }
        } catch (Exception e) {
            e.getMessage();
        }

    }


    public void seedEmployees() {

        Employee newEmployee = new Employee();
        newEmployee.setName_employee("Operator1");
        //newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Operator2");
        // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Operator3");
        //newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Operator4");
        //newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Operator5");
        // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Operator6");
        // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Operator7");
        // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Supervisor1");
        // newEmployee.setAvailable(true);
        newEmployee.setRank("Supervisor");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Supervisor2");
        //  newEmployee.setAvailable(true);
        newEmployee.setRank("Supervisor");

        availableSyncEmployeeList.add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Director");
        // newEmployee.setAvailable(true);
        newEmployee.setRank("Director");

        availableSyncEmployeeList.add(newEmployee);

    }
/*
    public synchronized static void enableEmployee(Employee employee) {
        try {
            synchronized (availableSyncEmployeeList) {
                availableSyncEmployeeList.get(availableSyncEmployeeList.indexOf(employee)).setAvailable(true);
                if (employee.getRank().equals("Operator"))
                    cantOperators++;
                if (employee.getRank().equals("Supervisor"))
                    cantSupervisors++;
                if (employee.getRank().equals("Director"))
                    cantDirectors++;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    */
 /*
    public void countEmployees (){

        cantOperators= getAvailableEmployeeList().stream().filter(s-> s.getRank().contains("Operator")).count();
        cantSupervisors= getAvailableEmployeeList().stream().filter(s-> s.getRank().contains("Supervisor")).count();
        cantDirectors= getAvailableEmployeeList().stream().filter(s-> s.getRank().contains("Director")).count();

        /*for (Employee employee: getEmployeeList()){
            if (employee.getRank().equals(""))
                cantOperators ++;
            if (employee.getRank().equals("Supervisor"))
                cantSupervisors++;
            if (employee.getRank().equals("Director"))
                cantDirectors++;
        }*/
    //}

/*
    public synchronized static Employee findAvailableEmployee() {

        Employee selectedEmployee = new Employee();
        synchronized (availableSyncEmployeeList) {
            if (cantOperators != 0) {
                for (Employee employeeO : availableSyncEmployeeList) {

                    if (employeeO.isAvailable() && employeeO.getRank().equals("Operator")) {
                        cantOperators--;
                        employeeO.setAvailable(false);
                        selectedEmployee = employeeO;
                        break;
                    }
                }
            }
            if (cantOperators == 0 && cantSupervisors != 0) {
                for (Employee employeeS : availableSyncEmployeeList) {

                    if (employeeS.isAvailable() && employeeS.getRank().equals("Supervisor")) {
                        cantSupervisors--;
                        employeeS.setAvailable(false);
                        selectedEmployee = employeeS;
                        break;
                    }
                }
            }

            if (cantOperators == 0 && cantSupervisors == 0 && cantDirectors != 0) {
                for (Employee employeeD : availableSyncEmployeeList) {

                    if (employeeD.isAvailable() && employeeD.getRank().equals("Director")) {
                        cantDirectors--;
                        employeeD.setAvailable(false);
                        selectedEmployee = employeeD;
                        break;
                    }
                }
            }
        }
        return selectedEmployee;
    }
*/
}

