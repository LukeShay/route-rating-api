#!/bin/bash -e

directories() {
    mkdir -p "${2}/src/main/java/com/routerating/api/${2}"
    mkdir -p "${2}/src/main/resources"
    mkdir -p "${2}/src/test/java/com/routerating/api/${2}"
}

files() {
    echo "apply plugin: 'org.springframework.boot'

archivesBaseName = '${2}'

dependencies {
    implementation project(':common')

    // Spring
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-web', version: project.springBootVersion
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-security', version: project.springBootVersion
}" > "${2}/build.gradle"

    echo "AWSTemplateFormatVersion: '2010-09-09'
Transform: AWS::Serverless-2016-10-31
Description: Rest API for Route Rating.

Metadata:
  AWS::ServerlessRepo::Application:
    Name: rest-api-${2}
    Description: Rest API for Route Rating.
    Author: Luke Shay
    ReadmeUrl: README.md
    HomePageUrl: https://lukeshay.com
    SemanticVersion: 0.0.1
    SourceCodeUrl: https://github.com/LukeShay/route-rating-api
    private: true

Resources:
  ${1}ApiFunction:
    Type: AWS::Serverless::Function
    Properties:
      Handler: com.routerating.api.${1}Handler::handleRequest
      Runtime: java11
      CodeUri: build/distributions/${2}-0.0.1.zip
      MemorySize: 1024
      Policies: AWSLambdaBasicExecutionRole
      Timeout: 120
      Role: arn:aws:iam::816188110262:role/LambdaRole
      Events:
        GetResource:
          Type: Api
          Properties:
            Path: /{proxy+}
            Method: any

Outputs:
  ${1}Api:
    Description: URL for application
    Value: !Sub 'https://\${ServerlessRestApi}.execute-api.\${AWS::Region}.amazonaws.com/Prod'
    Export:
      Name: ${1}Api
" > "${2}/template.yml"

    echo "# ${2}-service" > "${2}/README.md"

echo "do.key.access=\${DIGITAL_OCEAN_ACCESS_KEY:-P14VEXIKJH7324T9K5YU}
do.key.secret=\${DIGITAL_OCEAN_SECRET_KEY:-+5rL+mZxuh987hsd93t8gfT7t0iY&h8WOTcQSJ91q18}
file.bucket.name=route-rating
file.bucket.url=nyc3.digitaloceanspaces.com
security.secret.jwt=\${REST_API_JWT_SECRET:-jonValaXQyPJpyG94tlpsDdmLMk42yqMbRpI51PJKOnK6sWK}
security.secret.refresh=\${REST_API_REFRESH_SECRET:-jonValaXQyPJpyG94tlpsDdmLMk42yqMbRpI51PJKOnK6sWK}
spring.aop.auto=false
spring.datasource.initialization-mode=always
spring.datasource.password=\${REST_API_DB_PASSWORD:-password}
spring.datasource.platform=postgres
spring.datasource.url=\${REST_API_DB_URL:-jdbc:postgresql://localhost:5432/routerating}
spring.datasource.username=postgres
spring.jmx.enabled=false
spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
spring.jpa.show-sql=false
spring.main.allow-bean-definition-overriding=true
spring.main.banner-mode=off
spring.main.lazy-initialization=true
spring.servlet.multipart.enabled=true
spring.servlet.multipart.file-size-threshold=2KB
spring.servlet.multipart.max-file-size=200MB
spring.servlet.multipart.max-request-size=215MB" > "${2}/src/main/resources/application.properties"

    sed -i '' "\$s/$/, '${2}'/" settings.gradle

    echo "package com.routerating.api;

import com.routerating.api.common.Handler;

public class ${1}Handler extends Handler<Application> {
	public ${1}Handler() {
		super(Application.class);
	}
}
" > "${2}/src/main/java/com/routerating/api/${1}Handler.java"

    echo "package com.routerating.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = \"com.routerating.api\", lazyInit = true)
@EnableJpaRepositories(basePackages = \"com.routerating.api\")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
" > "${2}/src/main/java/com/routerating/api/Application.java"
}

new-service() {
    directories ${1} $(echo "${1}" | tr '[:upper:]' '[:lower:]')
    files ${1} $(echo "${1}" | tr '[:upper:]' '[:lower:]')

    echo "Now you need to add the new service as a module in IntelliJ gradle plugin."
}
