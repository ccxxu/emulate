<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>CA认证授权中心</title>
        <link rel="stylesheet" href="/css/login.css">
        <link rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
        <script type="text/javascript">
            if(window !=top){
                top.location.href=location.href;
            }
        </script>
    </head>
    <body>
        <div class="logo_box">
            <h1>CA认证授权中心</h1>
            <form action="/login" method="post" autocomplete="off">
                <div class="input_outer">
                    <i class="fa fa-user-o u_user"></i>
                    <input required="required" name="username" class="text" placeholder="输入账号" type="text">
                </div>
                <div class="input_outer">
                    <i class="fa fa-eye u_user"></i>
                    <input required="required" placeholder="请输入密码" name="password" class="text" type="password">
                </div>
                <div class="mb2">
                    <button type="submit" class="act-but submit" style="color: #FFFFFF">登录</button>
                </div>
                <#if error??>
                    <div style="text-align:center;padding: 10px;">${error}</div>
                </#if>
            </form>
        </div>
    </body>
</html>
