#!/usr/bin/env sh

export source='./desktop/build/libs/desktop-1.0.jar'
export version='0.1-alpha'

shortHash() {
    git rev-parse --short HEAD
}

fileName() {
    echo mroff-$(shortHash).jar
}

./gradlew desktop:dist
mkdir release
mv ${source} release/$(fileName)
./gh-release create Luguan/Mroff mroff-${version}