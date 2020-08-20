<%@ page import="com.nexacro17.xapi.data.DataTypes"%>
<%@ page import="com.nexacro17.xapi.tx.PlatformType"%>
<%@ page import="com.nexacro17.xapi.tx.HttpPlatformResponse"%>
<%@ page import="com.nexacro17.xapi.data.PlatformData"%>
<%@ page import="com.nexacro17.xapi.data.DataSet"%>
<%@ page import="java.util.ArrayList"%>
<%-- <%@ page import="vo.DeptVO"%> --%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%--
	List<DeptVO> list = new ArrayList<>();
	DeptVO vo = new DeptVO();
	vo.setDeptno(10);
	vo.setDname("총무무");
	vo.setLoc("서울");
	list.add(vo);
	DeptVO vo1 = new DeptVO();
	vo1.setDeptno(20);
	vo1.setDname("개발부");
	vo1.setLoc("인천");
	list.add(vo1);
	DeptVO vo2 = new DeptVO();
	vo2.setDeptno(30);
	vo2.setDname("기획부");
	vo2.setLoc("부산");
	list.add(vo2);
	
	DataSet ds = new DataSet("ar");
	ds.addColumn("deptno", DataTypes.INT,10);
	ds.addColumn("dname", DataTypes.STRING,20);
	ds.addColumn("loc", DataTypes.STRING,20);
	
	for(DeptVO e : list){
		int row = ds.newRow();
		ds.set(row, "deptno", e.getDeptno());
		ds.set(row, "dname", e.getDname());
		ds.set(row, "loc", e.getLoc());
	}
	request.setAttribute("ds", ds);
--%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	DataSet ds1 = (DataSet)request.getAttribute("ds");
	System.out.println("DS :" + ds1.getRowCount());
	PlatformData pData = new PlatformData();
	pData.addDataSet(ds1);
	HttpPlatformResponse res = 
			new HttpPlatformResponse(response,PlatformType.CONTENT_TYPE_XML,"utf-8");
	out.clear();
	out = pageContext.pushBody();
	res.setData(pData);
	res.sendData();
%>
</body>
</html>