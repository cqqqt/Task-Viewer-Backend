[![DevOps By Rultor.com](https://www.rultor.com/b/eo-cars/eo-kafka)](https://www.rultor.com/p/eo-cqrs/eo-kafka)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![maven](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/maven.yml/badge.svg)](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/maven.yml)
[![docker](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/docker.yml/badge.svg)](https://github.com/cqqqt/Task-Viewer-Backend/actions/workflows/docker.yml)
[![docker.io](https://img.shields.io/docker/v/abialiauski/taskviewer-api/latest)](https://hub.docker.com/repository/docker/abialiauski/taskviewer-api/general)

[![Hits-of-Code](https://hitsofcode.com/github/cqqqt/Task-Viewer-Backend)](https://hitsofcode.com/view/github/cqqqt/Task-Viewer-Backend)
[![Lines-of-Code](https://tokei.rs/b1/github/cqqqt/Task-Viewer-Backend)](https://github.com/cqqqt/Task-Viewer-Backend)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/cqqqt/Task-Viewer-Backend/blob/master/LICENSE.txt)

Task Viewer Core Engine

[_Users API_](#users-api)
<br>
[_Tasks API_](#tasks-api)
<br>
[_Comments API_](#comments-api)
<br>
[_Scheduled Jobs_](#scheduled-jobs)
<br>
[_Auth API_](#auth-api)
<br>

# Prerequisites

You need to have [```Docker```](https://www.docker.com), ```Java 17+```, and ```Maven 3.8.7+``` installed.

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

## API

### Users API

Add new Admin:
<br>
**POST** [**Admin**] ```/api/v1/users/admin```
<br>
Body example:

```json
{
  "username": "h1alexbel",
  "firstname": "Aliaksei",
  "lastname": "Bialiauski",
  "password": "rawPassword",
  "email": "abiliauski.dev@gmail.com"
}
```

Update user info:
<br>
**PUT** [**Admin, User**] ```/api/v1/users```
<br>
Body example:

```json
{
  "firstname": "Aliaksei",
  "lastname": "Bialiauski"
}
```

Get Account info:
**GET** [**Admin, User**] ```/api/v1/users/me```

Get User info by username:
**GET** [**Admin, User**] ```/api/v1/users/@{username}```

**GET** [**Admin, User**] ```/api/v1/users```

also, search criteria can be applied(Optional):

```/api/v1/users?firstname=firstnameForSearch&lastname=lastnameForSearch```

### Tasks API

Create new Task:
<br>
**POST** [**Admin**] ```/api/v1/tasks```
<br>
Body example:

```json
{
  "username": "h1alexbel",
  "title": "TV-1",
  "about": "rultor setup",
  "status": "in progress",
  "priority": "5",
  "due": "2023-04-11T11:51:18.189942"
}
```

about is optional.

Replicate the Task:
<br>
**POST** [**Admin**] ```/api/v1/tasks/replicate/{id}```
<br>
id - task id.

Update the Task:
<br>
**PUT** [**Admin**] ```/api/v1/tasks/{id}```
<br>
id - task id.
<br>
Body example:

```json
{
  "username": "h1alexbel",
  "title": "TV-1",
  "about": "rultor setup",
  "status": "in progress",
  "priority": "5",
  "due": "2023-04-11T11:51:18.189942"
}
```

about is optional.

Delete a Task:
<br>
**DELETE** [**Admin**] ```/api/v1/tasks/{id}```
<br>
id - task id.

Close the Task:
<br>
**PUT** [**Admin**] ```/api/v1/tasks/close/{id}```
<br>
id - task id.

Assign the Task:
<br>
**POST** [**Admin**] ```/api/v1/tasks/assign/{id}/{username}```
<br>
id - task id.
<br>
username - assignee's username.

**GET** [**Admin, User**] ```/api/v1/tasks```
<br>
also, search criteria can be applied(Optional):

```/api/v1/tasks?<args>```

Args:
```
  "title": "...",
  "username": "...",
  "status": "...",
  "priority": "...",
  "estimate": "...",
  "sort": "<sort option>"
}
```

sorting can be done with `status`, `title`, `username`, and `priority`.

Get the Assigned Tasks:
<br>
**GET** [**Admin, User**] ```/api/v1/tasks/@{username}```
<br>
username - assignee's username.

Get schedule for next 7 days:
<br>
**GET** [**Admin, User**] ```/api/v1/weekly```

Get schedule for next 7 days for user with id {id}:
<br>
**GET** [**Admin**] ```/api/v1/weekly/{id}```
<br>
id - user id.

### Comments API

Post a comment:
<br>
**POST** [**Admin, User**] ```/api/v1/comments```
<br>
Body example:

```json
{
  "content": "app coverage should be more than 65%",
  "task": "123"
}
```

Update comment:
<br>
**PUT** [**Admin, User**] ```/api/v1/comments/{id}```
<br>
Body example:

```json
{
  "content": "app coverage should be more than 70%"
}
```

Delete a Comment:
<br>
**DELETE** [**Admin, User**] ```/api/v1/comments/{id}```
<br>
id - comment id.

**GET** [**Admin, User**] ```/api/v1/comments```
<br>
also, search criteria can be applied(Optional):

```/api/v1/comments?user=1&task=1```
<br>
```/api/v1/comments?user=1```
<br>
```/api/v1/comments?task=1```
<br>

### Auth API

**POST** [**Public**] ```/api/v1/auth/login```
Body example:

```json
{
  "username": "h1alexbel",
  "password": "root"
}
```

**POST** [**Public**] ```/api/v1/auth/register```
Body example:

```json
{
  "username": "h1alexbel",
  "firstname": "Aliaksei",
  "lastname": "Bialiauski",
  "password": "rawPassword",
  "email": "abiliauski.dev@gmail.com"
}
```

firstname and lastname are optional.

**POST** [**Public**] ```/api/v1/auth/refresh```
Body example:

```json
{
  "token": "<...>"
}
```

### Scheduled Jobs

[ExpirationEmailScheduler](https://github.com/cqqqt/Task-Viewer-Backend/blob/master/src/main/java/com/taskviewer/api/scheduled/ExpirationEmailScheduler.java)
runs every `12h` and notifies assignee that task will be expired soon.
<br>
[ExpiredTaskEmailScheduler](https://github.com/cqqqt/Task-Viewer-Backend/blob/master/src/main/java/com/taskviewer/api/scheduled/ExpiredTaskEmailScheduler.java)
runs every `1h` and notifies assignee and reporter that task was expired.

# Security

Task-Viewer is a secured API. We are using [```JWT```](https://www.wikiwand.com/en/JSON_Web_Token) Bearer formatted
token
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
