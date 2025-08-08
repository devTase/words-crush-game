# 🎮 Words Crush Game - Demo Mode

## ✨ Demo Features

This demo version runs **without requiring MySQL database setup**, using an in-memory database with pre-configured users.

### 🚀 Quick Start

#### Option 1: Run Demo Script
```bash
./run-demo.sh
```

#### Option 2: Manual Commands

**Start Demo Server:**
```bash
mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.DemoProgram"
```

**Start Client GUI:** (in another terminal)
```bash
mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client"
```

### 👥 Demo Users

The demo comes with pre-configured users:

| Username | Password | Role   | Description        |
|----------|----------|--------|--------------------|
| `admin`  | `admin`  | `ROOT` | Administrator user |
| `player` | `player` | `PLAYER` | Regular player    |

### 🎯 Demo Capabilities

✅ **Network Server** - Full TCP server functionality  
✅ **Client-Server Communication** - Real networking  
✅ **User Authentication** - Working login system  
✅ **In-Memory Database** - No MySQL required  
✅ **Dependency Injection** - Google Guice working  
✅ **Multi-client Support** - Multiple players can connect  
✅ **GUI Client** - Swing-based graphical interface  

### 🌐 Connection Details

- **Server Address:** `127.0.0.1` (localhost)
- **Port:** `8001`
- **Max Clients:** `2` (configurable in application-dev.properties)

### 🔧 Technical Details

- **Java Version:** 21
- **Frameworks:** Google Guice 7.0.0, Jakarta Inject
- **Database:** In-memory HashMap (for demo)
- **Networking:** Java NIO ServerSocket
- **GUI:** Java Swing
- **Build Tool:** Maven

### 🏗️ Project Structure

```
src/main/java/
├── org.academiadecodigo.wordsgame.application/
│   ├── Program.java           # Production version (needs MySQL)
│   ├── DemoProgram.java       # Demo version (in-memory DB)
│   └── client/Client.java     # GUI client
├── org.academiadecodigo.wordsgame.config/
│   ├── GameModule.java        # Production DI module
│   ├── DemoGameModule.java    # Demo DI module
│   ├── DemoDatabase.java      # In-memory database
│   ├── DemoUserService.java   # Demo user service
│   └── DemoUserAuthenticator.java # Demo auth service
└── ...
```

### 📋 Testing

All tests pass (22/22):
```bash
mvn test
```

### 🎮 How to Play

1. Start the demo server
2. Connect clients using the GUI
3. Login with demo credentials:
   - Use `admin`/`admin` for administrator access
   - Use `player`/`player` for regular player access
4. Enjoy the multiplayer word game!

### 🛠️ Development Notes

- The demo uses an in-memory database for simplicity
- Real production version requires MySQL setup
- All core functionality is working and testable
- Perfect for development and testing purposes

---

### 🚨 Production Setup

For production use with real MySQL database:

1. Install and configure MySQL
2. Run database setup scripts
3. Use the production `Program.java` main class
4. Configure database connection in properties files

---

**🎉 Enjoy the Words Crush Game Demo!**
