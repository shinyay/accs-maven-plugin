#!/bin/bash

GROUPID=com.oracle.jp.shinyay
ARTIFACTID=accs-maven-plugin
VERSION=1.0-SNAPSHOT

mvn archetype:generate -DgroupId=${GROUPID} -DartifactId=${ARTIFACTID} -Dversion=${VERSION} -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-plugin
