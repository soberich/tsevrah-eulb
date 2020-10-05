FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle check --project-dir=gateway --no-daemon
RUN gradle check --project-dir=account --no-daemon
RUN gradle check --project-dir=transaction --no-daemon
