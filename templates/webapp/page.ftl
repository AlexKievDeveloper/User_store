<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <title>Table users</title>
    <style>
        body {
            background: yellow;
        }

        .form__block > div {
            background: yellow;
            float: left;
            width: 50%;
        }

        .form__block > div:last-child {
            margin-right: 0;
        }

        .add_user_form {
            margin-left: 130px;
        }

    </style>
</head>

<body>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Id</th>
        <th scope="col">First name</th>
        <th scope="col">Second name</th>
        <th scope="col">Salary</th>
        <th scope="col">Date of birth</th>
        <th scope="col">
            <div class="add_user_form">
                <form action="/users/add" method="GET">
                    <button class="form__btn">
                        <div class="add">
                            <img src="add.jpg" alt="Add" style="vertical-align:middle">
                        </div>
                    </button>
                </form>
            </div>
        </th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.secondName}</td>
            <td>${user.salary}</td>
            <td>${user.dateOfBirth}</td>
            <td>
                <div class="form__block">
                    <div class="form">
                        <form action="/users/edit" method="GET">
                            <button class="form__btn">
                                <div class="img">
                                    <input type="hidden" name="id" value=${user.id}>
                                    <img src="edit.jpg" alt="Edit" style="vertical-align:middle">
                                </div>
                            </button>
                        </form>
                    </div>
                    <div class="form">
                        <form action="/users/remove" method="POST">
                            <button class="form__btn">
                                <div class="img">
                                    <input type="hidden" name="id" value=${user.id}>
                                    <img src="remove.jpg" alt="Remove" style="vertical-align:middle">
                                </div>
                            </button>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>

