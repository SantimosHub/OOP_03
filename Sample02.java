
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Sample02 {

    private static Random random = new Random();

    /**
     * TODO: 2. generateEmployee должен создавать различных сотрудников (Worker, Freelancer)
     */
    static Employee generateEmployee() {
        String[] names = new String[]{"Анатолий", "Глеб", "Клим", "Мартин", "Лазарь", "Владлен", "Клим", "Панкратий", "Рубен", "Герман"};
        String[] surnames = new String[]{"Григорьев", "Фокин", "Шестаков", "Хохлов", "Шубин", "Бирюков", "Копылов", "Горбунов", "Лыткин", "Соколов"};
        int age = random.nextInt(21, 66);
        if (random.nextBoolean()) {
            double salary = random.nextInt(200, 300);
            int index = random.nextInt(30, 50);
            String status = "Фрилансер";

            return new Worker(names[random.nextInt(10)], surnames[random.nextInt(10)], status, age, salary * index);
        } else {
            double hourRate = random.nextInt(100, 150);
            int workHours = random.nextInt(10, 150);
            String status = "Рабочий";

            return new Freelancer(names[random.nextInt(10)], surnames[random.nextInt(10)], status, age, hourRate * workHours, hourRate, workHours);
        }

    }

    public static void main(String[] args) {

        Employee[] employees = new Employee[10];
        for (int i = 0; i < employees.length; i++)
            employees[i] = generateEmployee();

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        Arrays.sort(employees, new AgeComparator());
//        Arrays.sort(employees);

        System.out.print("\n*** Отсортированный массив сотрудников ***\n\n");

        for (Employee employee : employees) {
            System.out.println(employee);
        }

    }

}

class AgeComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        int res = o1.status.compareTo(o2.status);
        if (res == 0) {
            res = Integer.compare(o2.age, o1.age);
        }
        return res;

    }
}

class SalaryComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {

        //return o1.calculateSalary() == o2.calculateSalary() ? 0 : (o1.calculateSalary() > o2.calculateSalary() ? 1 : -1);
        return Double.compare(o2.calculateSalary(), o1.calculateSalary());
    }
}

class NameComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        //return o1.calculateSalary() == o2.calculateSalary() ? 0 : (o1.calculateSalary() > o2.calculateSalary() ? 1 : -1);
        int res = o1.name.compareTo(o2.name);
        if (res == 0) {
            res = Double.compare(o2.calculateSalary(), o1.calculateSalary());
        }
        return res;
    }
}

abstract class Employee /*implements Comparable<Employee>*/ {

    protected String name;
    protected String surName;
    protected String status;

    protected int age;
    protected double salary;

    public Employee(String name, String surName, String status, int age, double salary) {
        this.name = name;
        this.surName = surName;
        this.status = status;
        this.age = age;
        this.salary = salary;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return String.format("Сотрудник: %s %s; Среднемесячная заработная плата: %.2f", name, surName, salary);
    }


}

class Worker extends Employee {

    public Worker(String name, String surName, String status, int age, double salary) {
        super(name, surName, status, age, salary);
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; %s; возраст %d ; Среднемесячная заработная плата " +
                "(фиксированная месячная оплата): %.2f (руб.)", name, surName, status, age, salary);
    }
}

/**
 * TODO: 1. Доработать самостоятельно в рамках домашней работы
 */
class Freelancer extends Employee {

    protected double hourRate;
    protected Integer workHours;

    public Freelancer(String name, String surName, String status, int age, double salary, double hourRate, int workHours) {
        super(name, surName, status, age, salary);
        this.hourRate = hourRate;
        this.workHours = workHours;
        this.salary = hourRate * workHours;
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; %s; возраст %d ; Почасовая ставка: %.2f (руб.), Месячная оплата: %.2f (руб.)", name, surName, status, age, hourRate, salary);
    }
}