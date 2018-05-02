通讯录说明文档
==============

# 程序（文件夹）结构

* classes
    * admin
        定义用户类

    * member
        定义通讯录成员类

* ui
    * login
        登录界面

    * contact
        通讯录管理界面
        * 通讯录表 
        * 成员信息修改区
        * 账户管理区
        * 搜索区
         
* utils
    * dbUtil
        数据库连接
    * contactUtil
        通讯录 UI 管理
    * csvUtil
        csv 输出设置
    * loginUtil
        登录管理
    * revokeStack
        用于撤销操作（仅实现了撤销删除）的 Stack 类
    * memberUtil
        用于读取数据库中通讯录成员



# 运作过程

 数据库 <---> *member* 类 <---> GUI

 登录时查询数据库中是否有此用户

 登录成功后，访问数据库表 *t\_member* ，并将所有的表中数据转化为 *member* 类，并组成一个 Vector，再通过 *memberUtil* 中的方法将 Vector 中的所有 *member* 输出至 GUI 的表格上。

 增删修改功能通过修改 Vector<member> 中各个 *member* 来实现，而在最后保存时才将 Vector<member> 中各个 *member* 通过 *memberUtil* 中的方法输出到数据库中。

 而用户的增添删改则是在用户登录成功后进行，具体内部细节在 *loginUtil* 中定义。

 
# 数据库结构
 数据库仅设置了两个表：
 * t_user
    用于存储登录的账号密码；
    
    username    |password
    ------------|--------
    _(用户名)_  |_(密码)_

 * t_members
    用于存储通讯录成员；
    
    id  |name   |grp    |grade  |class  |phonenum   |email  |dormitory  |address
    ----|-------|-------|-------|-------|-----------|-------|-----------|-------
    (ID)|(姓名) |(方向) |(年级) |(班级) |(电话号码) |(电邮) |(宿舍)     |(住址)




