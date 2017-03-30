# Embedded MySQL Gradle Plugin

[![Linux Build Status](https://travis-ci.org/michaelruocco/embedded-mysql-plugin.svg?branch=master)](https://travis-ci.org/michaelruocco/embedded-mysql-plugin)
[![Windows Build status](https://ci.appveyor.com/api/projects/status/ewbb2vr04e89sadf?svg=true)](https://ci.appveyor.com/project/michaelruocco/embedded-mysql-plugin)
[![Coverage Status](https://coveralls.io/repos/michaelruocco/embedded-mysql-plugin/badge.svg?branch=master&service=github)](https://coveralls.io/github/michaelruocco/embedded-mysql-plugin?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.michaelruocco/embedded-mysql-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.michaelruocco/embedded-mysql-plugin)

This plugin is built around the [embedded mysql project](https://github.com/wix/wix-embedded-mysql).
It allows you to easily start up a MySQL process withouth having to install it by hand. Currently
the plugin only exposes the ability to be able to set the port the process is running on and a 
username a password to use when connecting. There are various other configuration options offered
in the [embedded mysql project](https://github.com/wix/wix-embedded-mysql) that could be exposed with
further development of the plugin if / when required.

## Usage

To use the plugin's functionality, you will need to add the its binary artifact to your build script's
classpath and apply the plugin.

### Adding the plugin binary to the build

The plugin JAR needs to be defined in the classpath of your build script. It is directly available on
[Maven Central](http://search.maven.org/).
The following code snippet shows an example on how to retrieve it from Bintray:

```
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.michaelruocco:embedded-mysql-plugin:2.1.5'
    }
}
```

### Applying the plugin

To use the plugin, include the following code snippet in your build script:

```
apply plugin: 'embedded-mysql'
```

This will add two new tasks to your gradle build:

* startEmbeddedMysql
* stopEmbeddedMysql

### Configuring your embedded MySQL database

It should be fairly obvious what these two tasks do, before you can use them you will need to
configure how you want your mysql to run, you do this by providing properties to the EmbeddedMysqlExtension
as shown below.

```
embeddedMysql {
    url = 'jdbc:mysql://localhost:3306/databaseName'
    username = 'user'
    password = ''
    version = 'v5_7_latest'
}
```

Once you have set these properties they will be used to spin up your database so you can connect to
it as you would any other mysql database.

### Setting server variables and charsets

It is also possible to set the charset, collate and server variables against the database configuration
thanks to work done by [kiakimov](https://github.com/kiakimov). An example configuration is shown below:

```
embeddedMysql {
    url = 'jdbc:mysql://localhost:3306/databaseName'
    username = 'user'
    password = ''
    version  = 'v5_7_latest'
    serverCharset = 'utf8'
    serverCollate = 'utf8_general_ci'
    serverVars = ['explicit_defaults_for_timestamp' : true]
}
```

#### Default configuration

The default values for each of the underlying properties are:

* databaseName = '' (empty string, process will not work if a databaseName is not provided)
* port = 3306 (default MySQL port)
* username = 'user'
* password = '' (empty string, this means no password is required to access your embedded MySQL database)
* version = 'v5_7_latest' (default version of MySQL used by this plugin 5.6.22)

This means the example configuration above could also be expressed as shown below.

```
embeddedMysql {
    url = 'jdbc:mysql://localhost:3306/databaseName'
}
```

#### Available versions

When selecting a version of MySQL to run you have the following versions currently available:

* v5_5_40
* v5_6_21
* v5_6_22
* v5_6_23
* v5_6_24
* v5_6_latest
* v5_7_10
* v5_7_13
* v5_7_latest

These available versions are exposed by the [embedded mysql project](https://github.com/wix/wix-embedded-mysql)

### Connecting to your embedded MySQL database

Given the example shown above and using the standard MySQL JDBC connector you would connect to
this database using the code shown below.

```
Class.forName("com.mysql.jdbc.Driver")
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/databaseName", "user", "")
```

## Running the Tests

You can run the unit and tests for this project by running the following command:

```
gradlew clean build
```

## Checking dependencies

You can check the current dependencies used by the project to see whether
or not they are currently up to date by running the following command:

```
gradlew dependencyUpdates
```