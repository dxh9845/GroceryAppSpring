package desbytes.models;


/**
 * Employee object representing a tuple from the Employee table
 */
public class Employee {
    private int user_id;
    private float salary;
    private int work_store_id;

    public Employee(int user_id, float salary, int work_store_id) {
        this.user_id = user_id;
        this.salary = salary;
        this.work_store_id = work_store_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getWork_store_id() {
        return work_store_id;
    }

    public void setWork_store_id(int work_store_id) {
        this.work_store_id = work_store_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "user_id=" + user_id +
                ", salary=" + salary +
                ", work_store_id=" + work_store_id +
                '}';
    }
}
