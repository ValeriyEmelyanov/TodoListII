# Todo List II

Строка подключения к базе jdbc:h2:D:/Projects/TodoListII/DB/TodoListDB

#### Описание
Создано по материалам учебного курса Spring Data.
Не является законченным приложением. Продемострировано:
* Создание пустого проекта Spring MVC (ветка Step1_SimpleSpringMVC)
* Работа с Template JDBC (ветка Step2_WorkWithJdbcTemplate)
* Работа с Entity Manager (ветка Step3_WorkWithEntityManager)
* Работа с JPA (ветки Step4_WorkWithJpa и main)
* Аутентификация с созданием токена
* Работа с АОП на примере авторизации пользователя
* Модульное и интеграционное тестирование

#### Технологии
* Java 11
* Maven
* Spring MVC
* Spring JDBC, Spring Data JPA
* JUnit 4

#### Полезные ссылки
* [Обработка исключений в контроллерах Spring](https://habr.com/ru/post/528116/)
* [Java and Spring development](https://blog.espenberntsen.net/2010/03/20/aspectj-cheat-sheet/)

#### Работа с HTTP запросами в консоли браузера

Добавить пользователя:
```
fetch("http://localhost:8080/TodoListII_war_exploded/users/create", {
    body: JSON.stringify({
        id: 1,
        email: "user@mail.ru",
        password: "qwerty123"
    }),
    method: "post",
    headers: new Headers({
        "Content-type": "application/json; characterSet: UTF-8"
    })
});
```
Получить пользователя:
```
fetch("http://localhost:8080/TodoListII_war_exploded/users/1", {
    method: "get",
    headers: new Headers({
        "Content-type": "application/json; characterSet: UTF-8"
    })
});
```
Изменить пользователя:
```
fetch("http://localhost:8080/TodoListII_war_exploded/users/1", {
    body: JSON.stringify({
        id: 1,
        email: "usr@mail.ru",
        password: "qwerty345"
    }),
    method: "put",
    headers: new Headers({
        "Content-type": "application/json; characterSet: UTF-8"
    })
});
```
