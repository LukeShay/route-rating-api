FROM lambci/lambda:build-java11

RUN mkdir -p /app
ENV PROJECT_HOME /app

WORKDIR $PROJECT_HOME

COPY gradle ./gradle
COPY gradlew ./gradlew

RUN ./gradlew --build-cache --no-daemon

ADD "https://www.random.org/cgi-bin/randbyte?nbytes=10&format=h" skipcache

COPY . ./

CMD ./gradlew -x test \
        -x distTar \
        -x distZip \
        -x bootDistTar \
        -x bootDistZip \
        -x bootJar \
        -x startScripts \
        -x bootStartScripts \
        clean build
