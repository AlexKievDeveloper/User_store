<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Edit user</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="http://localhost:8080/css/style.css">
</head>
<body class="body">
<div class="header">
    <h1 class="h1"> Edit user</h1>
</div>
<div>
    <div class="form">
        <form action="/users/edit" method="POST">
            <label for="name">Name
                <input type="text" name="firstName" id="name" value="${firstName}" class="input" required>
            </label>
            <label for="surname">Surname
                <input type="text" name="secondName" id="surname" value="${secondName}" class="input"
                       required>
            </label>
            <label for="salary"> Salary
                <input type="text" name="salary" id="salary" value="${salary?string.@salary}" class="input" required>
            </label>
            <label for="date_of_birth"> Date of birth
                <input type="text" name="dateOfBirth" id="date_of_birth" value="${dateOfBirth}" class="input"
                       required>
            </label>
            <div>
                <input type="submit" value="EDIT" class="btn">
                <input name="id" value=${id} type="hidden">
            </div>
        </form>
    </div>
</div>
</body>
</html>
