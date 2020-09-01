##  解决 JWT + SSO 报错 ：Error creating bean with name 'jwtTokenServices' ...

解决：报这个错是因为我们没有在客户端的 application.properties里配置token-value

##  eureka会自动注册自己，必须设置下面属性

如果不想注册, 添加两行设置
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
