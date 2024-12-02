import java.util.ArrayList;
import java.util.Scanner;

class Student {
    int id;
    String name;
    double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getRank() {
        if (marks < 5.0) return "Fail";
        if (marks < 6.5) return "Medium";
        if (marks < 7.5) return "Good";
        if (marks < 9.0) return "Very Good";
        return "Excellent";
    }
}

public class StudentManager {
    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    public void addStudent(int id, String name, double marks) {
        for (Student s : students) {
            if (s.id == id) {
                System.out.println("ID already exists. Please use a different ID.");
                return;
            }
        }
        students.add(new Student(id, name, marks));
        System.out.println("Students have been added successfully!");
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("There are no students to display.");
            return;
        }
        for (Student s : students) {
            System.out.println("ID: " + s.id + ", Name: " + s.name + ", Mark: " + s.marks + ", Rank: " + s.getRank());
        }
    }

    public void sortStudents() {
        heapSort();
    }

    private void heapSort() {
        int n = students.size();

        // Xây dựng Heap (đưa danh sách về dạng Max-Heap)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(n, i);
        }

        // Trích xuất từng phần tử từ Heap
        for (int i = n - 1; i > 0; i--) {
            swap(0, i); // Đưa phần tử lớn nhất (gốc) xuống cuối danh sách
            heapify(i, 0); // Gọi heapify trên phần còn lại của heap
        }
    }

    private void heapify(int n, int i) {
        int largest = i; // Gốc ban đầu
        int left = 2 * i + 1; // Con trái
        int right = 2 * i + 2; // Con phải

        // Nếu con trái lớn hơn gốc
        if (left < n && students.get(left).marks > students.get(largest).marks) {
            largest = left;
        }

        // Nếu con phải lớn hơn gốc
        if (right < n && students.get(right).marks > students.get(largest).marks) {
            largest = right;
        }

        // Nếu gốc không phải lớn nhất
        if (largest != i) {
            swap(i, largest); // Đổi chỗ gốc và phần tử lớn nhất
            heapify(n, largest); // Đệ quy heapify với nút lớn nhất
        }
    }

    private void swap(int i, int j) {
        Student temp = students.get(i);
        students.set(i, students.get(j));
        students.set(j, temp);
    }


    public void editStudent(int id, String name, double marks) {
        for (Student s : students) {
            if (s.id == id) {
                s.name = name;
                s.marks = marks;
                System.out.println("Student information has been successfully updated!");
                return;
            }
        }
        System.out.println("No student found with ID: " + id);
    }

    public void deleteStudent(int id) {
        if (students.removeIf(s -> s.id == id)) {
            System.out.println("The student has been successfully deleted!");
        } else {
            System.out.println("No student found with ID`: " + id);
        }
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Display Student");
            System.out.println("5. Sort students by grade");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter Student Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter Mark: ");
                        double marks = Double.parseDouble(scanner.nextLine());

                        if (marks < 0 || marks > 10) {
                            System.out.println("The score must be between 0 and 10.");
                            break;
                        }

                        manager.addStudent(id, name, marks);
                        break;

                    case 2:
                        System.out.print("Enter the Student ID to be corrected: ");
                        int editId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter New Name: ");
                        String newName = scanner.nextLine();

                        System.out.print("Enter New Mark: ");
                        double newMarks = Double.parseDouble(scanner.nextLine());

                        if (newMarks < 0 || newMarks > 10) {
                            System.out.println("The new score must be between 0 and 10.");
                            break;
                        }

                        manager.editStudent(editId, newName, newMarks);
                        break;

                    case 3:
                        System.out.print("Enter the Student ID to be deleted: ");
                        int deleteId = Integer.parseInt(scanner.nextLine());
                        manager.deleteStudent(deleteId);
                        break;

                    case 4:
                        System.out.println("Student List:");
                        manager.displayStudents();
                        break;

                    case 5:
                        System.out.println("List of Students Sorted by Marks.");
                        manager.sortStudents();
                        break;

                    case 6:
                        System.out.println("Exit.");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid input. Please re-ente");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please re-enter.");
            }
        }
    }
}