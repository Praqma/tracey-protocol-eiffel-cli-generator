[![License](https://img.shields.io/badge/license-New%20BSD-blue.svg)](LICENSE) [![Build Status](https://api.travis-ci.org/Praqma/tracey-protocol-eiffel-cli-generator.svg?branch=master)](https://travis-ci.org/Praqma/tracey-protocol-eiffel-cli-generator) [![Release](https://jitpack.io/v/Praqma/tracey-protocol-eiffel-cli-generator.svg)](https://jitpack.io/#Praqma/tracey-protocol-eiffel-cli-generator)
---
maintainer: andrey9kin
---
# CLI generator for Eiffel messages

Note! This repo is still work in progress

This is a wrapper around [tracey-eiffel-protocol](https://github.com/Praqma/tracey-protocol-eiffel)
Allows to extract data from different sources and generate corresponding Eiffel messages

## Supported messages

### EiffelSourceChangeCreatedEvent

message will be generated from the current commit, more options to come.

**Example command** `EiffelSourceChangeCreatedEvent -p Praqma/tracey-protocol-eiffel-cli-generator -c HEAD~1`

**Output**

```
{
  "meta": {
    "id": "f8a8bca7-cbc1-4f4a-904a-226acbd66208",
    "type": "EiffelSourceChangeCreatedEvent",
    "time": "1469130192472",
    "source": {
      "host": "Andreys-MacBook-Pro.local",
      "name": "Eiffel command line generator",
      "uri": "https://github.com/Praqma/tracey-protocol-eiffel-cli-generator",
      "serializer": {
        "groupId": "net.praqma.tracey.protocol.eiffel.cli",
        "artifactId": "tracey-protocol-eiffel-cli-generator",
        "version": "drop1-10-gc7704af.dirty"
      }
    }
  },
  "links": [{
    "type": "PREVIOUS_VERSION",
    "id": "de3b7468-67b1-4f46-8ecc-a8ac251845f6"
  }, {
    "type": "CAUSE",
    "id": "a0e42d9d-0ccc-4a79-a2b3-676a40632018"
  }],
  "data": {
    "author": {
      "name": "Andrey Devyatkin",
      "email": "andrey.a.devyatkin@gmail.com"
    },
    "issues": [{
      "id": "7",
      "transition": "RESOLVED",
      "uri": "http://github.com/Praqma/tracey-protocol-eiffel-cli-generator/issues/7",
      "trackerType": "GitHub"
    }],
    "change": {
      "insertions": 28,
      "deletions": 6,
      "files": ["MODIFY .travis.yml", "MODIFY build.gradle", "ADD config/checkstyle/checkstyle.xml", "MODIFY src/main/java/net/praqma/tracey/protocol/eiffel/cli/Main.java"]
    },
    "gitIdentifier": {
      "commitId": "c2bb5a9f7dbfabdcdba9957128ce5387963e32c1",
      "branch": "master",
      "repoName": "tracey-protocol-eiffel-cli-generator",
      "repoUri": "https://github.com/Praqma/tracey-protocol-eiffel-cli-generator.git"
    }
  }
}
```

### EiffelCompositionDefinedEvent

**Example command** `tracey-protocol-eiffel-cli-generator.jar EiffelCompositionDefinedEvent -n Composition_A -l CAUSE:8a718a03-f473-4e61-9bae-e986885fee18`

**Output**

```
{
  "meta": {
    "id": "d2f20d54-5a8e-4a69-a7d1-5a11b6d89bf2",
    "type": "EiffelCompositionDefinedEvent",
    "time": "1469533752898",
    "source": {
      "host": "mads-notebook",
      "name": "Eiffel command line generator",
      "uri": "https://github.com/Praqma/tracey-protocol-eiffel-cli-generator",
      "serializer": {
        "groupId": "net.praqma.tracey.protocol.eiffel.cli",
        "artifactId": "tracey-protocol-eiffel-cli-generator",
        "version": "drop1-19-gdd2bbde"
      }
    }
  },
  "links": [{
    "type": "CAUSE",
    "id": "8a718a03-f473-4e61-9bae-e986885fee18"
  }],
  "data": {
    "name": "Compsition_A"
  }
}
```

### EiffelArtifactCreatedEvent

Creates an artifact created event. Use with the `-m` option to point to a pom file for parsing maven projects.

**Example command:** `EiffelArtifactCreatedEvent -l CAUSE:8a718a03-f473-4e61-9bae-e986885fee18 -c "mvn clean package" -a artifact-one -w 1.0 -g artifact-group`

**Output**

```

{
  "meta": {
    "id": "f49c5ecc-e48f-42ad-b870-465ddd1c6292",
    "type": "EiffelArtifactCreatedEvent",
    "time": "1469536428257",
    "source": {
      "host": "mads-notebook",
      "name": "Eiffel command line generator",
      "uri": "https://github.com/Praqma/tracey-protocol-eiffel-cli-generator",
      "serializer": {
        "groupId": "org.eclipse.jgit",
        "artifactId": "slf4j-log4j12",
        "version": "1.7.21"
      }
    }
  },
  "links": [{
    "type": "CAUSE",
    "id": "8a718a03-f473-4e61-9bae-e986885fee18"
  }],
  "data": {
    "gav": {
      "groupId": "artifact-group",
      "artifactId": "artifact-one",
      "version": "1.0"
    },
    "buildCommand": "mvn clean package"
  }
}
```


## Releasing

To release a new version of this CLI on Github release you need to tag the commit to release. This will be picked up by Travis CI.

### Github auth for Travis release

Release is done a ReleasePraqma user and was securely created using `travis setup releases`

    $ travis setup releases
    Detected repository as Praqma/tracey-protocol-eiffel-cli-generator, is this correct? |yes| yes
    Username: ReleasePraqma
    Password for ReleasePraqma: **********
    File to Upload: build/libs/tracey-protocol-eiffel-cli-generator.jar
    Deploy only from Praqma/tracey-protocol-eiffel-cli-generator? |yes| yes
    Encrypt API key? |yes| yes
