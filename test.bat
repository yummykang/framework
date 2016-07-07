@echo off
title generate project with base framework:framework-1.0-SNAPSHOT
set /p groupId=input your groupId:
set /p artifactId=input your artifactId:
set /p version=input your version:

mvn -X archetype:generate -DarchetypeGroupId=me.yummykang -DarchetypeArtifactId=framework -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=%groupId% -DartifactId=%artifactId% -Dversion=%version% -DarchetypeCatalog=local

pause