<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>角色管理</title>
        <link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="/css/app.css">
        <script type="text/javascript" src="/easyui/jquery.min.js"></script>
        <script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#showList').datagrid({
                    url: '/role/list',
                    method : 'post',
                    pageSize : 10,//每页显示的记录条数，默认为10
                    pageList: [10,20,30,40],//可以设置每页记录条数的列表
                    fitColumns : true,
                    pagination : true,//分页控件
                    fit : true,//自动大小
                    idField : 'id',
                    rownumbers : true,//行号
                    singleSelect : true,//是否单选
                    columns : [ [ {
                        field : 'name',
                        align : 'center',
                        title : '角色标记',
                        width : 160
                    },{
                        field : 'notes',
                        title : '角色名称',
                        align : 'center',
                        width : 160
                    },{
                        field : 'operate',
                        title : '操作',
                        align : 'center',
                        width : 110,
                        formatter : function(value, row) {
                            var html = "<span style='color:blue'>";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doEdit('" + row.fdid + "')\" >修改</a>&nbsp;";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doDel('" + row.fdid + "')\" >删除</a>&nbsp;";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doResource('" + row.fdid + "')\" >分配资源</a>&nbsp;";
                            html += "</span>";
                            return html;
                        }
                    }] ],
                    toolbar 	: [{
                        text : "新增",
                        iconCls : 'icon-add',
                        handler : function(){
                            add();
                        }
                    }]
                });
                $("#searchBtn").click(function(){
                    var eName = $("#name").val();
                    var oCode = $("#notes").val();
                    $('#showList').datagrid("options").queryParams={
                        name:eName,
                        notes:oCode
                    };
                    $('#showList').datagrid("load");
                });
                $("#clearBtn").click(function(){
                    $("#name").textbox("clear");
                    $("#notes").textbox("clear");
                });
            });

            function doEdit(id){
                displayDialog("修改角色", "icon-edit", "/role/edit?fdid="+id);
            }

            function doDel(id) {
                var url = "/role/del?id=" + id;
                parent.$.messager.confirm("系统提示", "确定删除角色信息吗？", function(v, h, f) {
                    if (v) {
                        $.post(url, {}, function (res) {
                            if (res.indexOf("true")>-1) {
                                $("#showList").datagrid("reload");
                            } else if (res.indexOf("relation")>-1) {
                                $.messager.alert("系统提示", res.msg);
                            } else {
                                $.messager.alert("系统提示", res.msg);
                            }
                        });
                    }
                });
            }
            
            function add() {
                displayDialog("新增角色", "icon-add", "/role/add");
            }

            //修改密码
            function displayDialog (title, icon, url) {
                var form;
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: title,
                    iconCls: icon,
                    height: 200,
                    width: 500,
                    href: url,
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        //窗口表单加载成功时执行
                        form = $("#role_info_form", dialog);
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            if (form.form('validate')) {
                                $.post("/role/save", form.serialize(), function (res) {
                                    if (res.indexOf("true")>-1) {
                                        parent.$("#viewTabs").tabs("getSelected").find("iframe")[0].contentWindow.$("#showList").datagrid("reload");
                                        dialog.dialog('close');
                                    } else {
                                        $.messager.alert("系统提示", res.message);
                                    }
                                });
                            }
                        }
                    },{
                        iconCls: 'icon-cancel',
                        text: '取消',
                        handler: function () {
                            dialog.dialog('close');
                        }
                    }]
                });
            }

            function doResource(id) {
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: "选择资源",
                    iconCls: 'icon-edit',
                    height: 600,
                    width: 300,
                    href: '/role/resource',
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        //窗口表单加载成功时执行
                        parent.$('#tt').tree({
                            url: '/resource/treeT?roleId='+id,
                            animate: true,
                            checkbox: true,
                            onlyLeafCheck: true
                        });
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            var nodes = parent.$('#tt').tree("getChecked");
                            if (nodes) {
                                var reIds = "";
                                for (i=0; i<nodes.length; i++) {
                                    reIds += nodes[i].id+",";
                                }
                                var data = {roleId:id, reIds:reIds};
                                $.post("/role/resource/save", data, function (res) {
                                    if (res.indexOf("true") > -1) {
                                        dialog.dialog('close');
                                    } else {
                                        $.messager.alert("系统提示", res.msg);
                                    }
                                });
                            }
                        }
                    }]
                });
            }

        </script>
    </head>
    <body>
        <div class="easyui-panel p2" data-options="fit:true,border:false">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'north',split:true" title="查询条件" style="height:99px;" class="p2">
                    <form id="searchForm" method="post">
                        <input id="pageNumber" name="pageNumber" type="hidden">
                        <table id="info" class="edit_table" border="0" style="padding: 10px;">
                            <tbody>
                            <tr>
                                <th width="80px">角色标记：</th>
                                <td width="200px"><input id="name" type="text" class="easyui-textbox wd200">
                                <th width="80px">角色名称：</th>
                                <td width="200px"><input id="notes" type="text" class="easyui-textbox wd200"></td>
                                <td >
                                    <input id="searchBtn" value="查询" type="button"/>
                                    <input id="clearBtn" value="重置" type="button"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div data-options="region:'center'" title="角色列表">
                    <table id="showList"></table>
                </div>
            </div>
        </div>
    </body>
</html>
