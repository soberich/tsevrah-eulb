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

rm -rf "${root}/uaa"

rm -rf "${root}/gateway"
rm -rf "${root}/transaction"
rm -rf "${root}/account"

jhipster jdl apps-schema.jh \
  --creation-timestamp 1970-01-01 \
  --experimental \
  --force \
  --prettier-java \
  --skip-cache \
  --skip-git \
  --with-entities \
  --yarn

cd "${root}/gateway" || exit

yarn upgrade '@types/enzyme@3.10.7' --dev

cd "${root}" || exit

rm "${root}/gateway/.gitignore"
rm "${root}/gateway/.gitattributes"
rm "${root}/transaction/.gitignore"
rm "${root}/transaction/.gitattributes"
rm "${root}/account/.gitignore"
rm "${root}/account/.gitattributes"
