# CLI generator for Eiffel messages

Note! This repo is still work in progress

This is a wrapper around [tracey-eiffel-protocol](https://github.com/Praqma/tracey-protocol-eiffel)
Allows to extract data from different sources and generate corresponding Eiffel messages

Supported messages:

* EiffelSourceChangeCreatedEvent (message will be generated from the current commit, more options to come)

```
$ ./gradlew uberjar
$ java -jar build/libs/tracey-protocol-eiffel-cli-generator-drop1.jar EiffelSourceChangeCreatedEvent
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Jul 08, 2016 8:54:59 AM net.praqma.tracey.protocol.eiffel.cli.Main main
INFO: {
  "meta": {
    "id": "375c88c2-07cd-43c5-b3cf-b9ad23197087",
    "type": "EiffelSourceChangeCreatedEvent",
    "time": "1467960899468",
    "source": {
      "domainId": "domainId",
      "host": "host",
      "name": "name",
      "uri": "uri",
      "serializer": {
        "groupId": "group",
        "artifactId": "id",
        "version": "1.0.0"
      }
    }
  },
  "links": [{
    "type": "PREVIOUS_VERSION",
    "id": "6c5ffc66-2bd5-46f4-ab0a-837d49888da5"
  }, {
    "type": "CAUSE",
    "id": "d4e6e737-c0e1-4b1a-b2fd-82b059d60007"
  }],
  "data": {
    "author": {
      "name": "Andrey Devyatkin",
      "email": "andrey.a.devyatkin@gmail.com"
    },
    "change": {
      "insertions": 133,
      "files": ["ADD .travis.yml", "ADD build.gradle", "ADD src/main/java/net/praqma/tracey/protocol/eiffel/cli/Main.java"]
    },
    "gitIdentifier": {
      "commitId": "a25bcbf244555bfb00b40376ec0ebf3872cd8eb3",
      "branch": "master",
      "repoName": "tracey-protocol-eiffel-cli-generator",
      "repoUri": "https://github.com/Praqma/tracey-protocol-eiffel-cli-generator.git"
    }
  }
}
```