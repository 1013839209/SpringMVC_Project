<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/3/19
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#testJson").click(function () {
                //通过ajax请求SpringMVC
                $.post(
                    "handler/testJson",//服务器地址
                    // {"name":"zs","age":23}
                    function ( result ) {//服务器处理完毕后的回调函数List<Student> students,加上@ResponseBody后，students实质就是一个Json数组的格式
                        // eval（result）;
                        for( var i=0; i<result.length; i++){
                            alert(result[i].id+"--"+result[i].name+"--"+result[i].age)
                        }
                    }
                )
            });
        });
    </script>
</head>
<body>

<input type="button" value="testJson" id="testJson">

        <a href="/handler/welcome3/dssfdc/test">first springmvc - welcome333</a>
        <a href="/handler/welcome4/add/sdd/fggh/test">first springmvc - welcome444</a>
        <a href="/handler/welcome5/a9c/test">first springmvc - welcome555</a>
        <a href="/handler/welcome6/zs">first springmvc - welcome666</a>

        <a href="handler/welcome2">first springmvc - welcome22</a>
        <form action="/handler/welcome" method="post">
            name:<input type="text" name="name2">
            age:<input type="text" name="age">
            height: <input type="text" name="height">
            <input type="submit" value="post">
        </form>

        <form action="/handler/testRest/1234" method="post">
            <input type="submit" value="增加">
        </form>
        <form action="/handler/testRest/1234" method="post">
            <input type="hidden" name="_method" value="DELETE">
            <input type="submit" value="删">
        </form>
        <form action="/handler/testRest/1234" method="post">
            <input type="hidden" name="_method" value="PUT">
            <input type="submit" value="改">
        </form>
        <form action="/handler/testRest/1234" method="get">
            <input type="submit" value="查">
        </form>

-----------------------------------------<br/>
    <form action="/handler/testParam" >
        <input type="text" name="name">
        <input type="text" name="age">
        <input type="submit" value="提交">
    </form>

        <a href="/handler/requestHeader">请求头信息</a>
        <a href="/handler/testCookieValue">获取Cookie值(JSESSIONID)</a>

        <form action="/handler/testObjectProperties" method="post">
            id:<input type="text" name="id"><br>
            name:<input type="text" name="name"><br>
            homeAddress:<input type="text" name="address.homeAddress"><br>
            schoolAddress:<input type="text" name="address.schoolAddress"><br>
            <input type="submit" value="提交">
        </form>
        <a href="/handler/testServletAPI">原生态的Servlet-API</a><br>
        <a href="/handler/testModelAndView">testModelAndView</a><br>
        <a href="/handler/testModelMap">testModelMap</a><br>
        <a href="/handler/testMap">testMap</a><br>
        <a href="/handler/testModel">testModel</a><br>

        <form action="/handler/testModelAttribute" method="post">
            id:<input type="hidden" name="id" value="23"><br>
            <%--name:<input type="text" name="name"><br>--%>
            <input type="submit" value="提交">
        </form>

        <a href="/handler/testI18n">testI18n</a><br>
        <a href="/handler/testMvcViewController">testMvcViewController</a><br>

        <form action="/handler/testConverter" method="post">
            学生信息:<input type="text" name="studentInfo"><br>
            <input type="submit" value="提交">
        </form>

        <form action="/handler/testDateTimeFormat">
            id:<input type="hidden" name="id" value="23"><br>
            name:<input type="text" name="name"><br>
            birthday:<<input type="text" name="birthday">
            email:<input type="email" name="email">
            <input type="submit" value="提交">
        </form>

        <form action="/handler/testUpload" method="post" enctype="multipart/form-data">
            id:<input type="file" name="file" ><br>
            描述:<input type="text" name="desc">
            <input type="submit" value="提交">
        </form>

        <a href="/handler/testInterceptor">testInterceptor</a>
</body>
</html>
