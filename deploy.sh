#!/usr/bin/env sh

export desktopJar='./desktop/build/libs/desktop-1.0.jar'
export htmlFolder='../../html/build/dist/*'
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

createDesktopDist() {
    ./gradlew desktop:dist
    mkdir release
    mv ${desktopJar} release/$(fileName)
    ./gh-release create Luguan/Mroff ${version}-b$(commitCount)
}

createWebDist() {
    ./gradlew html:dist
    git clone git@github.com:Luguan/Mroff.git --branch gh-pages --single-branch gh-pages
    cd gh-pages
    git config user.name "Circle CI"
    git config user.email hk.henrik+circle@gmail.com

    mkdir game
    cd game
    rm -rf ./*
    mv ${htmlFolder} .
    git add -A
    git commit -m "Build #$(commitCount)"
    git push
}

createDesktopDist
createWebDist