#  Gym Progress Tracker (Full-Stack Backend Project)

##  Key Features

*   **Secure User Authentication:** Implemented via Spring Security with protected session management.
*   **Automatic 1RM Calculation:** Integrated business logic in the Service layer to calculate One-Rep Max using the Epley formula: `Weight * (1 + Reps / 30)`[cite: 1].
*   **Relational Data Management:** Full CRUD operations backed by a **PostgreSQL** database[cite: 1].
*   **Personalized Experience:** Data isolation ensures users only see and manage their own training records[cite: 1].
*   **Dynamic Filtering:** Ability to sort exercises by status (**Planned** or **Completed**) for better workout organization[cite: 1].

##  Technical Stack

*   **Language:** Java 17+[cite: 1]
*   **Framework:** Spring Boot (MVC, Security, Data JPA)[cite: 1]
*   **Database:** PostgreSQL[cite: 1]
*   **Template Engine:** Thymeleaf[cite: 1]
*   **Build Tool:** Maven[cite: 1]
*   **Architecture:** Clean Layered Architecture (Controller -> Service -> Repository -> Entity)[cite: 1]

![App_Screenshot](src/main/resources/static.css/images/Снимок экрана 2026-04-30 203104.png)
