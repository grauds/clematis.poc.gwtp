FROM tomcat:7.0-jdk8-corretto

RUN mkdir -p /usr/local/tomcat/webapps/elp
COPY war/ /usr/local/tomcat/webapps/elp