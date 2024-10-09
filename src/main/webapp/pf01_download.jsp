<%-- 
    Document   : pf01_download
    Created on : Sep 30, 2024, 4:43:59 PM
    Author     : aothu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="main.css" type="text/css"/>
</head>
<body>
    <h1>Downloads</h1>
    <p></p>
    <h2>Paddlefoot - The First CD</h2>
    <table class = "tablecart">
        <tr>
            <th>Song Title</th>
            <th>Audio Format</th>
        </tr>
        <tr>
            <td>First Song Title</td>
            <td>
                <a href="/musicStore/sound/pf01/first_song.mp3">MP3</a>
            </td>
        </tr>
        <tr>
            <td>Second Song Title</td>
            <td>
                <a href="/musicStore/sound/pf01/second_song.mp3">MP3</a>
            </td>
        </tr>
    </table>
    <p><a href="download?action=viewAlbums">View list of albums</a></p>
    <p><a href="?action=viewCookies">View all cookies</a></p>
</body>
</html>

