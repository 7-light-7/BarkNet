# TrackerNet
## An enterprise application alpha

Here I demonstrate how a Spring Boot framework can be leveraged to set up the logic for a typical service-based business.

### Structure
| Category                   | Choice                 |
|----------------------------|------------------------|
| **IDE**                    | VSCode                 | 
| **Dependency Management**  | Gradle                 | 
| **Configuration Management** | `application.properties` |
| **Database**               | H2                     | 
| **NoSQL**                  | Redis                  | 
| **HTML Template**          | Thymeleaf & Bootstrap  |

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
5. **To access the UI, go to your browser and navigate to:**
   ```sh
   http://localhost:8080/
   ```
6. **Login as default1**
   ```sh
   default1
   ```
   ```sh
   password
   ```
   
