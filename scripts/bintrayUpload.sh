#!/bin/bash

./gradlew bintrayUpload -PbintrayUser="${bintrayUser}" -PbintrayKey="${bintrayApiKey}" -PdryRun=false