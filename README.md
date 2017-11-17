# javers-issue-managed-type

To simulate a working boot, make sure org.javers.mongo.javersmongoproblem.domain.AbstractPermission doesn't implement `Serializable` 

To simulate a fail boot a "get changes" request, add `implements Serializable` to org.javers.mongo.javersmongoproblem.domain.AbstractPermission

Run with : 

```bash
./gradlew clean bootRun
```