
# Student Management System

A simple console-based Java application for managing student records. This system allows users to perform CRUD (Create, Read, Update, Delete) operations on student data, with role-based access for Admin and Viewer. Data persistence is achieved through serialization, and export options are provided via CSV.

## Features

- **Login Authentication**
  - Admin (`admin` / `admin123`)
  - Viewer (`user` / `viewer123`)
- **Admin Capabilities**
  - Add new students
  - Update student information
  - Delete student records
- **Common Features (Admin & Viewer)**
  - View all students
  - Search students by ID or Name
  - Sort students by Name or Age
  - View summary dashboard (average age, course-wise student count)
  - Export student list to CSV (`Student.csv`)

## Data Storage

- Student data is saved to a local file using Java Serialization (`students.ser`).
- On startup, the application attempts to load existing data from this file.

## How to Run

1. Compile the Java file:
   ```bash
   javac Student.java
   ```

2. Run the program:
   ```bash
   java Student
   ```

3. Login using:
   - Admin credentials: `admin` / `admin123`
   - Viewer credentials: `user` / `viewer123`

## File Structure

- `Student.java` - Main class containing all logic
- `students.ser` - Serialized data file generated during runtime
- `Student.csv` - Exported CSV file containing student data

## Functional Overview

| Option | Function                        | Role     |
|--------|----------------------------------|----------|
| 1      | Add Student                      | Admin    |
| 2      | View All Students                | Admin/Viewer |
| 3      | Search Student by ID            | Admin/Viewer |
| 4      | Update Student                  | Admin    |
| 5      | Delete Student                  | Admin    |
| 6      | Search Student by Name          | Admin/Viewer |
| 7      | Sort Students (Name or Age)     | Admin/Viewer |
| 8      | Show Summary Dashboard          | Admin/Viewer |
| 9      | Export to CSV                   | Admin/Viewer |
| 0      | Exit                             | All      |

## Example CSV Output

```csv
id, name, age, course
1, Alice, 21, Computer Science
2, Bob, 22, Mechanical Engineering
...
```

## Author

Developed by [Your Name]. Feel free to modify and expand the project as needed.
