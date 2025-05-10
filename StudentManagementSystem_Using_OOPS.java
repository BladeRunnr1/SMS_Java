
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Student implements Serializable {
    public static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;

    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student [id = " + id + ", name = " + name + ", age = " + age + ", course = " + course + "]";
    }

    static ArrayList<Student> studentList = new ArrayList<>();

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        // if (!login()) {
        // return; // exit program if login fails
        // }
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Student Management System");
        String username = "admin";
        String password = "admin123";

        String viewer = "user";
        String viewerPassword = "viewer123";

        String role = "";

        int attemps = 0;
        while (attemps < 3) {
            System.out.println("Enter your username: ");
            String inputUsername = scanner.nextLine();

            System.out.println("Enter your password");
            String inputPassword = scanner.nextLine();

            if (username.equals(inputUsername) && password.equals(inputPassword)) {
                System.out.println("Logged in as Admin successful!\n");
                role = "admin";
                break;
            } else if (viewer.equals(inputUsername) && password.equals(viewerPassword)) {
                System.out.println("Logged in as User successful!\n");
                role = "viewer";
                break;
            } else {
                attemps++;
                System.out.println("Incorrect credentials. Attempts left: " + (3 - attemps));
            }
        }

        loadFromFile();

        int choice;

        do {

            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("6. Search Student by Name");
            System.out.println("7. Sort Students (Name or Age)");
            System.out.println("8. Show Summary Dashboard");

            if (role.equals("admin")) {
                System.out.println("Logged in");
                System.out.println("1. Add Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("4. Update Student");
            }

            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addStudent(scanner);
                case 2 -> viewStudent();
                case 3 -> searchStudent(scanner);
                case 4 -> updateStudent(scanner);
                case 5 -> deleteStudent(scanner);
                case 6 -> searchByName(scanner);
                case 7 -> sortStudents(scanner);
                case 8 -> showSummary();
                case 9 -> exportToCSV();
                default -> System.out.println("You have seleted option: " + choice);

            }

        } while (choice != 0);

        System.out.println("By By");
        scanner.close();

    }

    // Method to add Student
    public static void addStudent(Scanner scanner) {
        System.out.println("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter course: ");
        String course = scanner.nextLine();

        Student s = new Student(id, name, age, course);
        studentList.add(s);
        saveToFile();
        System.out.println("Student added successfully");
    }

    // Method to View Students
    public static void viewStudent() {
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
        } else {
            System.out.println("List of Students");
            for (Student student : studentList) {
                System.out.println(student);
            }
            System.out.println("Total Students: " + studentList.size());
        }
    }

    // Mehotd to search student
    public static void searchStudent(Scanner scanner) {
        System.out.println("Enter Student ID to search:");
        int id = scanner.nextInt();

        boolean found = false;
        for (Student student : studentList) {
            if (student.getId() == id) {
                System.out.println("Student found");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found");
        }
    }

    // Method to update student
    public static void updateStudent(Scanner scanner) {
        System.out.println("Enter student Id to update");
        int id = scanner.nextInt();
        scanner.nextLine();

        Boolean found = false;
        for (Student s : studentList) {
            if (s.getId() == id) {
                System.out.println("Student found: " + s);
                System.out.println("Enter new name (leave blank to keep current)");
                String name = scanner.nextLine();
                if (!name.isEmpty()) {
                    s.setName(name);
                }

                System.out.println("Enter new age (or press enter to skip)");
                String age = scanner.nextLine();
                if (!age.isEmpty()) {
                    int newAge = Integer.parseInt(age);
                    s.setAge(newAge);
                }

                System.out.println("Enter new course (leave blank to keep current)");
                String course = scanner.nextLine();
                if (!course.isEmpty()) {
                    s.setCourse(course);
                }

                saveToFile();
                System.out.println("Record updated successfully");

                found = true;
                break;
            }

        }
        if (!found) {
            System.out.println("Student not found");
        }
    }

    // Method to delete Student
    public static void deleteStudent(Scanner scanner) {
        System.out.println("Enter Student ID to delete the record: ");
        int id = scanner.nextInt();

        boolean removed = false;
        for (Student s : studentList) {
            if (s.getId() == id) {
                studentList.remove(s);

                saveToFile();
                System.out.println("Student removed successfully");
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("Student not found");
        }
    }

    // Method to save records into a file
    public static void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream("students.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(studentList);
            System.out.println("Student data saved to file");
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    // Method to retreive records into a file
    @SuppressWarnings("unchecked")
    public static void loadFromFile() {
        try (FileInputStream fis = new FileInputStream("students.ser");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            studentList = (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
            studentList = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }

    // Method for Authentication
    public static boolean login(Scanner scanner) {
        String username = "admin";
        String password = "admin123";

        int attemps = 0;
        while (attemps < 3) {
            System.out.println("Enter your username: ");
            String inputUsername = scanner.nextLine();

            System.out.println("Enter your password");
            String inputPassword = scanner.nextLine();

            if (username.equals(inputUsername) && password.equals(inputPassword)) {
                System.out.println("Logged in successful!\n");
                return true;
            } else {
                attemps++;
                System.out.println("Incorrect credentials. Attempts left: " + (3 - attemps));
            }
        }
        return false;
    }

    // Method to search student by name
    public static void searchByName(Scanner scanner) {
        System.out.println("Enter student name to search: ");
        String nameToSearch = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(nameToSearch)) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No student found with that name.");
        }
    }

    // Method to sort the students
    public static void sortStudents(Scanner scanner) {
        if (studentList.isEmpty()) {
            System.out.println("No students to sort");
            return;
        }

        System.out.println("🔽 Sort by:");
        System.out.println("1. Name (A-Z)");
        System.out.println("2. Age (Ascending)");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                studentList.sort(Comparator.comparing(Student::getName));
                System.out.println("Sorted by name");
            }
            case 2 -> {
                studentList.sort(Comparator.comparing(Student::getAge));
                System.out.println("Sorted by name");
            }
            default -> {
                System.out.println("Invalid option");
                return;
            }
        }
        viewStudent();
    }

    // Method to view summary dashboard

    public static void showSummary() {
        if (studentList.isEmpty()) {
            System.out.println("No student found");
        }
        System.out.println("Summary dashboard");

        System.out.println("Total student: " + studentList.size());

        double avgAge = studentList.stream().mapToInt(Student::getAge).average().orElse(0.0);
        System.out.printf("Average Age: %.2f\n", avgAge);

        // Count per course
        Map<String, Long> courseCount = studentList.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));

        System.out.println("Student per course");
        for (Map.Entry<String, Long> entry : courseCount.entrySet()) {
            System.out.println(" - " + entry.getKey() + " : " + entry.getValue());
        }
    }

    // Method to export record as CSV
    public static void exportToCSV() {
        if (studentList.isEmpty()) {
            System.out.println("No student found");
            return;
        }

        String fileName = "Student.csv";
        System.out.println("Size: " + studentList.size());
        try {
            try (PrintWriter pWriter = new PrintWriter(new FileWriter(fileName))) {
                pWriter.println("id, name, age, course");
                for (Student student : studentList) {
                    pWriter.println(
                            student.getId() + "," + student.getName() + "," + student.getAge() + ","
                                    + student.getCourse());
                }
            }
            System.out.println("Students exported successfully to " + fileName);
        } catch (Exception e) {
            System.out.println("Error found - " + e.getMessage());
        }
    }
}