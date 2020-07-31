<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">

    <title>Table users</title>
</head>

<body class="page_body">
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Id</th>
        <th scope="col">First name</th>
        <th scope="col">Second name</th>
        <th scope="col">Salary</th>
        <th scope="col">Date of birth</th>
        <th scope="col">
            <div class="page_add_user_form">
                <form action="/users/add" method="GET">
                    <button>
                            <img class="add" src="img/add.jpg" alt="Add" style="vertical-align:middle">
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
            <td>${user.salary?string.@salary}</td>
            <td>${user.dateOfBirth}</td>
            <td>
                <div class="page__block">
                    <div>
                        <form action="/users/edit" method="GET">
                            <button>
                                    <input type="hidden" name="id" value=${user.id}>
                                    <img src="img/edit.jpg" alt="Edit" style="vertical-align:middle">
                            </button>
                        </form>
                    </div>
                    <div>
                        <form action="/users/remove" method="POST">
                            <button>
                                    <input type="hidden" name="id" value=${user.id}>
                                    <img src="img/remove.jpg" alt="Remove" style="vertical-align:middle">
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

