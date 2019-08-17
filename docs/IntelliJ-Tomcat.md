
* Download and install Tomcat 7.0.92

* Update tomcat-users.xml

  C:\Users\<username>\apps\apache-tomcat-7.0.92\conf\tomcat-users.xml

  <tomcat-users>
    <role rolename="tomcat"/>
    <user username="tomcat" password="tomcat" roles="tomcat,manager,manager-gui,manager-script"/>
  </tomcat-users>

* Start Tomcat

  Download
  Start

* Add to pom.xml

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

* Create c:\Users\<username>\.m2\settings.xml if not present

  C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.4\plugins\maven\lib\maven3\conf\settings.xml

* Add to c:\Users\<username>\.m2\settings.xml

  <servers>
    <server>
      <id>TomcatServer</id>
      <username>tomcat</username>
      <password>tomcat</password>
    </server>
  </servers>

* Reimport all Maven projects

* Run tomcat7:deploy

  Open View > Tool Windows > Maven
  Run tomcat7:deploy
