# SIMPLE-FITURE-PLUGIN

A Minecraft server plugin built with Java and Maven.

---

## 📦 Build Instructions

This project uses **Apache Maven** to build the plugin.

### Requirements
- Java JDK 8 or newer
- Apache Maven

You can verify your setup with:
```bash

java -version
mvn -version

Build the Plugin
Run the following command in the project root directory:
Copy code
Bash
mvn clean package
After a successful build, the compiled plugin JAR will be generated in:
Copy code

target/
Example:
Copy code

target/SIMPLEFITURE-1.2+BETA.jar
⚠️ Note: The target/ directory contains build output and is not part of the source code.
📂 Project Structure
Copy code

src/
 └── main/
     ├── java/        # Java source code
     └── resources/   # plugin.yml, config.yml
pom.xml               # Maven build configuration
⬇️ Download Source Code
Option 1: Clone with Git
Copy code
Bash
git clone https://github.com/Sahur01-arch/SIMPLE-FITURE-PLUGIN.git
cd SIMPLE-FITURE-PLUGIN
Option 2: Download ZIP
Open the GitHub repository page
Click Code
Select Download ZIP
Extract the archive and open the folder
🧾 Notes
This repository contains source code only
Compiled .jar files are provided through official releases or distribution platforms
The plugin can be built locally using the instructions above
📜 License
Specify your license here (if applicable).
