import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int g : grades) sum += g;
        return (double) sum / grades.size();
    }

    int getHighest() {
        int max = grades.isEmpty() ? 0 : grades.get(0);
        for (int g : grades) if (g > max) max = g;
        return max;
    }

    int getLowest() {
        int min = grades.isEmpty() ? 0 : grades.get(0);
        for (int g : grades) if (g < min) min = g;
        return min;
    }

    String getLetterGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }
}

public class StudentGradeTrackerArrayList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("How many students do you want to add? ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        // Add students and their grades
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name of student " + (i + 1) + ": ");
            String name = sc.nextLine();
            Student s = new Student(name);

            System.out.print("How many grades for " + name + "? ");
            int gradeCount = sc.nextInt();
            for (int j = 0; j < gradeCount; j++) {
                System.out.print("Grade " + (j + 1) + ": ");
                int grade = sc.nextInt();
                s.grades.add(grade);
            }
            sc.nextLine(); // consume newline
            students.add(s);
            System.out.println();
        }

        // Display summary
        System.out.println("\n--- Summary Report ---");
        System.out.printf("%-15s %-10s %-10s %-10s %-10s\n", "Name", "Average", "Highest", "Lowest", "Letter");
        for (Student s : students) {
            System.out.printf("%-15s %-10.2f %-10d %-10d %-10s\n",
                    s.name, s.getAverage(), s.getHighest(), s.getLowest(), s.getLetterGrade());
        }

        sc.close();
    }
}