package beans;

import exceptions.RejectCallThreadException;
import model.Employee;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    //Generator of calls + MonitorThread
    public void startGeneratingCalls() {

        try {
            callThreadArrayList.clear();
            //countCantCallRejected=0;
            //countCantCall=0;
            ThreadLocalRandom time_generator = ThreadLocalRandom.current();
            int cantCalls = time_generator.nextInt(5, 20);

            System.out.println("This many calls:------------------ " + cantCalls);
            for (int i = 0; i < cantCalls; i++) {

                // Runnable newCall = new CallThread( findAvailableEmployee());
                Runnable newCall = new CallThread();
                callThreadArrayList.add(newCall);
            }

            monitor = new MyMonitorThread(executorPool, 5);
            Thread monitorThread = new Thread(monitor);
            monitorThread.start();

            dispatchCall(callThreadArrayList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Same generator of calls with cant param for testing mainly
    public void startGeneratingCallsP(int cantOfCalls) {

        try {
            callThreadArrayList.clear();
            for (int i = 0; i < cantOfCalls; i++) {

                // Runnable newCall = new CallThread( findAvailableEmployee());
                Runnable newCall = new CallThread();
                callThreadArrayList.add(newCall);
            }

            dispatchCall(callThreadArrayList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //terminate executorPool
    public void stopGeneratingCalls() {
        executorPool.shutdown();
        monitor.shutdown();

    }

    //find available employees
    public static Employee findAvailableEmployee() throws RejectCallThreadException {


        Employee selectedEmployee;
        //Employee selectedEmployeeOptional;
        synchronized (availableSyncEmployeeList) {


            selectedEmployee = availableSyncEmployeeList.stream().filter(x -> x.getRank().equals("Operator")).findFirst().orElse(null);

            if (selectedEmployee != null) {
                busySyncEmployeeList.add(selectedEmployee);
                availableSyncEmployeeList.remove(selectedEmployee);
                return selectedEmployee;
            }

            selectedEmployee = availableSyncEmployeeList.stream().filter(x -> x.getRank().equals("Supervisor")).findFirst().orElse(null);
            if (selectedEmployee != null) {
                busySyncEmployeeList.add(selectedEmployee);
                availableSyncEmployeeList.remove(selectedEmployee);
                return selectedEmployee;
            }


            selectedEmployee = availableSyncEmployeeList.stream().filter(x -> x.getRank().equals("Director")).findFirst().orElse(null);
            if (selectedEmployee != null) {
                busySyncEmployeeList.add(selectedEmployee);
                availableSyncEmployeeList.remove(selectedEmployee);
                return selectedEmployee;
            }
        }

        if (selectedEmployee == null) {
            //rejectionHandler.rejectedExecution(Thread.currentThread(), executorPool);
            throw new RejectCallThreadException(Thread.currentThread().getName() + " Cannot be process, limit employees");

        }

        return null;
    }

    //Once used, enable employee again
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


    //constructor
    public Dispatcher() {
        rejectionHandler = new RejectedExecutionHandlerImpl();

        threadFactory = Executors.defaultThreadFactory();
        executorPool = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), threadFactory, rejectionHandler);
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

    // seed employees
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
}

