<%--
  Created by IntelliJ IDEA.
  User: czhtbq
  Date: 2017/4/6
  Time: 下午9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="${ctx}/import/coreTaglib.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>急诊区奖金统计</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jeasyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jeasyui/themes/icon.css">
    <script type="text/javascript" src="${ctx}/static/jeasyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jeasyui/jquery.easyui.min.js"></script>
</head>
<body>
<div style="margin:10px 5px 15px;"></div>
<h2>急诊区奖金</h2>
<div class="easyui-layout" data-options="fit:true" >
    <div data-options="region:'north',split:true" style="height:200px">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'west',split:true" title="总数" style="width:300px;">

            </div>
            <div data-options="region:'center',split:true" title="说明" >
                <h6>一、科室平均奖按计算平均奖</h6>
                <li>岗位分数：80（N5）、70(N4)、60(N3)、50(N2)、40(N1)、30(轮科N1),N4≤2年 65分；N3≥10年为60；≤5-10年55；N2≤3年40；4-5年45；</li>
                <li>质量分数：按每月统计数据计算</li>
                <li>岗位分值：按餐费表计算(例如合同0.8，人事代理0.7）</li>


            </div>
        </div>
    </div>
    <%--<div data-options="region:'east',split:true" title="East" style="width:100px;"></div>--%>
    <%--<div data-options="region:'west',split:true" title="West" style="width:100px;"></div>--%>
    <div data-options="region:'center',title:'cn.mbean.Main Title',iconCls:'icon-ok'" style="margin: 0px 10px 0px;">
        <table class="easyui-datagrid"
               toolbar="#toolbar"
               data-options="url:'datagrid_data1.json',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true">
            <thead>
            <tr>
                <th data-options="field:'itemid'" width="80">Item ID</th>
                <th data-options="field:'productid'" width="100">Product ID</th>
                <th data-options="field:'listprice',align:'right'" width="80">List Price</th>
                <th data-options="field:'unitcost',align:'right'" width="80">Unit Cost</th>
                <th data-options="field:'attr1'" width="150">Attribute</th>
                <th data-options="field:'status',align:'center'" width="60">Status</th>
            </tr>
            </thead>
        </table>
        <div id="toolbar">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').edatagrid('addRow')">New</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">Destroy</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow')">Save</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dg').edatagrid('cancelRow')">Cancel</a>
        </div>
    </div>
</div>
</body>
</html>
