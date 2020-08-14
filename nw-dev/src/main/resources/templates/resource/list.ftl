<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>用户管理</title>
        <link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="/css/app.css?v=6">
        <script type="text/javascript" src="/easyui/jquery.min.js"></script>
        <script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#showList').treegrid({
                    url: '/resource/tree',
                    method : 'post',
                    treeField: 'name',
                    fitColumns : true,
                    pagination : false,//分页控件
                    fit : true,//自动大小
                    idField : 'id',
                    singleSelect : true,//是否单选
                    columns : [[{
                        field : 'id',
                        title : '资源ID',
                        align : 'center',
                        width : 40
                    },{
                        field : 'name',
                        align : '',
                        title : '资源名称',
                        width : 40
                    },{
                        field : 'ordernum',
                        title : '节点次序',
                        align : 'center',
                        width : 40,
                        formatter:function(val)
                        { /* 调用函数显示时间 */

                            //return formattime(val);
                            return val;
                        }
                    },{
                        field : 'state',
                        title : '叶子节点',
                        align : 'center',
                        width : 40,
                        formatter:function(val,row) {
                            return row.state=='open'?'是':'否';
                        }
                    },{
                        field : 'url',
                        title : '资源URL',
                        align : 'center',
                        width : 40,
                        formatter:function(val,row){
                            //return inputStatusCodeToCn(row);
                            return val;
                        }
                    },{
                        field : 'operate',
                        title : '操作',
                        align : 'center',
                        width : 60,
                        formatter : function(value, row) {
                            var html = "<span style='color:blue'>";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doEdit('" + row.id + "','"+row.pid+"')\" >修改</a>&nbsp;";
                            html += "<a href=\"javascript:void(0);\" onclick=\"javascript:doDel('" + row.fdid + "','"+row.pid+"')\" >删除</a>";
                            html += "</span>";
                            return html;
                        }
                    }]],
                    toolbar 	: [{
                        text : "新增资源",
                        iconCls : 'icon-add',
                        handler : function(){
                            add();
                        }
                    }]
                });
                $("#searchBtn").click(function(){
                    var eName = $("#rid").val();
                    var oCode = $("#rname").val();
                    $('#showList').treegrid("options").queryParams={
                        search:"1",
                        id:eName,
                        name:oCode
                    };
                    $('#showList').treegrid("load");
                });
                $("#clearBtn").click(function(){
                    $("#rid").textbox("clear");
                    $("#rname").textbox("clear");
                });
            });

            function doDel(id, pid) {
                var url = "/resource/del?fdid=" + id;
                parent.$.messager.confirm("提示", "确定删除资源信息吗？",  function(v, h, f) {
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

            function doEdit(id, pid){
                displayDialog("修改资源", "icon-edit", "/resource/edit?id="+id, pid);
            }

            function add() {
                var row = $("#showList").treegrid("getSelected");
                var pid = '00';
                if(row) {
                    pid = row.id;
                }
                displayDialog("新增资源", "icon-add", "/resource/add?pid="+pid, pid);
            }

            //修改密码
            function displayDialog (title, icon, url, pid) {
                var form;
                var dialog = parent.$("<div/>", {class: 'noflow'}).dialog({
                    title: title,
                    iconCls: icon,
                    height: 400,
                    width: 600,
                    href: url,
                    modal: true,
                    onClose: function () {
                        dialog.dialog("destroy");
                    },
                    onLoad: function () {
                        //窗口表单加载成功时执行
                        form = $("#resource_info_form", dialog);
                    },
                    buttons: [{
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: function () {
                            if (form.form('validate')) {
                                $.post("/resource/save", form.serialize(), function (res) {
                                    if (res.indexOf("true")>-1) {
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
                            <th width="80px;">资源ID：</th>
                            <td width="200px;"><input id="rid" type="text" class="easyui-textbox wd200"></td>
                            <th width="80px;">资源名称：</th>
                            <td width="200px;"><input id="rname" type="text" class="easyui-textbox wd200"></td>
                            <td >
                                <input id="searchBtn" value="查询" type="button"/>
                                <input id="clearBtn" value=重置 type="button"/>
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