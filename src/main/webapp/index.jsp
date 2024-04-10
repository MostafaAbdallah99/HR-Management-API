<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SOAP AND REST</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            .link-label {
                display: inline-block;
                margin: 10px;
                padding: 10px;
                background-color: #4CAF50;
                color: white;
                text-decoration: none;
                text-align: center;
                border-radius: 8px;
                font-size: 20px;
            }
            .link-label:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <a class="link-label" href="rest">Go to REST endpoint</a>
        <a class="link-label" href="soap/jobs">Go to SOAP endpoint</a>
    </body>
</html>