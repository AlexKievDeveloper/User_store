<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Add user</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://localhost:8080/css/style.css">
</head>
<body class="body">
<div class="container">
    <div class="row">
        <div class="col-lg-7 m-auto">
            <div class="header">
                <h1 class="h1"> Add user</h1>
            </div>
            <div class="form">
                <form action="/users/add" method="POST">
                    <label for="name">Name
                        <input type="text" name="firstName" id="name" placeholder="Enter your name" class="input"
                               required>
                    </label>
                    <label for="surname">Surname
                        <input type="text" name="secondName" id="surname" placeholder="Enter your surname"
                               class="input" required>
                    </label>
                    <label for="salary"> Salary
                        <input type="text" name="salary" id="salary"
                               placeholder="Enter your salary. Use separator: ' . '" class="input" required>
                    </label>
                    <label for="date_of_birth"> Date of birth
                        <input type="text" name="dateOfBirth" id="date_of_birth"
                               placeholder="Enter your date of birth, format example: 1993-08-24" class="input"
                               required>
                    </label>
                    <div>
                        <input type="submit" value="SUBMIT" class="btn">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

