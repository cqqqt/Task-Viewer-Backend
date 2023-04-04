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