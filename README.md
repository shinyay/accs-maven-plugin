# Maven Plugin for Oracle Application Container Cloud Service

This is a Maven plugin for Oracle Application Container Cloud Service.

## Description

[Oracle Application Container](https://cloud.oracle.com/acc) is simple as easy-to-use service that offers a scalable platform for multiple languages.
All you have to do is to build, zip and deploy an application.
This service is composed by Docker though, you don't have to take care of the infrastructure. If you don't know Docker you can run your application on ACCS. You just take care of only your application since ACCS wrap Docker interface.

ACCS has REST API as well as Brower GUI. This maven plugin enables you to use REST API with Maven Commands instead of native command like curl.

## Demo

## Features

This plugin has the following feature:

- Create an Application
- Create a deployment.json
- Create a manifest.json
- Package an Application and a manifest.json
- View All Applications

## Requirement

- Maven 3
- Java 1.8

## Usage

### Create an Applicaion

- command: `accs:create`

Configure ACCS instance information under configuration element in pom.xmliguration>

```xml
<configuration>
    <region>emea</region>
    <identitydomain>${opc.identitydomain}</identitydomain>>
    <user>${opc.user}</user>
    <password>${opc.password}</password>
    <storage>${scs.container}</storage>
    <appName>MY-APP</appName>
    <runtime>java</runtime>
    <subscription>Hourly</subscription>
    <archiveURL>${scs.container}/${project.name}-${project.version}.zip</archiveURL>
</configuration>
```

- **region**: Your Region your data center belongs to
  - `us` or `emea`
- **identitydomain**: Your Identity Domain you sbscribe to
- **user**: Your user name
- **password**: Your password
- **storage**: Storage Container Name application archive is uploaded to
- **appName**: Application Name
- **runtime**: Application runtime for your application
  - `java` or `node` or `php`

  > You can use just **java** currently in the version of 1.0.0

- **subscription**: Your subscription type
  - `Hourly` or `Monthly`
- **archiveURL**: Application archive name with Storage Container name

### Create a deployment.json

- command: `accs:create-deployment`

Configure deployment metainfo under configuration element in pom.xml

```xml
<configuration>
    <metaJsonPath>${project.basedir}\metafiles</metaJsonPath>
    <memory>1G</memory>
    <instances>1</instances>
</configuration>
```

- **metaJsonPath**: Output path for deployment.json
- **memory**: Memory capacity assigned for your application instance
- **instances**: Number of the container your application is deployed to

### Create a manifest.json

- command: `accs:create-manifest`

Configure applicatiton metainfo under configuration element in pom.xml

```xml
<configuration>
    <metaJsonPath>${project.basedir}\metafiles</metaJsonPath>
    <archiveName>${project.name}-${project.version}</archiveName>
    <majorVersion>8</majorVersion>
    <command>java -jar ${project.artifactId}-${project.version}.jar</command>
    <version>${project.version}</version>
</configuration>
```

- **metaJsonPath**: Output path for deployment.json
- **archiveName**: Application archive name without extension
  - Archive is gerated as `zip` format
- **majorVersion**: Runtime version
    - In the case of `java`
      - `8` or `7`
- **command**: Launch command
- **version**: Application version as identifier

### Package an Application and a manifest.json

- command: `accs:package`

Generate a applio archive with an applicat binary and a manifest.json

### View All Applications

- command: `accs:list`

View all applications on ACCS

## Installation

Add the plugin as a dependency in your Maven project.
And configure the information described before.

```xml
<plugin>
    <groupId>com.oracle.jp.shinyay</groupId>
    <artifactId>accs-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
        <region>emea</region>
        <identitydomain>${opc.identitydomain}</identitydomain>>
        <user>${opc.user}</user>
        <password>${opc.password}</password>
        <storage>${scs.container}</storage>
        <appName>MY-APP</appName>
        <runtime>java</runtime>
        <subscription>Hourly</subscription>
        <archiveURL>${scs.container}/${project.name}-${project.version}.zip</archiveURL>
        <metaJsonPath>${project.basedir}\metafiles</metaJsonPath>
        <archiveName>${project.name}-${project.version}</archiveName>
        <majorVersion>8</majorVersion>
        <command>java -jar ${project.artifactId}-${project.version}.jar</command>
        <version>${project.version}</version>
        <memory>1G</memory>
        <instances>1</instances>
    </configuration>
</plugin>
```

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/44f0f4de510b4f2b918fad3c91e0845104092bff/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
