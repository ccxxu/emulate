### 1.构建DAO
1.1 EntityManager是用来对实体Bean进行操作的辅助类.
他可以用来产生/删除持久化的实体Bean, 通过主键查找实体bean, 
也可以通过EJB3QL语言查找满足条件的实体Bean.
实体Bean被EntityManager管理时, 
EntityManager跟踪他的状态改变, 
在任何决定更新实体Bean的时候便会把发生改变的值同步到数据库中.
当实体Bean从EntityManager分离后, 他是不受管理的, 
EntityManager无法跟踪他的任何状态改变.
EntityManager的获取前面已经介绍过, 
可以通过@PersistenceContext注释由EJB容器动态注入。