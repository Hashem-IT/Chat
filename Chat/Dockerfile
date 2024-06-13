# Name des Basis-Images auf dessen
# Grundlage erweitert wird
FROM openjdk:21
# Name des Users, der dieses Image verantwortet
MAINTAINER Vorlesung VS
# RUN führt einen Befehl innerhalb des Images aus
RUN mkdir /usr/src/vs
# Kopiert vom Rechner in das Images. Chat-1 von File in Target
COPY target/Chat-1.0-SNAPSHOT-shaded.jar /usr/src/vs/Chat.jar
# Setzt das Arbeitsverzeichnis innerhalb des Containers
WORKDIR /usr/src/vs
# Ports, die der Container dem Host gegenüber veröffentlicht
EXPOSE 9000
# Befehl der nach Start des Containers ausgeführt wird (Startet eigene Anwendung)
CMD ["java", "-jar", "Chat.jar"]
#Wird als Befehl zusammengesetzt: java –jar uebung09.jar
