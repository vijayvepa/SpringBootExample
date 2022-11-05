# Okta Login
- Install [Okta CLI](https://cli.okta.com/)
- Run `okta login`
- Run `okta apps create`
  - Select Okta SpringBoot starter
  - Use the default login and logout url
  - It will generate application.properties file
- Add following dependencies in build.gradle
```groovy

	//region security
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
	implementation 'com.okta.spring:okta-spring-boot-starter:2.1.6'
	//endregion
```