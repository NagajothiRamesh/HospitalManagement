# Hospital Management System

## Overview

The Hospital Management System (HMS) is a Java-based application designed to manage various aspects of hospital operations. This application includes features such as managing patient records, doctor schedules, appointments, and billing. The GUI is built using Java Swing, providing an intuitive interface for users to interact with the system.

## Features

- **Patient Management:** Add, update, and view patient records.
- **Doctor Scheduling:** Manage doctor schedules and availability.
- **Appointments:** Schedule and manage patient appointments.
- **Billing:** Handle billing and financial transactions.

## Installation

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **Integrated Development Environment (IDE):** IntelliJ IDEA or Eclipse
- **MySQL Database:** Ensure MySQL is installed and running
- **JDBC Driver:** MySQL Connector/J for connecting Java applications to MySQL

### Steps

1. **Clone the Repository:**

Open a terminal and clone the repository using:

      git clone https://github.com/NagajothiRamesh/HospitalManagement.git

2.***Navigate to the Project Directory:***
                   
     cd HospitalManagement

3.**Set Up MySQL Database:**

  Create a new database in MySQL for the application:

     CREATE DATABASE hospital_management;

Import the database schema if provided (usually a .sql file) using:

     mysql -u your_username -p hospital_management < path_to_schema_file.sql

4.***Configure Database Connection:***

   Open the dbconfig.properties (or similar configuration file) and update the database connection details:

      db.url=jdbc:mysql://localhost:3306/hospital_management
      db.username=your_username
      db.password=your_password

5.***Install JDBC Driver:***

   Download MySQL Connector/J from MySQL's website.

   Add the JDBC driver JAR file to your IDE's build path or include it in your project's lib directory.

3 ***.Open the Project:***

   Open the project in your preferred IDE:

   IntelliJ IDEA: Open IntelliJ IDEA and select "Open" to navigate to the project directory.
    
   Eclipse: Open Eclipse, select "File" > "Import" > "Existing Projects into Workspace," and choose the project directory.

4. ***Build and Run:***

    IntelliJ IDEA: Right-click on the src directory and select "Run 'HospitalManagementMain'" to build and run the project.

5.***Usage***
  
   Launching the Application:

   Run the main class HospitalManagementMain.java to start the application.

6 .***Navigating the GUI:***

  **Patient Management:** Use the interface to add, view, or update patient records.
  
  ![patients](https://github.com/user-attachments/assets/6ab9fe3d-6ebb-4eb8-b93f-b38f527ffde6)


  **Doctor Scheduling:** Manage doctor schedules from the scheduling module.

  ![doctors](https://github.com/user-attachments/assets/d1b82786-8850-4157-835e-917345dd742e)

  *Appointments:** Schedule and manage appointments via the appointment module.

  ![billing](https://github.com/user-attachments/assets/6c16bb93-e81a-4926-96bc-c10179f03d48)
  

  **Billing: Process billing and view financial transactions from the billing module.

  ![appointments](https://github.com/user-attachments/assets/c15b85bb-66ab-430a-b5e7-ef1d919e8901)


Contact
For questions or feedback, please contact:

    Name: Nagajothi

    Email: danamjothi1135626@gmail.com

    GitHub: NagajothiRamesh

    linkedin: www.linkedin.com/in/n4g4j0th1

Thank you for using the Hospital Management System. We hope this tool enhances your hospital management experience!
