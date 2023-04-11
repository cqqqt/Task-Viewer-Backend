[![DevOps By Rultor.com](https://www.rultor.com/b/eo-cars/eo-kafka)](https://www.rultor.com/p/eo-cqrs/eo-kafka)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![maven](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/maven.yml/badge.svg)](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/maven.yml)
[![docker](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/docker.yml/badge.svg)](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/docker.yml)
[![docker.io](https://img.shields.io/docker/v/abialiauski/taskviewer-api/latest)](https://hub.docker.com/repository/docker/abialiauski/taskviewer-api/general)

[![Hits-of-Code](https://hitsofcode.com/github/cqqqt/Task-Viewer-Backend)](https://hitsofcode.com/view/github/cqqqt/Task-Viewer-Backend)
[![Lines-of-Code](https://tokei.rs/b1/github/cqqqt/Task-Viewer-Backend)](https://github.com/cqqqt/Task-Viewer-Backend)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/cqqqt/Task-Viewer-Backend/blob/master/LICENSE.txt)

Task Viewer Core Engine

## Quick Start
Run this script to start containers:
```shell
$ sh up.sh
```

Then, go to POST `${host}:8080/api/v1/auth/login` With the next body:
```json
{
  "username": "admin",
  "password": "root"
}
```
You will receive Access and Refresh tokens. You will need them using the Task-Viewer API.
Access Token expires in 20 minutes, while Refresh one - 24 hours.

# Security

Task-Viewer is a secured API. We are using [```JWT```](https://www.wikiwand.com/en/JSON_Web_Token) Bearer formatted token
to authenticate and authorize users.
So, each request to the secured resources should go with the ```Autorization``` header.
e.g.:

```
-X Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlkIjo4LCJ0eXBlIjoiQUNDRVNTIiwicm9sZSI6IlJPTEVfQURNSU4iLCJleHAiOjE2ODEyMjI2MTR9.tueREEm0kdJM6nEBemM5PCT-5bT02SMkRNfmK0t9CE7CSM9kyRhVdz2VwFZhECuq6GvIinFBaZP11814k7od3Q
```

Also, we have 2 types of authority: ```USER```, and the ```ADMIN```

## How to Contribute

Fork repository, make changes, send us a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install
```

You will need Maven 3.8.7+ and Java 17+.

Our [rultor image](https://github.com/eo-cqrs/eo-kafka-rultor-image) for CI/CD.