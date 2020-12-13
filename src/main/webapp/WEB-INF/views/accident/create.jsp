<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        function validate() {
            var s = '';
            var name = $('#name').val();
            var text = $('#text').val();
            var address = $('#address').val();
            var type = $('#type').val();
            var rules = $('#rIds').val();
            if (name == '') {
                s = s + ' ' + $('#nameLabel')[0].innerText;
            }
            if (text == '') {
                s = s + ' ' + $('#textLabel')[0].innerText;
            }
            if (address == '') {
                s = s + ' ' + $('#addressLabel')[0].innerText;
            }
            if (type == '') {
                s = s + ' ' + $('#typeLabel')[0].innerText;
            }
            if (rules == '') {
                s = s + ' ' + $('#rulesLabel')[0].innerText;
            }
            if (name == '' || text == '' || address == '' || type == '' || rules == '') {
                alert('Заполните поля:' + s);
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<div class="container">
    <h2>Добавить инцидент</h2>
    <form action="/accident/save" method="post" onsubmit="return validate()">
        <div class="form-group">
            <label id="nameLabel" for="name">Название</label>
            <input type="text" class="form-control" id="name" placeholder="Введите название" name="name">
        </div>
        <div class="form-group">
            <label id="textLabel" for="text">Описание</label>
            <input type="text" class="form-control" id="text" placeholder="Добавьте описание" name="text">
        </div>
        <div class="form-group">
            <label id="addressLabel" for="address">Адрес</label>
            <input type="text" class="form-control" id="address" placeholder="Укажите адрес" name="address">
        </div>
        <div class="form-group">
            <label id="typeLabel" for="type">Тип</label>
            <select class="form-control" id="type" name="type.id">
                <c:forEach var="type" items="${types}" >
                    <option value="${type.id}">${type.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label id="rulesLabel" for="rIds">Статьи</label>
            <select class="form-control" id="rIds" name="rIds" multiple>
                <c:forEach var="rule" items="${rules}" >
                    <option value="${rule.id}">${rule.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-default">Добавить</button>
    </form>
</div>

</body>
</html>
