本程序结构
=========


### 功能：
* 登录
* 读取通讯录数据库，并格式化输出到 JTable - catalogue 上
* 对 catalogue 上内容进行修改
* 修改后能保存到数据库中
* 将通讯录数据库双向读写至逗号分隔值 (csv) 文件中
* 对 catalogue 上内容能够准确条件搜索和模糊搜索


### 源码目录结构

1. src/classes
* admin.java - 定义登录用户对象，并且连接到用户数据库
* member.java - 定义通讯录中成员对象，并且连接到通讯录数据库

2. src/db
数据库文件

3. src/ui
* commitSaveDiag    - 确认保存对话框
* contacts          - 主界面（通讯录管理界面）
* login             - 登录界面

4. src/utils
* contactUtils      - 通讯录数据库连接工具
* csvUtils          - 输出 csv 文件工具
* loginUtils        - 用户数据库连接工具
* revokeStack       - 用于**删除/撤销**操作的栈
