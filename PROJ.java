import java.util.Scanner;

interface GradingOperations 
{
    void addStudent(int studentID, String name);
    void addGrade(int studentID, double[] grades);
    void viewGrades(int studentID);
    double calculateAverage(int studentID);
}

abstract class Student implements GradingOperations 
{
    int studentID;
    String name;
    double[] grades = new double[5];

    public void addStudent(int studentID, String name) 
    {
        this.studentID = studentID;
        this.name = name;
    }

    public int getStudentID() 
    {
        return studentID;
    }

    public String getName() 
    {
        return name;
    }

    public void addGrades(double[] grades) 
    {
        this.grades = grades;
    }

    public double[] getGrades() 
    {
        return grades;
    }

    public double calculateAverage() 
    {
        double total = 0.0;
        for (double grade: grades) 
        {
            total += grade;
        }
        return total / grades.length;
    }
}

class GradingSystem extends Student 
{
    public static final int max_stu = 100;
    public static GradingSystem[] students = new GradingSystem[max_stu];
    public static int studentCount = 0;

    public void addStudent(int studentID, String name) 
    {
        if (studentCount < max_stu) 
        {
            GradingSystem newStudent = new GradingSystem();
            newStudent.studentID = studentID;
            newStudent.name = name;
            students[studentCount++] = newStudent;
        } 
        else 
        {
            System.out.println("Error : Student Limit Exceeded.");
        }
    }

    public void addGrade(int studentID, double[] grades) 
    {
        for (int i = 0; i < studentCount; i++) 
        {
            if (students[i].studentID == studentID) 
            {
                students[i].addGrades(grades);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void viewGrades(int studentID) 
    {
        for (int i = 0; i < studentCount; i++) 
        {
            if (students[i].studentID == studentID) 
            {
                System.out.println("Grades for " + students[i].getName() + " are : ");
                for (double grade : students[i].getGrades()) 
                {
                    System.out.print(grade + " ");
                }
                System.out.println();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public double calculateAverage(int studentID) 
    {
        for (int i = 0; i < studentCount; i++) 
        {
            if (students[i].studentID == studentID) 
            {
                return students[i].calculateAverage();
            }
        }
        System.out.println("Student not found.");
        return 0;
    }
}

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        GradingSystem gradingSystem = new GradingSystem();
        int choice;

        do {
            System.out.println("\n\tStudent Registration System Menu:\n");
            System.out.println("\t1. Add Student (Maximum 100)");
            System.out.println("\t2. Add Grade (up to 5 subjects)");
            System.out.println("\t3. View Grades");
            System.out.println("\t4. Calculate Average");
            System.out.println("\t5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int studentID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    gradingSystem.addStudent(studentID, name);
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    double[] grades = new double[5];
                    System.out.println("Enter Grades for 5 subjects:");
                    for (int i = 0; i < 5; i++) {
                        System.out.print("Grade " + (i + 1) + ": ");
                        grades[i] = scanner.nextDouble();
                    }
                    gradingSystem.addGrade(studentID, grades);
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    gradingSystem.viewGrades(studentID);
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    double average = gradingSystem.calculateAverage(studentID);
                    if (average > 0) {
                        System.out.println("Average for Student ID " + studentID + ": " + average);
                    }
                    break;

                case 5:
                    System.out.println("Bye....");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }
}
