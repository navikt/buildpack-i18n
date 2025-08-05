This repo is a minimal reconstruction of an error involving international characters in class names, in combination with the Spring Boot 
_bootBuildImage_ task which uses Paketo buildpacks. (In particular the Norwegian characters æ,ø and å)

To reproduce:
```
$ ./gradlew bootBuildImage
$ docker run -p 8080:8080 docker.io/library/demo:1.0.1
```
This bombs out with a stack trace, saying that this class does not exist. 
```
Caused by: java.io.FileNotFoundException: class path resource [com/example/demo/HelloWørld.class] cannot be opened because it does not exist
	at org.springframework.core.io.ClassPathResource.getInputStream(ClassPathResource.java:215) ~[spring-core-6.2.9.jar:6.2.9]
	at org.springframework.core.type.classreading.SimpleMetadataReader.getClassReader(SimpleMetadataReader.java:54) ~[spring-core-6.2.9.jar:6.2.9]
	at org.springframework.core.type.classreading.SimpleMetadataReader.<init>(SimpleMetadataReader.java:48) ~[spring-core-6.2.9.jar:6.2.9]
	at org.springframework.core.type.classreading.SimpleMetadataReaderFactory.getMetadataReader(SimpleMetadataReaderFactory.java:103) ~[spring-core-6.2.9.jar:6.2.9]
```
Inspecting the contents of the file _build/libs/demo-1.0.1.jar_, produced by the build target does however list this class, and running the application using either the executable jar or the via the _bootrun_ task works just fine.
```
BOOT-INF/classes/com/example/demo/HelloApplicationKt.class
BOOT-INF/classes/com/example/demo/HelloWørld.class
BOOT-INF/classes/com/example/demo/HelloApplication.class
BOOT-INF/classes/com/example/demo/HelloController.class
```
I have also tried building the image with different paketo buildpacks, but _bellsoft-liberica_ and _adoptium_ both fail.

Renaming the class _HelloWørld_ to _HelloWorld_ makes everything work as expected

This leads me to believe that there is a bug in either the _bootBuildImage_ task or in one of the buildpacks, but this is as far as I am able to analyze this myself
