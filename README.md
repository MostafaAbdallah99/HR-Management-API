
<div align="center">
  
  <h1>HR Management System API</h1>

  <p>
    HR Management System API, which provides REST and SOAP APIs. This project aims to streamline the process of employee, project, and department management by offering a comprehensive set of endpoints for adding, removing, and updating various entities within the system.

  </p>

</div>

<br />

<!-- Table of Contents -->
# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
    * [Tech Stack](#space_invader-tech-stack)
    * [Features](#dart-features)
- [Getting Started](#toolbox-getting-started)
    * [Prerequisites](#bangbang-prerequisites)
    * [Run Locally](#running-run-locally)
- [Contact](#handshake-contact)



<!-- About the Project -->
## :star2: About the Project
<p>You will find the REST API documentation <a href="https://documenter.getpostman.com/view/33815313/2sA3BgBvRL">here</a>, you can explore it.</p>
<br>
<p>You will find the SOAP API documentation <a href="https://documenter.getpostman.com/view/33815313/2sA3BgBvVe">here</a>, you can explore it.</p>


<!-- Tech Stack -->
## :space_invader: Tech Stack

<details>
  <summary>🌐 <h3>Tools</h3></summary>
    <a href="https://www.postman.com/">Postman</a><br>  
</details>

<details>
  <summary>💻 <h3>Backend</h3></summary>
    <a href="">Java</a><br>  
    <a href="">Hibernate</a><br> 
    <a href="">HikariCP</a><br> 
    <a href="">JAX-RS</a><br>  
    <a href="">JAX-WS</a><br>  
</details>

<details>
<summary>🛢️ <h3>Database</h3></summary>
    <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"></a><br>  
</details>

<!-- Features -->
## :dart: Features
- Allows adding, updating, and removing employees
- Allows adding, updating, and removing departments
- Enables assigning employees to departments
- Enables employees to take vacations
- HATEOAS
  
<!-- Getting Started -->
## 	:toolbox: Getting Started

<!-- Prerequisites -->
## :bangbang: Prerequisites

:point_right: This project requires Java JDK 17. Make sure you have installed it on your system. You can check by running the following command in your terminal:

```bash
java -version
```

:point_right: This project uses Maven as a build tool and package manager. Make sure you have it installed on your system. You can check by running the following command in your terminal:

```bash
mvn -v
```

:point_right: This project requires Apache Tomcat 10. Make sure you have it installed on your system. You can check by running the following command in your terminal:

```bash
catalina version
```
> [!NOTE]  
> Please note that the `catalina version` command will only work if the `CATALINA_HOME` environment variable is set to the directory where Tomcat is installed, and the `bin` directory of Tomcat is added to the `PATH` environment variable..


<!-- Run Locally -->
## :running: Run Locally

<h4>:one: Clone the project</h4>

```bash
  git clone https://github.com/MostafaAbdallah99/HR-Management-API
```
<h4>:two: Go to the project directory</h4>

```bash
  cd HR-Management-API
```

<h4>3️⃣To deploy the application on Tomcat, first, you need to package your application as a WAR file. Navigate to the project directory and run the following command</h4>

```bash
  mvn clean package
```
<h4>4️⃣This command will create a WAR file in the `target` directory. You can deploy this WAR file on Tomcat by copying it to the `webapps` directory of Tomcat</h4>

```bash
  cp target/HR-Management-API.war $CATALINA_HOME/webapps/
```
<h4>5️⃣To run the application, start the Tomcat server by running the following command</h4>

```bash
  catalina run
```

<h4>6️⃣Explore Endpoints on postman</h4>

<!-- Contact -->
## :handshake: Contact

[![Mostafa Abdallah](https://img.shields.io/badge/Mostafa_Abdallah-Profile-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/mostafa-abdallah-a35130151/)
