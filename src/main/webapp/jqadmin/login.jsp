<%@ page language="java"  contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商城后台管理系统</title>
<link href="main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body{background:#fff;margin:0;padding:0;color:#333;}
h1{float:left;width:410px;margin:20px;display:inline;}
h1 img{float:left;}
h1 span{float:right;width:290px;height:47px;background:#ebebeb;font:22px/46px "黑体";text-indent:20px;}
.login-body{margin-top:50px;height:392px;width:100%;background:url(images/lor_bg.gif) repeat-x;}
.login-con{width:450px;height:392px;background:url(images/login_bg.gif) no-repeat;margin:0 auto;}
.login{float:right;width:290px;margin-right:20px;display:inline}
.login li{float:left;width:100%;margin-bottom:20px;}
.login label{float:left;width:100%;font-size:14px;margin-bottom:3px;}
.login input{float:left;padding:4px 2px;margin:0}
.submit{float:left;border:none;width:70px; height:28px;background:transparent url(images/lg_buttom.gif) no-repeat;cursor: pointer;font-size:14px;color:#fff;font-weight:bold;}
-->
</style>
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script>
        function check() {
            var  name=document.getElementById("username").value;
            var  pwd=document.getElementById("password").value;
            var xmlhttp;
            if (window.XMLHttpRequest)
            {
                // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp=new XMLHttpRequest();
            }
            else
            {
                // IE6, IE5 浏览器执行代码
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange=function()
            {
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                   var result=xmlhttp.responseText;
                   //获取返回信息的status 0 成功 1 失败
                    var status=result.charAt(result.indexOf(",")-1);
                    if(status==1){
                        var alertStr="{"+result.substring(result.indexOf(",")+1);
                        var alertJson=JSON.parse(alertStr);
                        alert(alertJson.msg);
                    }else if(status==0){
                        alert("准备跳转")
                        // $.post("index.htm")
                        // window.close();
                        window.location="/jqadmin/index.htm";
                    }


                }
            }


            xmlhttp.open("POST","../user/login.do",true);
            xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");

            xmlhttp.send("userName="+name+"&"+"passWord="+pwd);
        }
        // $(function () {
        //     $("#username").blur(function () {
        //         var name=$("#username").value;
        //         $.post("../user/login.do",name,function (data,status) {
        //             var nam=data;
        //             alert("数据: " + nam + "\n状态: " + status);
        //         });
        //     });
        // });

    </script>
</head>
<body>
<div class="login-body">
<div class="login-con">
<h1><span>数据管理后台</span></h1>
<div class="login">
 <form action="../user/adminLogin.do" method="post" name="form1">
     <table>
         <tbody>
         <tr>
             <td><label for="username">用户名：</label></td>
             <td><input id="username" type="text" class="text" name="userid"  style="_width:208px;"/></td>
         </tr>
         <tr>
             <td><label for="password">密   码：</label></td>
             <td><input id="password" type="password" class="text" name="pwd"  style="_width:208px;"/></td>
         </tr>
         <tr>
             <td><label>验证码：</label></td>
             <td><input type="text" class="text" style="width: 50px;margin-right:5px; text-transform: uppercase;" id="vdcode" name="validate"/>
                 <img id="vdimgck" src="../include/vdimgck.php" alt="看不清？点击更换" align="absmiddle" style="cursor:pointer" onClick="this.src=this.src+'?'" /></td>
         </tr>
         <tr>
             <td>
                 <input type="button" onclick="check()" class="submit" value="登录"  name="sm1"/>
             </td>
         </tr>
         <p id="myDiv"></p>
         </tbody>
     </table>










 </form>  
</div>
            
</div>
</div>
</body>
</html>
