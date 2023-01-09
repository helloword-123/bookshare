# 数据库设计文档

**数据库名：** bookshare

**文档版本：** 1.0.0

**文档描述：** 数据库设计文档生成

| 表名                  | 说明       |
| :---: | :---: |
| [acl_permission](#acl_permission) |  |
| [acl_permission_type](#acl_permission_type) |  |
| [acl_role](#acl_role) |  |
| [acl_role_permission](#acl_role_permission) |  |
| [acl_role_user](#acl_role_user) |  |
| [book](#book) |  |
| [book_category](#book_category) |  |
| [book_detail](#book_detail) |  |
| [book_drift](#book_drift) |  |
| [user](#user) |  |

**表名：** <a id="acl_permission">acl_permission</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | description |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | key |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | type_id |   int   | 10 |   0    |    Y     |  N   |       |   |
|  6   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  7   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |

**表名：** <a id="acl_permission_type">acl_permission_type</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | description |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | key |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |

**表名：** <a id="acl_role">acl_role</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | description |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | key |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |

**表名：** <a id="acl_role_permission">acl_role_permission</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | role_id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | permission_id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |

**表名：** <a id="acl_role_user">acl_role_user</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | role_id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |

**表名：** <a id="book">book</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 1  |
|  2   | book_detail_id |   int   | 10 |   0    |    Y     |  N   |       | 外键  |
|  3   | status |   int   | 10 |   0    |    Y     |  N   |       | 漂流状态  |
|  4   | current_address |   varchar   | 255 |   0    |    Y     |  N   |       | 图书当前地址  |
|  5   | release_time |   datetime   | 19 |   0    |    Y     |  N   |       | 图书分享发布时间  |
|  6   | sharer_id |   int   | 10 |   0    |    Y     |  N   |       | 外键：当前共享发布者id  |
|  7   | publisher_id |   int   | 10 |   0    |    Y     |  N   |       |   |
|  8   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 图书描述  |
|  9   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  10   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  11   | picture_url |   varchar   | 255 |   0    |    Y     |  N   |       | 共享时上传的图片  |

**表名：** <a id="book_category">book_category</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | description |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  5   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |

**表名：** <a id="book_detail">book_detail</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | category_id |   int   | 10 |   0    |    Y     |  N   |       |   |
|  3   | book_name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | author |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | publication_time |   varchar   | 255 |   0    |    Y     |  N   |       | 图书出版时间  |
|  6   | picture_url |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  7   | isbn |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  8   | description |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  9   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  10   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  11   | publishing_house |   varchar   | 255 |   0    |    Y     |  N   |       |   |

**表名：** <a id="book_drift">book_drift</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | sharer_id |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | user_id |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | drift_address |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | drift_time |   datetime   | 19 |   0    |    Y     |  N   |       | 使用者共享图书的时间点  |
|  6   | book_id |   int   | 10 |   0    |    Y     |  N   |       | 外键：图书的id  |
|  7   | note |   varchar   | 255 |   0    |    Y     |  N   |       | 读书心得、分享愿望  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |

**表名：** <a id="user">user</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | user_name |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  3   | account |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  4   | password |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  5   | phone |   char   | 11 |   0    |    Y     |  N   |       |   |
|  6   | sex |   tinyint   | 4 |   0    |    Y     |  N   |       |   |
|  7   | point |   int   | 10 |   0    |    Y     |  N   |       | 用户积分  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       |   |
|  10   | total_share_num |   int   | 10 |   0    |    Y     |  N   |       |   |
|  11   | total_use_num |   int   | 10 |   0    |    Y     |  N   |       |   |
|  12   | avatar_url |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  13   | openid |   varchar   | 255 |   0    |    Y     |  N   |       |   |
