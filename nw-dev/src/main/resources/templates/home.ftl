<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>CA认证授权中心</title>
        <!-- 导入主题样式文件 -->
        <link rel="stylesheet" href="/easyui/themes/gray/easyui.css">
        <!-- 图标 -->
        <link rel="stylesheet" href="/easyui/themes/icon.css">
        <link rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
        <!-- 项目公共样式 -->
        <link rel="stylesheet" href="/css/app.css?v=98">

        <style type="text/css">
            .nav ul,li { list-style:none; font-size:14px; line-height:20px; margin:0; padding-left:6px; }
            .child { display:none; }
            .nav a { display:block; color:#5c84c1; padding-left:0px; }
            #viewTabs .tabs-panels>.panel>.panel-body {
                overflow: hidden;
            }

        </style>

        <!-- 第一脚本：jquery，必须是在第一位 -->
        <script src="/easyui/jquery.min.js" charset="utf-8"></script>
        <!-- easyui的核心 -->
        <script src="/easyui/jquery.easyui.min.js" charset="utf-8"></script>
        <!-- Easyui的扩展 -->
        <script src="/easyui/jquery.edatagrid.js" charset="utf-8"></script>
        <!-- Easyui的国际化 -->
        <script src="/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
        <!-- Easyui的bug修复包 -->
        <script src="/easyui/fixed.js" charset="utf-8"></script>
        <script src="/js/lib/xss.js" charset="utf-8"></script>

        <!--webSocket的支持-->
        <script src="/js/lib/sockjs.min.js" charset="utf-8"></script>

        <script src="/js/lib/vue.js" charset="utf-8"></script>
        <script src="/js/app.js?v=1" charset="utf-8"></script>

        <script language="javascript" type="text/javascript">
            $(document).ready(function(){
                //$(".nav ul li").children("ul").hide();
                $(".nav").find("li").not(":has(ul)").children("a").css({textDecoration:"none",color:"#333",background:"none"}).click(function(){
                    if ($(this).get(0) != "javascript:void(0)") {
                        $(this).get(0).location.href="'"+$(this).attr("href")+"'";
                    }
                });

                $(".nav").find("li:has(ul)").children("a").css({background:"url(images/statu_close.gif) no-repeat left top;"}).click(function(){
                    if($(this).next("ul").is(":hidden")){
                        $(this).next("ul").slideDown("slow");
                        if($(this).parent("li").siblings("li").children("ul").is(":visible")){
                            $(this).parent("li").siblings("li").find("ul").slideUp("1000");
                            $(this).parent("li").siblings("li:has(ul)").children("a").css({background:"url(images/statu_close.gif) no-repeat left top;"}).end().find("li:has(ul)").children("a").css({background:"url(images/statu_close.gif) no-repeat left top;"});
                        }
                        $(this).css({background:"url(images/statu_open.gif) no-repeat left top;"});
                        return false;
                    } else {
                        $(this).next("ul").slideUp("normal");
                        //不用toggle()的原因是为了在收缩菜单的时候同时也将该菜单的下级菜单以后的所有元素都隐藏
                        $(this).css({background:"url(images/statu_close.gif) no-repeat left top;"});
                        $(this).next("ul").children("li").find("ul").fadeOut("normal");
                        $(this).next("ul").find("li:has(ul)").children("a").css({background:"url(images/statu_close.gif) no-repeat left top;"});
                        return false;
                    }
                });
            });

            var MEMBER = {
                id:${s_member.id},
                realName: '${s_member.realName}',
                userName: '${s_member.username}'
            };

            function addTab(id, name, url,showScroll) {
                if(showScroll == undefined){
                    showScroll="no";
                }
                //var path="<%=request.getContextPath()%>";
                var path="";
                url = path + url;
                var tt = $("#viewTabs");
                if (tt.tabs('exists', name)) {
                    tt.tabs('close', name);
                    /* $("#iframe_"+id).attr("src",url);
                    tt.tabs('select', name); */
                }
                if(tt.find("ul.tabs li").length>9){
                    alert("您打开的选项卡过多，请先关闭一些。");
                    return false;
                }
                var content = "<iframe scrolling='"+showScroll+"' class='tabs_need_destroy_iframe' id='iframe_" + id
                        + "' frameborder=\"0\"  src='" + url
                        + "'  style=\"width:100%;height:100%\" border=\"0\" marginwidth=\"0\" marginheight=\"0\">";
                tt.tabs('add', {
                    title : name,
                    content : content,
                    closable : true
                });
            }

            function closeTab(name) {
                var tt = $('.easyui-tabs');
                if (tt.tabs('exists', name)) {
                    tt.tabs('close', name);
                }
            }
        </script>
        <!--
        <script src="/js/require.js" charset="utf-8" data-main="js/app" defer async="true"></script>
        -->
    </head>
    <body class="easyui-layout">
        <div data-options="region:'north'" style="height: 70px;overflow: hidden;padding: 0 10px;">
            <div class="user-info">
                <span class="item" id="public_change_info"><i class="fa fa-user"></i> ${s_member.realName}</span>
                <span class="item" id="public_change_password"><i class="fa fa-user"></i> 修改密码</span>
                <a class="item" href="/logout"><i class="fa fa-sign-out"></i> 注销</a>
            </div>
            <h1>CA认证授权中心</h1>
        </div>
        <div title="菜单" data-options="region:'west',iconCls:'fa fa-list'" style="width: 200px">
            <div class="easyui-accordion" data-options="fit:true,border:false">
            <#list menus as menu>
                <#if menu.pid == '00'>
                    <div class="nav" title="${menu.name}" data-options="iconCls:'fa fa-cogs'">
                        <ul>
                            <#list menus as child>
                                <#if child.pid?? && child.pid == menu.id>
                                    <!--
                                    <li ><a href="${child.url}">${child.name}</a>
                                    -->
                                    <li ><a href="javascript:void(0)" onclick="addTab('${child.id}','${child.name}','${child.url}')">${child.name}</a>
                                        <ul class="child">
                                            <#list menus as child2>
                                                <#if child2.pid?? && child2.pid == child.id>
                                                    <li ><a href="javascript:void(0)" onclick="addTab('${child2.id}','${child2.name}','${child2.url}')">${child2.name}</a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </li>
                                </#if>
                            </#list>
                        </ul>
                    </div>
                </#if>
            </#list>
            </div>
        </div>
        <div id="viewTabs" class="easyui-tabs" data-options="region:'center'">
            <div title="主页" style="padding:5px;" >
                <iframe src="/desktop" height=100% width=100% frameborder="0" scrolling="auto" style="min_width:800px;"></iframe>
            </div>
        </div>
        <div id="footer" data-options="region:'south'" style="height:20px;text-align: center;line-height: 20px;overflow: hidden;">
            <div id="online" class="online">
                当前在线人数：<span v-text="online"></span>
            </div>
            Copyright © 2019 CA认证授权中心 v1.0

            <div id="online_list" class="online-list">
                <div class="online-list-header">
                    <i class="fa fa-close"></i>
                    <span>系统在线用户</span>
                </div>
                <div class="online-list-users">
                    <ul>
                        <li v-for="(user,i) in onlineUser" :id="'user'+user.uid" :key="user" :index="i" @click="sendMsg(user,${s_member.id})">
                  <span class="online-user-avator">
                    <i class="fa fa-user"></i>
                  </span>
                            <span v-text="user.realName"></span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
