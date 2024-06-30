
# Albums_Example

Welcome to the **Albums_Example** repository! This Android project demonstrates a comprehensive example of managing and displaying album information using modern Android development practices and clean architecture principles.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

The **Albums_Example** project showcases a detailed implementation of an album management system. It allows users to add, view, and manage albums with a focus on a clean and maintainable codebase using clean architecture principles.

## Features

- View a list of all albums.
- Show album details.
- Three build flavors: dev, testing, and staging.

## Architecture

This project is implemented following clean architecture, which includes:

- **View Layer:** Contains the UI components and is implemented using Kotlin Compose.
- **Presentation Layer:** Handles the presentation logic.
- **Data Layer:** Manages data sources, including local and remote data.
- **Domain Layer:** Contains business logic and use cases.
- **Remote Layer:** Manages network connections using Retrofit.
- **Local Layer:** Manages local database using Realm DB.

### Decoupled Structure

The app is structured in a decoupled way to enhance maintainability and testability. Dependency injection is handled using Hilt.

## Installation

To run this project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/mahmoud-elsadany/Albums_Example.git
   ```
2. Open the project in Android Studio.
3. Build the project to install the required dependencies.
4. Select the desired build flavor (dev, testing, staging) and run the app.

## Usage

Once the app is running, you can start adding, viewing, editing, and deleting albums through the user interface.

## Technologies Used

- **Frontend:**
  - Kotlin Compose for designing the UI
- **Architecture:**
  - Clean Architecture principles
- **Data Storage:**
  - Realm DB for local database
- **Networking:**
  - Retrofit for remote connections
- **Navigation:**
  - NavGraph for navigation between fragments
- **Dependency Injection:**
  - Hilt for dependency injection

## Contributing

Contributions are welcome! If you have any ideas or improvements, feel free to fork the repository and submit a pull request. Please make sure to follow the code of conduct.

## Contact

For any questions or inquiries, please contact:

Mahmoud Elsadany
- GitHub: [mahmoud-elsadany](https://github.com/mahmoud-elsadany)
- porfolio : [mahmoud elsadany portofolio](http://mahmoudelsadany.space/)
