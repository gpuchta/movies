# Notes

## ffmpeg

A complete, cross-platform solution to record, convert and stream
audio and video.

[FFmpeg](https://ffmpeg.org/)

## Handbrake CLI

Go to [HandBrake.fr](https://handbrake.fr/) > Downloads > Command Line Version

# Synology NAS

## Plex Media Server

The Plex Media server ...

## Java 8

Install Java 8 in order to run this software in Tomcat.

## Tomcat 7

Tomcat 7 is at the time of this writing the latest available
packaged version. It has a dependency on Java 7. But we will
replace it with Java 8 as described in the following section.

```
/var/packages/Tomcat7/target/src/webapps/movies
/var/packages/Tomcat7/target/src/webapps/movies.war
````

### Configure to use Java 8

Add `setenv.sh` to the bin folder and set `JRE_HOME` to Java 8.

    > cd /volume1/@appstore/Tomcat7/src/bin
    > sudo vi setenv.sh
    JRE_HOME=/var/packages/Java8/target/j2sdk-image/jre
    > sudo chmod 755 setenv.sh


### Access Log Files

    tail -f \@appstore/Tomcat7/src/logs/localhost.2017-02-03.log
    tail -f \@appstore/Tomcat7/src/logs/catalina.out
    tail -f \@appstore/Tomcat7/src/logs/movies.out

# Code

## Web Service

To build the web application we use jQuery and Bootstrap.

### jQuery and Bootstrap

Use webjars to allow Maven to take care of dependency management. Add the following
entties to the `pom.xml` file.

    <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>jquery</artifactId>
      <version>3.1.1-1</version>
    </dependency>

    <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>bootstrap</artifactId>
      <version>3.3.7</version>
    </dependency>

In your HTML file add the following links to include the given Javasccript and CSS
files. Note the `webjars/` prefix in each path.

    <link rel='stylesheet' href='webjars/bootstrap/3.3.7/css/bootstrap.min.css'>
    <script src="webjars/jquery/3.1.1-1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

As per [WebJars](http://www.webjars.org/documentation#servlet3) documentation for Servlet 3 containers.

> With any Servlet 3 compatible container, the WebJars that are in the `WEB-INF/lib` directory
> are automatically made available as static resources. This works because anything in a
> `META-INF/resources` directory in a JAR in `WEB-INF/lib` is automatically exposed as a static
> resource.

### Bootstrap Theme

Use [Bootswatch](https://bootswatch.com/) to style the application.

Replace original Bootstrap CSS:

```HTML
<link rel='stylesheet' href='webjars/bootstrap/3.3.7/css/bootstrap.min.css'>
```

With the Bootswatch version:

```HTML
<link rel='stylesheet' href='webjars/bootswatch/3.3.7/cyborg/bootstrap.min.css'>
```

## Static Page

### jQuery

We use jQuery v3.1.1 slim which excludes the following:

* -ajax
* -ajax/jsonp
* -ajax/load
* -ajax/parseXML
* -ajax/script
* -ajax/var/location
* -ajax/var/nonce
* -ajax/var/rquery
* -ajax/xhr
* -manipulation/_evalUrl
* -event/ajax
* -effects
* -effects/animatedSelector
* -effects/Tween
* -deprecated

```HTML
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
integrity="sha256-/SIrNqv8h6QGKDuNoLGA4iret+kyesCkHGzVUUV0shc="
crossorigin="anonymous"></script>
```

## CSS

# Markdown

* [Markdown Cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)

# Development Software

* [Brackets Editor](http://brackets.io/)


# Interesting Libraries

* [jcodec](http://jcodec.org/)
* [humble-video](https://github.com/artclarke/humble-video)
