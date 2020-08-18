# Springboot版本与SpringCloud版本不是在统一范围内报错
## 控制台报如下错误：
### Description:

The bean 'proxyRequestHelper', defined in class path resource [org/springframework/cloud/netflix/zuul/ZuulProxyAutoConfiguration$NoActuatorConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource [org/springframework/cloud/netflix/zuul/ZuulProxyAutoConfiguration$EndpointConfiguration.class] and overriding is disabled.

### Action:

Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true

### 原因分析:

此时用的版本springboot的版本是2.1.1.RELEASE，而用的springCloud用的版本是Finchley.RC1；

### 改正:
springboot的版本2.1.1.RELEASE不变，springCloud用版本是Greenwich.M3，
springboot的版本2.0.1.RELEASE对应的springColud版本是Finchley.RC1；