<%@ page language="java"  contentType="text/html; charset=UTF-8" %>
<html>
<body>
<h2>Hello wrld!</h2>

springmvc上55传文件
<form name="f1" action="/manage/product/upload.do"  method="post" enctype="multipart/form-data"> <!--上传文件这里选择enctype="multipart/form-data" -->
<input name="upload_file" type="file" />
    <input type="submit" value="SpringMVC上传文件"/>

</form>


富文本图片222222222222222323232323上传文件
<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本图片上传文件" />
</form>
</body>
</html>
