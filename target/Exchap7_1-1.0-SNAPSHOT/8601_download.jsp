<%-- 
    Document   : 8601_download
    Created on : Sep 30, 2024, 4:06:24 PM
    Author     : aothu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head >
    <meta charset ="utf-8">
    <title>Murach's Java Servlets and JSP </title >
    <link rel="stylesheet" href="main.css" type="text/css"/>
</head >
<body>
    <h1>Downloads</h1>
    <p></p>
    <h2>86 (the band) - True Life Songs and Pictures</h2>
    <table class = "tablecart">
        <tr>
            <th>Song title </th>
            <th>Audio Format </th>
        </tr>
        <tr>
            <td >You Are a Star</td>
            <td >
            <a href ="/musicStore/sound/${productCode}/star.mp3">MP3 </a>
            </td>
        </tr>
        <tr>
            <td>Don't Make No Difference</td>
            <td>
            <a href ="/musicStore/sound/${productCode}/no_difference.mp3">MP3 </a>
            </td>
        </tr>
    </table>
    <p>
    <p><a href="download?action=viewAlbums">View list of albums</a></p>
    <p><a href="?action=viewCookies">View all cookies</a></p>
</body>
</html>
