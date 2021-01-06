FROM openjdk:8-jdk-alpine

RUN apk add git \
	&& git clone https://github.com/ajulay/first.git \
	&& cd first \

WORKDIR ./docker-site

CMD yarn start

EXPOSE 3000

