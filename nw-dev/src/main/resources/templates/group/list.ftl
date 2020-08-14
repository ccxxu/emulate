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
                    url: '/group/list',
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
                        field : 'id',
                        align : 'center',
                        title : '用户组ID',
                        width : 60
                    },{
                        field : 'name',
                        align : 'center',
                        title : '用户组名称',
                        width : 100
                    },{
                        field : 'descc',
                        title : '说明',
                        align : 'center',
                        width : 160
                    },{
                        field : 'operate',
                        title : '操作',
                        align : 'center',
                        width : 80,
                        formatter : function(value, row) {
                            var html = "<span style='color:blue'>";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doEdit('" + row.fdid + "')\" >修改</a>&nbsp;";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doDel('" + row.fdid + "')\" >删除</a>&nbsp;";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doRole('" + row.fdid + "')\" >角色</a>";
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
                displayDialog("修改用户组信息", "icon-edit", "/group/edit?fdid="+id);
            }

            function doDel(id) {
                var url = "/group/del?id=" + id;
                parent.$.messager.confirm("系统提示", "确定删除用户组信息吗？", function(v, h, f) {
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
                displayDialog("新增用户组信息", "icon-add", "/group/add");
            }

            //修改密码
            function displayDialog (title, icon, url) {
                var form;
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: title,
                    iconCls: icon,
                    height: 250,
                    width: 500,
                    href: url,
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        //窗口表单加载成功时执行
                        form = $("#group_info_form", dialog);
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            if (form.form('validate')) {
                                $.post("/group/save", form.serialize(), function (res) {
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

            function doRole(id) {
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: "角色列表",
                    iconCls: "icon-edit",
                    height: 400,
                    width: 600,
                    href: "/group/role",
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        parent.$('#roleList').datagrid({
                            url: '/group/role?groupId='+id,
                            method : 'post',
                            fitColumns : true,
                            fit : true,//自动大小
                            idField : 'id',
                            rownumbers : true,//行号
                            singleSelect : false,//是否单选
                            onLoadSuccess : function(data) {
                                if (data.rows.length > 0) {
                                    for (kk=0;kk<data.rows.length;kk++) {
                                        var rowIndex = parent.$('#roleList').datagrid("getRowIndex", data.rows[kk]);
                                        var che = data.rows[kk].checked;
                                        console.log("index["+kk+"]==" + che);
                                        if(che) {
                                            parent.$('#roleList').datagrid("selectRow", rowIndex);
                                        }
                                    }
                                }
                            },
                            columns : [ [ {
                                field : 'ck',
                                align : 'center',
                                checkbox : true,
                                width : 30
                            },{
                                field : 'name',
                                align : 'center',
                                title : '角色标记',
                                width : 160
                            },{
                                field : 'notes',
                                title : '角色名称',
                                align : 'center',
                                width : 160
                            }] ]
                        });
                        parent.$("#searchBtn").click(function(){
                            var eName = parent.$("#notes").val();
                            parent.$('#roleList').datagrid("options").queryParams={
                                notes:eName
                            };
                            parent.$('#roleList').datagrid("load");
                        });
                        parent.$("#clearBtn").click(function(){
                            parent.$("#notes").textbox("clear");
                        });
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            var rows = parent.$('#roleList').datagrid("getSelections");
                            var roles = "";
                            for (i=0; i<rows.length; i++) {
                                roles += rows[i].fdid+",";
                            }
                            var data = {groupId:id,roles:roles};
                            $.post("/group/role/save", data, function (res) {
                                if (res.indexOf("true")>-1) {
                                    dialog.dialog('close');
                                } else {
                                    $.messager.alert("系统提示", res.msg);
                                }
                            });
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
                                <th width="100px">用户组名称：</th>
                                <td width="200px"><input id="name" type="text" class="easyui-textbox wd200">
                                <th width="80px">说明：</th>
                                <td width="200px"><input id="notes" type="text" class="easyui-textbox wd200"></td>
                                <td >
                                    <input id="searchBtn" value="查询"  type="button"/>
                                    <input id="clearBtn" value="重置" type="button"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div data-options="region:'center'" title="用户组列表">
                    <table id="showList"></table>
                </div>
            </div>
        </div>
    </body>
</html>
