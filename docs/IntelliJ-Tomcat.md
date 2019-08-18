The Intellij Community version does not offer Java application server
integration. This setup still allows to (re)deploy your application
from within the IDE.

## Current Setup
* Download and install Tomcat 7.0.92

* Update tomcat-users.xml

  ```
  C:\Users\<username>\apps\apache-tomcat-7.0.92\conf\tomcat-users.xml
  ```

  ```Xml
  <tomcat-users>
    <role rolename="tomcat"/>
    <user username="tomcat" password="tomcat" roles="tomcat,manager,manager-gui,manager-script"/>
  </tomcat-users>
  ```

* Install and start Tomcat

* Add to pom.xml

  ```Xml
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
          <uriEncoding>UTF-8</uriEncoding>
          <url>http://localhost:8080/manager/text</url>
          <path>/movies</path>
          <server>TomcatServer</server>
          <update>true</update>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ```

* Create c:\Users\<username>\.m2\settings.xml if not present

  ```
  C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.4\plugins\maven\lib\maven3\conf\settings.xml
  ```

* Add to c:\Users\<username>\.m2\settings.xml

  ```Xml
  <servers>
    <server>
      <id>TomcatServer</id>
      <username>tomcat</username>
      <password>tomcat</password>
    </server>
  </servers>
  ```

* Reimport Maven project

* Run tomcat7:deploy

  * Open View > Tool Windows > Maven
  * Run tomcat7:deploy

## Alternatives

* Installing Smart Tomcat plugin https://plugins.jetbrains.com/plugin/9492
* Installing IDEA Jetty Runner plugin https://plugins.jetbrains.com/plugin/7505

