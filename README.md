Java Enterprise Graduation Project
===============================
Restaurant Voting Application
===============================


Наиболее востребованные технологии /инструменты / фреймворки Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)/ Bootstrap(CSS)/ jQuery + plugins.

- [Вступительное занятие](https://github.com/JavaOPs/topjava)
- [Описание и план проекта](https://github.com/JavaOPs/topjava/blob/master/description.md)
- [Wiki](https://github.com/JavaOPs/topjava/wiki)
- [Wiki Git](https://github.com/JavaOPs/topjava/wiki/Git)
- [Wiki IDEA](https://github.com/JavaOPs/topjava/wiki/IDEA)
- [Демо разрабатываемого приложения](http://topjava.herokuapp.com/)

### Миграция TopJava на Spring-Boot
За основу взят финальный код проекта BootJava с миграцией на Spring Boot 3.0 - это первый патч открытого урока курса [CloudJava](https://javaops.ru/view/cloudjava/lesson01),
ветка [_patched_](https://github.com/JavaOPs/cloudjava/tree/patched).

-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 3.0, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html) 
Креденшелы:
```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
Guest: guest@gmail.com / guest
```