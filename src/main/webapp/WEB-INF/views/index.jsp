<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Accident</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
    <div class="container">
        <h3>
            <span>Список инцидентов</span>
            <a href="/accident/logout" class='pull-right' style="font-size: smaller">Выйти</a>
            <span class='pull-right' style="font-size: smaller">${user.username} | </span><br>
            <a href="/accident/reg" class='pull-right' style="font-size: smaller">Регистрация</a>
        </h3>
    </div>
        <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Адрес</th>
                    <th>Тип</th>
                    <th>Статьи</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${accidents}" var="accident">
                        <tr>
                            <td><c:out value="${accident.id}"/></td>
                            <td><c:out value="${accident.name}"/></td>
                            <td><c:out value="${accident.text}"/></td>
                            <td><c:out value="${accident.address}"/></td>
                            <td><c:out value="${accident.type.name}"/></td>
                            <td>
                                <c:forEach items="${accident.rules}" var="rule">
                                    <c:out value="${rule.name}"/><br>
                                </c:forEach>
                            </td>
                            <td><a href="<c:url value="/update?id=${accident.id}"/>">Редактировать инцидент</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="<c:url value="/create"/>">Добавить инцидент</a>
        </div>
    </body>
</html>
