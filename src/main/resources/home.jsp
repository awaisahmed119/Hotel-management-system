<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" th:replace="~{fragments/layout :: layout (~{::body},'home')}">



<head>

    <title>Hotel Booking System</title>
</head>
<body>

<div class="container">
    <h1>ROOMS INFORMATION</h1>
    <label>ID</label> &nbsp; <label>Category</label> &nbsp; <label>Price</label> &nbsp;
    <ul th:each="record : ${rooms}">
        <li th:text="${rooms}"/>
    </ul>
</div>




<table class="table table-striped table-bordered">
    <thead>
    <tr>
        <th class="warning">Flight#</th>
        <th>ID </th>
        <th>Category</th>


    </tr>
    </thead>
    <tbody>

        <tr th:each="record : ${rooms}">
            <td th:text="${rooms}"> </td>
            <td th:text="${rooms}"> </td>

        </tr>

    </tbody>
</table>


<form action="/addroom">
    <button  type=submit  > Add New Rooms </button>
</form>
</body>
</html>