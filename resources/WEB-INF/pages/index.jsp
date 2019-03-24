<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <table border="1">
        <caption style="font-size: larger; font-weight: bold">List photos</caption>

        <tr>
            <th></th>
            <th>Photo ID</th>
            <th>File name</th>
            <th>Small photo</th>
        </tr>

        <c:forEach items="${photos}" var="photo">
            <tr>
                <td>
                    <input type="checkbox" class="chbox" value="${photo.getId()}"/>
                </td>
                <td>
                    <c:out value="${photo.getId()}"/>
                </td>
                <td>
                    <a href="/viewphoto/${photo.getId()}"/>
                    <c:out value="${photo.getFileName()}"/>
                    </a>
                </td>
                <td align="center">
                    <img src="/photo/${photo.getId()}" height="50"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>

    <input type="submit" class="btn" value="Delete Selected Photos" onclick="sentDel()"/>
    <br/><br/>
    <input type="submit" value="Update Page" onclick="window.location='/';"/>
    <br/><br/>
    <form action="/add_photo" enctype="multipart/form-data" method="POST">
        Add photo: <input type="file" name="photo">
        <input type="submit"/>
    </form>
</div>

<script type="text/javascript">
    function sentDel() {
        var chboxes = document.getElementsByClassName('chbox');
        var ids = [];
        for (var i = 0; i < chboxes.length; i++) {
            if (chboxes[i].checked) {
                ids.push(chboxes[i].getAttribute('value'));
            }
        }

        window.location = '/delete/' + ids.toString();
    }
</script>

</body>
</html>
