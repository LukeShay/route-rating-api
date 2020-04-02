#!/bin/bash -e

directories() {
    mkdir -p "${1,,}/src/main/java"
    mkdir -p "${1,,}/src/main/resources"
    mkdir -p "${1,,}/src/test/java"
}

files() {
    echo "archivesBaseName = '${1,,}'" > "${1,,}/build.gradle"
    echo "AWSTemplateFormatVersion: '2010-09-09'
Transform: AWS::Serverless-2016-10-31
Description: Rest API for Route Rating.

Metadata:
  AWS::ServerlessRepo::Application:
    Name: rest-api-${1,,}
    Description: Rest API for Route Rating.
    Author: Luke Shay
    ReadmeUrl: README.md
    HomePageUrl: https://lukeshay.com
    SemanticVersion: 0.0.1
    SourceCodeUrl: https://github.com/LukeShay/route-rating-api
    private: true

Resources:
  ${1,,^}ApiFunction:
    Type: AWS::Serverless::Function
    Properties:
      Handler: com.routerating.api.${1,,^}Handler::handleRequest
      Runtime: java11
      CodeUri: build/distributions/${1,,^}-0.0.1.zip
      MemorySize: 1024
      Policies: AWSLambdaBasicExecutionRole
      Timeout: 120
      Events:
        GetResource:
          Type: Api
          Properties:
            Path: /{proxy+}
            Method: any

Outputs:
  ${1,,^}Api:
    Description: URL for application
    Value: !Sub 'https://\${ServerlessRestApi}.execute-api.\${AWS::Region}.amazonaws.com/Prod'
    Export:
      Name: ${1,,^}Api
" > "${1,,}/template.yml"
    echo "${1,,}-service" > "${1,,}/README.md"
}

new-service() {
    directories ${1}
    files ${1}
}
