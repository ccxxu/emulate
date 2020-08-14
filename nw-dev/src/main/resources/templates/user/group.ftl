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

        </script>
    </head>
    <body>
        <div class="easyui-panel p2" data-options="fit:true,border:false">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'north',split:true" style="height:69px;" class="p2">
                    <form id="searchForm" method="post">
                        <input id="pageNumber" name="pageNumber" type="hidden">
                        <table id="info" class="edit_table" border="0" style="padding: 10px;">
                            <tbody>
                            <tr>
                                <th width="100px">用户组名称：</th>
                                <td width="200px"><input id="name" type="text" class="easyui-textbox wd200">
                                <td >
                                    <input id="searchBtn" value="查询"  type="button"/>
                                    <input id="clearBtn" value="重置" type="button"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div data-options="region:'center'">
                    <table id="groupList"></table>
                </div>
            </div>
        </div>
    </body>
</html>