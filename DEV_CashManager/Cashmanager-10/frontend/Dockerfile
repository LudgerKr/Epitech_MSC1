FROM ubuntu:18.04

RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get -y install apache2
RUN apt-get -y install android-sdk

WORKDIR /home/frontend/
COPY . /home/frontend/

RUN ./gradlew build

RUN mv /home/frontend/app/build/outputs/apk/debug/app-debug.apk /home/frontend/app/build/outputs/apk/debug/client.apk 


EXPOSE 80

CMD /usr/sbin/apache2ctl -D FOREGROUND