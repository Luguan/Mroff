#!/usr/bin/env sh

export source='./desktop/build/libs/desktop-1.0.jar'
export version='0.1-pre'

shortHash() {
    git rev-parse --short HEAD
}

commitCount() {
    git rev-list HEAD --count
}
fileName() {
    echo mroff-$(shortHash).jar
}

./gradlew desktop:dist
mkdir release
mv ${source} release/$(fileName)
./gh-release create Luguan/Mroff ${version}-b$(commitCount)