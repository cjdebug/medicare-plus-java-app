# MediCare Plus - Java Swing Application

MediCare Plus is a Java Swing desktop application developed as a university Object-Oriented Programming project. The application is based on a healthcare management scenario where MediCare Plus manages patient appointments, doctors’ schedules, and medical records across multiple branches.

The purpose of this project is to provide an automated patient management system to reduce manual work, avoid double bookings, improve appointment handling, and make patient and doctor information easier to manage.

## Project Overview

In the given scenario, MediCare Plus currently manages its operations manually using appointment books and phone calls. This can lead to double bookings, patient dissatisfaction, and delays in accessing medical records.

This Java Swing application was developed to automate important healthcare management tasks such as managing patients, managing doctors, scheduling appointments, tracking appointment statuses, assigning doctors to patients, and generating monthly reports.

The project uses Java and Java Swing to create a desktop-based graphical user interface, with object-oriented programming concepts used to structure the main parts of the system.

## Project Category

University Coursework Project  
Module: Object-Oriented Programming  
Project Type: Java Swing Desktop Application

## Features

### Patient Management

- Add new patient records
- Update existing patient details
- Remove patient records
- Store patient personal details
- Store contact information
- Store medical history

### Doctor Management

- Add new doctor records
- Update existing doctor details
- Remove doctor records
- Store doctor specialties
- Store available time slots
- Store working schedules

### Appointment Scheduling

- Book appointments for patients
- Schedule appointments with doctors based on availability
- Store appointment details
- Provide appointment confirmation within the system

### Appointment Status Tracking

- Track and update appointment status
- Supported statuses include:
  - Scheduled
  - Completed
  - Canceled
  - Delayed

### Doctor Assignment

- Assign doctors to patients
- Allocate doctors based on:
  - Specialty
  - Availability
  - Urgency

### Monthly Reports

- Generate monthly appointment summaries
- View appointment volume details
- View doctor performance summaries
- View patient visit summaries

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming Concepts

## OOP Concepts Applied

This project applies several Object-Oriented Programming concepts, including:

- Classes and Objects
- Encapsulation
- Constructors
- Methods
- ArrayLists for data handling
- GUI development using Java Swing
- Event handling for user interactions

## Project Type

This is a desktop-based Java Swing application. It does not include a web interface, online deployment, or real-time database connection.

## Data Storage

This project uses in-program data handling to manage patient, doctor, and appointment records during application runtime. A permanent database connection was not implemented in this version.

## How to Run the Project

### Option 1: Run using IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Open the project folder.
3. Navigate to the `src` folder.
4. Open `MediCarePlusApp.java`.
5. Click the green Run button.
6. The application window should open.

### Option 2: Run using Command Prompt

Make sure Java is installed on your computer.

Open Command Prompt inside the project folder and run:

```bash
javac src/MediCarePlusApp.java
java -cp src MediCarePlusApp
```

## Project Structure

```text
medicare-plus-java-app/
├── src/
│   └── MediCarePlusApp.java
├── README.md
└── .gitignore
```

## Project Status

This project was completed as a university coursework project. It is not currently being actively maintained, but it remains available to show my early learning experience in Java, Java Swing, and object-oriented application development.

## Possible Improvements

Some possible improvements for this project could include:

- Connecting the application to a database
- Saving patient, doctor, and appointment records permanently
- Adding user login and role-based access
- Improving the user interface design
- Adding stronger form validation
- Improving error handling
- Adding search and filter options
- Exporting monthly reports as PDF or Excel files
- Separating the project into multiple Java files for better structure
- Improving appointment conflict detection

## What I Learned

Through this project, I improved my understanding of:

- Building desktop applications using Java Swing
- Creating and managing classes and objects
- Applying object-oriented programming concepts in a practical scenario
- Handling user input through GUI forms
- Using ArrayLists to manage application data
- Designing a basic healthcare management workflow
- Managing patients, doctors, and appointment-related data
- Testing and improving a Java application during development

## Author

Developed by **Chanuka Jayasundara** as part of a university Object-Oriented Programming project.

## Note

This project was created as a beginner-level academic learning project. AI tools were used as learning and development support for guidance, debugging, and implementation assistance, while I reviewed, tested, and customized the application as part of my learning process.
