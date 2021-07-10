# Todo List II

Строка подключения к базе jdbc:h2:D:/Projects/TodoListII/DB/TodoListDB

#### Заметки

Добавить пользователя через консоль в браузере:
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