# Hotel Client Android App

Welcome to the **Hotel Client Android App**! This app provides a seamless way for hotel guests to
manage their bookings, view services, and interact with hotel staff using their mobile devices.
Below, you'll find all the essential details about the project, including setup, architecture, and
features.

---

## Features

- **Booking Management**: View, modify, and cancel reservations.
- **User Authentication**: Secure login and signup using email, Google, or social media accounts.
- **Multi-language Support**: Available in English, Spanish, and more.

---

## Tech Stack

### Languages and Frameworks

- **Kotlin**: Primary programming language for the app.
- **Jetpack Compose**: For building modern, declarative UIs.
- **Hilt**: For dependency injection.
- **Retrofit**: For API calls and network requests.
- **Room Database**: For local data persistence.
- **Firebase**: For authentication and real-time notifications.
- **Android SDK**: Includes SharedPreferences or Encrypted SharedPreferences for secure local data
  storage.

### Architecture

This project follows **MVVM (Model-View-ViewModel)** combined with **Clean Architecture**, ensuring
maintainability, scalability, and testability. The architecture layers include:

- **Presentation Layer**: Views and ViewModels, handling UI and user interactions.
- **Domain Layer**: Use cases encapsulating business logic.
- **Data Layer**: Repositories for managing data from local and remote sources.

---

## Prerequisites

Before setting up the project, ensure you have the following installed:

- **Android Studio**: Latest stable version.
- **Java Development Kit (JDK)**: Version 11 or higher.
- **Gradle**: Comes with Android Studio (no manual installation needed).

---

## Setup Guide

1. Clone the repository:
   ```bash
   git clone https://github.com/manuellugodev/HotelClient.git
   ```

2. Open the project in Android Studio.

3. Sync Gradle files to download dependencies.

4. Configure the `google-services.json` file:
    - Obtain this file from your Firebase project.
    - Place it in the `app/` directory.

5. Build and run the app on an emulator or physical device.

---

## API Integration

The app interacts with a backend API built using **Spring Boot**. To set up the backend:

1. Clone the backend repository:
   ```bash
   git clone https://github.com/manuellugodev/HotelManagmentApi.git
   ```

2. Follow the README in the backend project for setup instructions.

3. Ensure the backend is running locally or hosted, and update the `BASE_URL` in the app’s API
   service configuration.

---

## Testing

- **Unit Tests**: Located in the `test/` directory.
- **Instrumented Tests**: Found in the `androidTest/` directory.
- **Mocking Tools**: The project uses **Mockito** for mocking dependencies.

To run tests:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

---

## License

This project is licensed for **personal use only**. Commercial use, redistribution, or inclusion in
paid services is strictly prohibited. By using this software, you agree to the following terms:

```
Copyright (c) 2025 Manuel Lugo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to use
the Software for personal, non-commercial purposes only, subject to the following conditions:

- Commercial use, redistribution, and sublicensing of the Software are prohibited.
- This license must be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

