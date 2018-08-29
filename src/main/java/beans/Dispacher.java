package beans;

import model.Employee;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@ManagedBean
@SessionScoped
public class Dispacher {

    RejectedExecutionHandlerImpl rejectionHandler;
    //Get the ThreadFactory implementation to use
    ThreadFactory threadFactory;
    ThreadPoolExecutor executorPool;


    //private static List<Employee> availableEmployeeList;
    public static List<Employee> busySyncEmployeeList;

    public static List<Employee> availableSyncEmployeeList;

    //private static long cantOperators = 0;
    // private static long cantSupervisors = 0;
    //private static long cantDirectors = 0;

    private Dispacher dispacher;
    private ArrayList<Runnable> callThreadArrayList;


    public void startGeneratingCalls() {

        for (int i = 0; i < 15; i++) {

            //Runnable newCall = new CallThread( findAvailableEmployee());
            Runnable newCall = new CallThread();
            callThreadArrayList.add(newCall);
        }
        dispatchCall(callThreadArrayList);
    }


    public synchronized static Employee findAvailableEmployee() {
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

        }
        return null;
    }


    public synchronized static void enableEmployee(Employee employee) {
        try {
            synchronized (busySyncEmployeeList) {
                availableSyncEmployeeList.add(employee);
                busySyncEmployeeList.remove(employee);
                availableSyncEmployeeList.get(availableSyncEmployeeList.indexOf(employee)).setAvailable(true);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public Dispacher() {
        rejectionHandler = new RejectedExecutionHandlerImpl();
        threadFactory = Executors.defaultThreadFactory();
        executorPool = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), threadFactory, rejectionHandler);

        availableSyncEmployeeList = Collections.synchronizedList(new ArrayList<Employee>());
        busySyncEmployeeList = Collections.synchronizedList(new ArrayList<Employee>());

        seedEmployees();
        //countEmployees ();
        //dispacher = new Dispacher();
        callThreadArrayList = new ArrayList<>();
        startGeneratingCalls();

    }
    /*
    @PostConstruct
    void init() {

        rejectionHandler = new RejectedExecutionHandlerImpl();
        threadFactory = Executors.defaultThreadFactory();
        executorPool = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), threadFactory, rejectionHandler);

        availableSyncEmployeeList = Collections.synchronizedList(new ArrayList<Employee>());
        busySyncEmployeeList = Collections.synchronizedList(new ArrayList<Employee>());

        seedEmployees();
        //countEmployees ();
        //dispacher = new Dispacher();
        callThreadArrayList = new ArrayList<>();
        startGeneratingCalls();

    }*/


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

