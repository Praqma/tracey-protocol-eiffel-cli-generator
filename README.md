# CLI generator for Eiffel messages

Note! This repo is still work in progress

This is a wrapper around [tracey-eiffel-protocol](https://github.com/Praqma/tracey-protocol-eiffel)
Allows to extract data from different sources and generate corresponding Eiffel messages

Supported messages:

* EiffelSourceChangeCreatedEvent (message will be generated from the current commit, more options to come)

```
$ ./gradlew build
$ java -jar build/libs/tracey-protocol-eiffel-cli-generator.jar EiffelSourceChangeCreatedEvent -p Praqma/tracey-protocol-eiffel-cli-generator -c HEAD~1
0 [main] WARN net.praqma.tracey.protocol.eiffel.cli.Main  - {
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