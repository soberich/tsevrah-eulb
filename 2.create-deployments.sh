#!/usr/bin/env sh


if ! command -v jhipster >/dev/null 2>&1
then
  if command -v yarn >/dev/null 2>&1
  then
    yarn global add generator-jhipster
  else
    echo "You must have yarn installed, see https://classic.yarnpkg.com/en/docs/install"
    exit 1
  fi
fi

root="$(pwd)"

rm -rf "${root}/docker-compose"

rm -rf "${root}/kubernetes"
rm -rf "${root}/k8s"

rm -rf "${root}/kubernetes-helm"
rm -rf "${root}/k8s-helm"

rm -rf "${root}/kubernetes-knative"
rm -rf "${root}/knative"

jhipster jdl deployments-schema.jh

cd "${root}/gateway" || exit 1
./gradlew bootJar -Pprod jibDockerBuild

cd "${root}/transaction" || exit 1
./gradlew bootJar -Pprod jibDockerBuild

cd "${root}/account" || exit 1
./gradlew bootJar -Pprod jibDockerBuild

# go back to root
cd "${root}" || exit 1
