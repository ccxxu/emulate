<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>用户管理</title>
        <link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="/css/app.css?v=9">
        <script type="text/javascript" src="/easyui/jquery.min.js"></script>
        <script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#showList').treegrid({
                    url: '/user/tree',
                    method : 'post',
                    treeField: 'abbrName',
                    fitColumns : true,
                    pagination : false,//分页控件
                    fit : true,//自动大小
                    idField : 'id',
                    singleSelect : true,//是否单选
                    columns : [[{
                        field : 'userId',
                        title : '用户ID',
                        align : 'center',
                        width : 80
                    },{
                        field : 'abbrName',
                        align : '',
                        title : '单位/人员',
                        width : 80
                    },{
                        field : 'aliasName',
                        title : '职务',
                        align : 'center',
                        width : 80
                    },{
                        field : 'objName',
                        title : '用户类型',
                        align : 'center',
                        width : 80
                    },{
                        field : 'useremail',
                        title : '内网邮箱',
                        align : 'center',
                        width : 80,
                        formatter:function(val,row){
                            //return inputStatusCodeToCn(row);
                            return val;
                        }
                    },{
                        field : 'displayOrder',
                        title : '次序',
                        align : 'center',
                        width : 80
                    },{
                        field : 'operate',
                        title : '操作',
                        align : 'center',
                        width : 110,
                        formatter : function(value, row) {
                            var html = "<span style='color:blue'>";
                            if (row.objType==1) {
                                html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doEdit2('" + row.userId + "','" + row.superId + "')\" >修改</a>&nbsp;";
                            } else {
                                html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doEdit('" + row.userId + "','" + row.superId + "')\" >修改</a>&nbsp;";
                            }
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doDel('" + row.userId + "','" + row.superId + "')\" >删除</a>&nbsp;";
                            if (row.objType==1) {
                                html += "<a href=\"javascript:void(0);\" onclick=\"javascript:resetPwd('" + row.userId + "')\" >重置密码</a>&nbsp;";
                            }
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:modifyDept('" + row.userId + "','" + row.superId + "')\" >变更部门</a>&nbsp;";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doGroup('" + row.userId + "')\" >用户组</a>&nbsp;";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doRole('" + row.userId + "')\" >角色</a>";
                            html += "</span>";
                            return html;
                        }
                    }]],
                    toolbar 	: [{
                        text : "新增单位",
                        iconCls : 'icon-add',
                        handler : function(){
                            add();
                        }
                    },{
                        text : "新增人员",
                        iconCls : 'icon-add',
                        handler : function(){
                            add2();
                        }
                    }]
                });
                $("#searchBtn").click(function(){
                    var abbrName = $("#an").val();
                    var loginName = $("#ln").val();
                    var useremail = $("#ue").val();
                    $('#showList').treegrid("options").queryParams={
                        search : "1",
                        abbrName : abbrName,
                        loginName : loginName,
                        useremail : useremail
                    };
                    $('#showList').treegrid("load");
                });
                $("#clearBtn").click(function(){
                    $("#an").textbox("clear");
                    $("#ln").textbox("clear");
                    $("#ue").textbox("clear");
                });
            });

            function doEdit(id, pid){
                displayDialog("修改单位", "icon-edit", '/user/edit?id='+id, pid);
            }

            function doEdit2(id, pid){
                displayDialog("修改人员", "icon-edit", '/user/edit2?id='+id, pid);
            }

            function doDel(id, pid) {
                var url = "/user/del?userId=" + id;
                parent.$.messager.confirm("提示", "确定删除用户/单位信息吗？",  function(v, h, f) {
                    if (v) {
                        $.post(url, {}, function (res) {
                            if (res.indexOf("true")>-1) {
                                $("#showList").treegrid("reload", pid);
                            } else if (res.indexOf("relation")>-1) {
                                $.messager.alert("系统提示", res.msg);
                            } else {
                                $.messager.alert("系统提示", res.msg);
                            }
                        });
                    }
                });
            }

            function resetPwd(id) {
                var url = "/user/reset?userId=" + id;
                parent.$.messager.confirm("提示", "密码重置后将变为a00000000000,您确定重置密码吗？",  function(v, h, f) {
                    if (v) {
                        $.post(url, {}, function (res) {
                            if (res.indexOf("true")>-1) {

                            } else {
                                $.messager.alert("系统提示", res.msg);
                            }
                        });
                    }
                });
            }
            
            function modifyDept(id, pid) {
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: "选择单位/部门",
                    iconCls: 'icon-edit',
                    height: 600,
                    width: 300,
                    href: '/user/tree',
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        //窗口表单加载成功时执行
                        parent.$('#tt').tree({
                            url: '/user/treeT',
                            animate: true

                        });
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            var node = parent.$('#tt').tree("getSelected");
                            if (node) {
                                var data = {userId:id, superId:node.id};
                                $.post("/user/dept/save", data, function (res) {
                                    if (res.indexOf("true") > -1) {
                                        parent.$("#viewTabs").tabs("getSelected").find("iframe")[0].contentWindow.$("#showList").treegrid("reload", pid);
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

            function add() {
                var row = $("#showList").treegrid("getSelected");
                var pid = 1;
                if(row) {
                    pid = row.id;
                }
                displayDialog("新增单位", "icon-add", '/user/add?pid='+pid, pid);
            }

            function add2() {
                var row = $("#showList").treegrid("getSelected");
                var pid = 1;
                if(row) {
                    pid = row.id;
                }
                displayDialog("新增人员", "icon-add", '/user/add2?pid='+pid, pid);
            }

            function displayDialog (title, icon, url, pid) {
                var form;
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: title,
                    iconCls: icon,
                    height: 500,
                    width: 600,
                    href: url,
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        //窗口表单加载成功时执行
                        form = $("#user_info_form", dialog);
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            if (form.form('validate')) {
                                $.post("/user/save", form.serialize(), function (res) {
                                    if (res.indexOf("true")>-1) {
                                        dialog.dialog('close');
                                        if (pid=='1') {
                                            parent.$("#viewTabs").tabs("getSelected").find("iframe")[0].contentWindow.$("#showList").treegrid("reload");
                                        } else {
                                            parent.$("#viewTabs").tabs("getSelected").find("iframe")[0].contentWindow.$("#showList").treegrid("reload", pid);
                                        }
                                    } else {
                                        $.messager.alert("系统提示", res.msg);
                                    }
                                });
                            }
                        }
                    }]
                });
            }

            function doGroup(id) {
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: "用户组列表",
                    iconCls: "icon-edit",
                    height: 400,
                    width: 600,
                    href: "/user/group",
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        parent.$('#groupList').datagrid({
                            url: '/user/group?userId='+id,
                            method : 'post',
                            fitColumns : true,
                            fit : true,//自动大小
                            idField : 'id',
                            rownumbers : true,//行号
                            singleSelect : false,//是否单选
                            onLoadSuccess : function(data) {
                                if (data.rows.length > 0) {
                                    for (kk=0;kk<data.rows.length;kk++) {
                                        var rowIndex = parent.$('#groupList').datagrid("getRowIndex", data.rows[kk]);
                                        var che = data.rows[kk].checked;
                                        console.log("index["+kk+"]==" + che);
                                        if(che) {
                                            parent.$('#groupList').datagrid("selectRow", rowIndex);
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
                                field : 'id',
                                align : 'center',
                                title : '用户组ID',
                                width : 60
                            },{
                                field : 'name',
                                align : 'center',
                                title : '用户组名称',
                                width : 100
                            }] ]
                        });
                        parent.$("#searchBtn").click(function(){
                            var eName = parent.$("#name").val();
                            var oCode = parent.$("#notes").val();
                            parent.$('#groupList').datagrid("options").queryParams={
                                name:eName,
                                notes:oCode
                            };
                            parent.$('#groupList').datagrid("load");
                        });
                        parent.$("#clearBtn").click(function(){
                            parent.$("#name").textbox("clear");
                            parent.$("#notes").textbox("clear");
                        });
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            var rows = parent.$('#groupList').datagrid("getSelections");
                            var groups = "";
                            for (i=0; i<rows.length; i++) {
                                groups += rows[i].fdid+",";
                            }
                            var data = {userId:id,groups:groups};
                            $.post("/user/group/save", data, function (res) {
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

            function doRole(id) {
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: "角色列表",
                    iconCls: "icon-edit",
                    height: 400,
                    width: 600,
                    href: "/user/role",
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        parent.$('#roleList').datagrid({
                            url: '/user/role?userId='+id,
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
                            var data = {userId:id,roles:roles};
                            $.post("/user/role/save", data, function (res) {
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
                                <th width="80px">名称：</th>
                                <td width="200px"><input id="an" type="text" class="easyui-textbox wd200"></td>
                                <th width="80px">用户名：</th>
                                <td width="200px"><input id="ln" type="text" class="easyui-textbox wd200"></td>
                                <th width="80px">内网邮箱：</th>
                                <td width="200px"><input id="ue" type="text" class="easyui-textbox wd200"></td>
                                <td >
                                    <input id="searchBtn" value="查询" type="button"/>
                                    <input id="clearBtn" value="重置" type="button"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div data-options="region:'center'">
                    <table id="showList" class="easyui-treegrid"></table>
                </div>
            </div>
        </div>
    </body>
</html>
