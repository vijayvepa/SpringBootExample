# Deployment Options

## Executable Jar

- Config 

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <executable>true</executable>
            </configuration>
        </plugin>
    </plugins>
</build>
```

- Build

```shell
./mvnw clean package
```

- Execute

```shell
java -jar .\planefinder-0.0.1-SNAPSHOT.jar
```

## Expanded Jar

- Make directory

```shell
cd target
mkdir expanded
cd expanded
```

- Extract Jar
```shell
 jar -xvf ..\planefinder-0.0.1-SNAPSHOT.jar
```

- Run 

```shell
java org.springframework.boot.loader.JarLauncher
```

## Build Image

- Config (Optional)

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <image>
                    <name>vijayvepa/${project.artifactId}</name>
                </image>
            </configuration>
        </plugin>
    </plugins>
</build>
```

- Ensure Docker Desktop is running
- Open Admin Console (windows)

```shell
./mvnw spring-boot:build-image
```

- Check images list
```shell
docker images
```

- Push image
```shell
docker login 
docker push vijayvepa/planefinder
```

- Run Image
```shell
docker run --name myplanefinder -p7634:7634 vijayvepa/planefinder
```

- Inspect image
```shell
 choco install pack 
 pack inspect-image vijayvepa/planefinder      
 
```

- View Image File Contents
```shell
choco install dive  
dive  vijayvepa/planefinder   
```