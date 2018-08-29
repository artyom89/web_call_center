package beans;

public class MainController {


    /*
    public void seedEmployees () {

        Employee newEmployee = new Employee();
        newEmployee.setName_employee("Victor");
        //newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Gabriel");
       // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Andres");
        //newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Hilda");
        //newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Claudia");
       // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Elizabeth");
       // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Jane");
       // newEmployee.setAvailable(true);
        newEmployee.setRank("Operator");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Antonio");
       // newEmployee.setAvailable(true);
        newEmployee.setRank("Supervisor");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Jose");
      //  newEmployee.setAvailable(true);
        newEmployee.setRank("Supervisor");

        getEmployeeList().add(newEmployee);

        newEmployee = new Employee();
        newEmployee.setName_employee("Domingo");
       // newEmployee.setAvailable(true);
        newEmployee.setRank("Director");

        getEmployeeList().add(newEmployee);

    }
*/

      /*
    public static Employee findAvailableEmployee() {
        boolean findEmployee = false;
        int count = 0;
        Employee selectedEmployee = new Employee();
        if (cantOperators != 0) {
            while (!findEmployee || count < getEmployeeList().size()) {

                if (getEmployeeList().get(count).isAvailable() && getEmployeeList().get(count).getRank().equals("Operator")) {
                    findEmployee = true;
                    selectedEmployee = getEmployeeList().get(count);
                    getEmployeeList().get(count).setAvailable(false);
                    cantOperators--;

                } else
                    count++;
            }
        }
        if (cantOperators == 0 && cantSupervisors!=0) {
            while (findEmployee || count < getEmployeeList().size()) {

                if (getEmployeeList().get(count).isAvailable() && getEmployeeList().get(count).getRank().equals("Supervisor")) {
                    findEmployee = true;
                    selectedEmployee = getEmployeeList().get(count);
                    getEmployeeList().get(count).setAvailable(false);
                    cantSupervisors--;

                } else
                    count++;
            }
        }

        if (cantOperators == 0 && cantSupervisors==0 && cantDirectors!=0) {
            while (findEmployee || count < getEmployeeList().size()) {

                if (getEmployeeList().get(count).isAvailable() && getEmployeeList().get(count).getRank().equals("Director")) {
                    findEmployee = true;
                    selectedEmployee = getEmployeeList().get(count);
                    getEmployeeList().get(count).setAvailable(false);
                    cantDirectors--;
                } else
                    count++;
            }
        }

        return selectedEmployee;
    }*/

    /*
    public static Employee findEmployeeL () {
        Employee employeeFind = new Employee();
        if (cantOperators==0)
           Optional employeeFind= getEmployeeList().stream().filter(employee -> employee.getRank().equals("Operators")).findFirst();
        if (cantOperators==0 && cantSupervisors!=0)
            Optional  employeeFind= getEmployeeList().stream().filter(employee -> employee.getRank().equals("Operators")).findFirst();
        if (cantOperators==0 && cantSupervisors==0 && cantDirectors=!0)
            Optional  employeeFind= getEmployeeList().stream().filter(employee -> employee.getRank().equals("Operators")).findFirst();

        return employeeFind;
    }*/

}
