#!/usr/bin/env sh

cd docker-compose || exit 1

NAME='tsevrah-eulb'

if command -v rev > /dev/null 2>&1
then
    NAME="$(echo $NAME | rev)"
fi

docker-compose --project-name "$NAME" up -d

docker-compose --project-name "$NAME" logs -f
