<%@ page language="java"  contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文档管理</title>
    <link href="/jqadmin/main.css" rel="stylesheet" type="text/css" />
    <script src="/js/jquery-3.3.1.min.js" language="javascript" type="text/javascript"></script>
</head>
<body>
<div class="mtitle">
  <h1>数据管理</h1>
  <span> 跳转:
    <select class="fs_12" name="123">
      <option>所有频道</option>
    </select>
  </span>
</div>
<div class="tform"><button type="button" class="btn1" onClick="location='/manage/category/getCategoryAndDeepChildrenCategorySort.do';" >添加数据</button>
    
<form name="form2">
  <table class="tlist" >
    <thead>
      <tr class="title">
          <th align="left">商品名称</th>
          <th align="left">商品类别</th>
          <th align="left">价格</th>
          <th align="left">上架状态</th>
          <th align="right">操作</th>
      </tr>  
    </thead>
<tbody>
<c:forEach items="${serverRsponse.data.list}" var="vo">
    <tr>
        <td><c:out value="${vo.name}"></c:out></td>
        <td><c:out value="${vo.categoryName}"></c:out></td>
        <td><c:out value="${vo.price}"></c:out></td>
        <td>
            <c:choose>
                <c:when test="${vo.status==1}">
                    <c:out value="上架"></c:out>
                </c:when>
                <c:when test="${vo.status==0}">
                    <c:out value="下架"></c:out>
                </c:when>
            </c:choose>
        </td>
        <td><a href="/manage/product/adminDetail.do?productId=${vo.id}">详情</a> </td>
        


    </tr>
</c:forEach>
</tbody>
</table>
<div class="pagelist">
    <span>共 <c:out value="${serverRsponse.data.pages}"></c:out>页/<c:out value="${serverRsponse.data.total}"></c:out>条记录 </span><span class="indexPage">
</span>
    <table>
        <tr>
            <td>
                <c:if test="${serverRsponse.data.pageNum>1 }">
                    <a href="adminList.do?pageNum=${serverRsponse.data.pageNum-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                </c:if>
            </td>
                 <c:forEach begin="1" step="1" end="${serverRsponse.data.pages}" var="item">
                <td>
                     <c:if test="${serverRsponse.data.pageNum==item }">
                         <a href="adminList.do?pageNum=${item }">${item}</a>
                     </c:if>
                     <c:if test="${serverRsponse.data.pageNum!=item }">
                         <a href="adminList.do?pageNum=${item }">${item}</a>
                     </c:if>
                </td>
                 </c:forEach>
            <td>
                <c:if test="${serverRsponse.data.pageNum<serverRsponse.data.pages}">
                    <a href="adminList.do?pageNum=${serverRsponse.data.pageNum+1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
                </c:if>
            </td>
            <td>
                <a class="endPage" href="adminList.do?pageNum=1">首页</a>
            </td>
            <td>
                <c:if test="${serverRsponse.data.hasPreviousPage == true}">
                    <a class="endPage" href="adminList.do?pageNum=${serverRsponse.data.prePage}">上页</a>
                </c:if>
                <c:if test="${serverRsponse.data.hasNextPage == true }">
                    <a class="nextPage" href="adminList.do?pageNum=${serverRsponse.data.nextPage}">下页</a>
                </c:if>
            </td>
            <td>
                <a class="endPage" href="adminList.do?pageNum=${serverRsponse.data.lastPage}">尾页</a>
            </td>
        </tr>
    </table>
</div>
</form>
<!--  搜索表单  -->
<table>

</table>
</body>
</html>