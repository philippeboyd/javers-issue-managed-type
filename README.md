# javers-issue-managed-type

for case https://github.com/javers/javers/issues/601

To simulate a working boot, make sure org.javers.mongo.javersmongoproblem.domain.AbstractPermission doesn't implement `Serializable` 

To simulate a fail boot a "get changes" request, add `implements Serializable` to org.javers.mongo.javersmongoproblem.domain.AbstractPermission

Run with : 

```bash
./gradlew clean bootRun
```