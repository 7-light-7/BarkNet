# BarkNet
## An enterprise application alpha

This project is being redesigned to represent the backend of a theoretical vet web application. Users will be able to login, view their pet's information, schedule appointments, and communicate with the vet. 
The staff of the veternary buisness will be able to update pet information, contact owners, and monitor necessary services.

### Structure
| Category                   | Choice                 |
|----------------------------|------------------------|
| **IDE**                    | Intellij                | 
| **Dependency Management**  | Gradle                 | 
| **Configuration Management** | `application.properties` |
| **Database**               | H2 & Redis                    |

### Features
- **Account Management**: Create, read, update, and delete accounts.
- **Work Order Management**: Link work orders to accounts, manage tasks, and track progress.
- **Session Management**: Use Redis to manage sessions.
- **Web Interface**: Use Thymeleaf and Bootstrap for the front end.

### Prerequisites
- Java 21
- Docker (for running Redis)
- Gradle

### Installation and Setup
1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/TrackerNet.git
   cd TrackerNet
2. **Build the project**
   ```sh
   ./gradlew clean build
   ```
3. **Start the docker image**
   ```sh
   docker-compose -f {"path to docker file"}\docker-compose.yml up -d
   ```
4. **Launch the application**
   ```sh
   ./gradlew bootRun
   ```
   
