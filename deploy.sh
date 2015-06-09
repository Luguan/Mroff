#!/usr/bin/env sh

export source='./desktop/build/libs/desktop-1.0.jar'
export version='0.1-alpha'

shortHash() {
    git rev-parse --short HEAD
}

isoDate() {
    date +%Y%m%dT%H%M%S
}

fileName() {
    echo project-shitstorm-reloaded-$(isoDate)-$(shortHash).jar
}

./gradlew desktop:dist
mkdir release
mv ${source} release/$(fileName)
./gh-release create Luguan/Mroff ${version}-$(isoDate)