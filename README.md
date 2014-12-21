Ilves WAR Seed Project
==================

Ilves simplifies Java web site creation. This is seed project to simplify new WAR project setup.

Preconditions
------------

1. Git
2. JDK 7
3. Maven 3

Features
--------

1. Example of navigation, page, localization and theme icon customization.
2. Database configuration examples for HSQL (default) and PostgreSQL.
3. Example page commenting implementation.
4. Example maven jetty setup for development.

Clone
-----

Clone projec to your local workstation from command line with the following command:

git clone https://github.com/bubblecloud/ilves-war-seed.git

Integrated Development Environments
-----------------------------------

Import project to IDE of choice (IntelliJ Idea, Eclipse, NetBeans...) by importing the maven pom.xml.

Building
--------

Build from command line with the following command:

mvn clean install

Executing
---------

Execute from command line with the following command:

mvn jetty:run