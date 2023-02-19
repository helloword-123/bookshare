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
| [advice](#advice) | 评价表 |
| [advice_picture](#advice_picture) | advice和drift_picture的关联表，其中advice_id和picture_id作联合主键 |
| [auth_picture](#auth_picture) | 保存着认证时上传的图片 |
| [book](#book) | 图书表，保存图书实体信息 |
| [book_category](#book_category) | 图书分类表，其中分类又分父子关系，通过pid记录 |
| [book_collect](#book_collect) | 图书收藏表，user_id和book_id作联合主键 |
| [book_drift](#book_drift) | 图书漂流表，记录着图书漂流信息 |
| [book_drift_picture](#book_drift_picture) | 图书漂流表和漂流图片的关联表 |
| [campus_staff_auth](#campus_staff_auth) |  |
| [drift_picture](#drift_picture) | 保存图书漂流图片以及评价上传图片的url |
| [message](#message) | 消息表 |
| [user](#user) | 用户表 |

**表名：** <a id="acl_permission">acl_permission</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       | 权限名  |
|  3   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 权限描述  |
|  4   | key |   varchar   | 255 |   0    |    Y     |  N   |       | 权限键  |
|  5   | type_id |   int   | 10 |   0    |    Y     |  N   |       | 权限类型id  |
|  6   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  7   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="acl_permission_type">acl_permission_type</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 权限类型id  |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       | 权限类型名  |
|  3   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 权限类型描述  |
|  4   | key |   varchar   | 255 |   0    |    Y     |  N   |       | 权限类型键  |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="acl_role">acl_role</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       | 角色名  |
|  3   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 角色描述  |
|  4   | key |   varchar   | 255 |   0    |    Y     |  N   |       | 角色的键  |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="acl_role_permission">acl_role_permission</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | role_id |   int   | 10 |   0    |    N     |  Y   |       | 角色id  |
|  2   | permission_id |   int   | 10 |   0    |    N     |  Y   |       | 权限id  |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="acl_role_user">acl_role_user</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   int   | 10 |   0    |    N     |  Y   |       | 用户id  |
|  2   | role_id |   int   | 10 |   0    |    N     |  Y   |       | 角色id  |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="advice">advice</a>

**说明：** 评价表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | content |   varchar   | 255 |   0    |    Y     |  N   |       | 评价内容  |
|  3   | contact |   varchar   | 255 |   0    |    Y     |  N   |       | 联系方式  |
|  4   | user_id |   int   | 10 |   0    |    Y     |  N   |       | 评价的用户id  |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  7   | reply |   varchar   | 255 |   0    |    Y     |  N   |       | 后台回复  |
|  8   | status |   int   | 10 |   0    |    Y     |  N   |       | 回复状态：0-未回复，1-已回复  |
|  9   | star |   float   | 13 |   0    |    Y     |  N   |       | 评价星级  |

**表名：** <a id="advice_picture">advice_picture</a>

**说明：** advice和drift_picture的关联表，其中advice_id和picture_id作联合主键

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | advice_id |   int   | 10 |   0    |    N     |  Y   |       | 评价id  |
|  2   | picture_id |   int   | 10 |   0    |    N     |  Y   |       | 图片id  |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="auth_picture">auth_picture</a>

**说明：** 保存着认证时上传的图片

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | picture_url |   varchar   | 255 |   0    |    Y     |  N   |       | 图片url  |
|  3   | auth_id |   int   | 10 |   0    |    Y     |  N   |       | 认证id  |
|  4   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  5   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="book">book</a>

**说明：** 图书表，保存图书实体信息

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       | 书名  |
|  3   | author |   varchar   | 255 |   0    |    Y     |  N   |       | 作者  |
|  4   | category_id |   int   | 10 |   0    |    Y     |  N   |       | 目录id  |
|  5   | publishing_time |   varchar   | 255 |   0    |    Y     |  N   |       | 出版时间  |
|  6   | publishing_house |   varchar   | 255 |   0    |    Y     |  N   |       | 出版社  |
|  7   | description |   text   | 65535 |   0    |    Y     |  N   |       | 描述  |
|  8   | picture_url |   varchar   | 255 |   0    |    Y     |  N   |       | 封面图片url  |
|  9   | isbn |   varchar   | 255 |   0    |    Y     |  N   |       | ISBN码  |
|  10   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  11   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="book_category">book_category</a>

**说明：** 图书分类表，其中分类又分父子关系，通过pid记录

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | pid |   int   | 10 |   0    |    Y     |  N   |       | 父目录id  |
|  3   | name |   varchar   | 255 |   0    |    Y     |  N   |       | 分类名  |
|  4   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 分类描述  |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="book_collect">book_collect</a>

**说明：** 图书收藏表，user_id和book_id作联合主键

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | book_id |   int   | 10 |   0    |    N     |  Y   |       | 图书id  |
|  2   | user_id |   int   | 10 |   0    |    N     |  Y   |       | 用户id  |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  5   | status |   int   | 10 |   0    |    Y     |  N   |       | 收藏状态：1-收藏，2-未收藏  |

**表名：** <a id="book_drift">book_drift</a>

**说明：** 图书漂流表，记录着图书漂流信息

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | sharer_id |   int   | 10 |   0    |    Y     |  N   |       | 共享者id  |
|  3   | sharer_name |   varchar   | 255 |   0    |    Y     |  N   |       | 共享者昵称  |
|  4   | sharer_phone |   varchar   | 255 |   0    |    Y     |  N   |       | 共享者手机号  |
|  5   | borrower_id |   int   | 10 |   0    |    Y     |  N   |       | 借阅者id  |
|  6   | drift_address |   varchar   | 255 |   0    |    Y     |  N   |       | 漂流地  |
|  7   | latitude |   float   | 13 |   0    |    Y     |  N   |       | 纬度  |
|  8   | longitude |   float   | 13 |   0    |    Y     |  N   |       | 经度  |
|  9   | release_time |   datetime   | 19 |   0    |    Y     |  N   |       | 共享时间  |
|  10   | drift_time |   datetime   | 19 |   0    |    Y     |  N   |       | 借阅时间  |
|  11   | book_id |   int   | 10 |   0    |    Y     |  N   |       | 图书id  |
|  12   | note |   varchar   | 255 |   0    |    Y     |  N   |       | 共享者愿望  |
|  13   | status |   int   | 10 |   0    |    Y     |  N   |       | 漂流状态：0-审核中，1-审核成功，已发布，2-审核失败，3-共享中，4-此次漂流结束，5-此书漂流结束  |
|  14   | drift_num |   int   | 10 |   0    |    Y     |  N   |       | 图书的漂流次数  |
|  15   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  16   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  17   | checker_id |   int   | 10 |   0    |    Y     |  N   |       | 审核人id  |
|  18   | check_time |   datetime   | 19 |   0    |    Y     |  N   |       | 审核时间  |
|  19   | checker_reply |   varchar   | 255 |   0    |    Y     |  N   |       | 审核回复  |

**表名：** <a id="book_drift_picture">book_drift_picture</a>

**说明：** 图书漂流表和漂流图片的关联表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | drift_id |   int   | 10 |   0    |    N     |  Y   |       | 图书漂流id  |
|  2   | picture_id |   int   | 10 |   0    |    N     |  Y   |       | 分享图片id  |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="campus_staff_auth">campus_staff_auth</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | real_name |   varchar   | 255 |   0    |    Y     |  N   |       | 认证填写的真名  |
|  3   | phone |   varchar   | 255 |   0    |    Y     |  N   |       | 手机号  |
|  4   | email |   varchar   | 255 |   0    |    Y     |  N   |       | 邮件  |
|  5   | number |   varchar   | 255 |   0    |    Y     |  N   |       | 学号/工号  |
|  6   | user_id |   int   | 10 |   0    |    Y     |  N   |       | 用户id  |
|  7   | status |   int   | 10 |   0    |    Y     |  N   |       | 审核状态：0-未审核，1-审核通过，2-审核未通过  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  10   | checker_id |   int   | 10 |   0    |    Y     |  N   |       | 审核人id  |
|  11   | check_time |   datetime   | 19 |   0    |    Y     |  N   |       | 审核时间  |
|  12   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 审核回复  |

**表名：** <a id="drift_picture">drift_picture</a>

**说明：** 保存图书漂流图片以及评价上传图片的url

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | picture_url |   varchar   | 255 |   0    |    Y     |  N   |       | 图片url  |
|  3   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  4   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="message">message</a>

**说明：** 消息表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | message |   blob   | 65535 |   0    |    Y     |  N   |       | 消息体，二进制形式  |
|  3   | produce_time |   datetime   | 19 |   0    |    Y     |  N   |       | 发布消息时间  |
|  4   | consume_time |   datetime   | 19 |   0    |    Y     |  N   |       | 消费消息时间  |
|  5   | producer_id |   int   | 10 |   0    |    Y     |  N   |       | 发布者id  |
|  6   | consumer_id |   int   | 10 |   0    |    Y     |  N   |       | 消费者id  |
|  7   | hasConsumed |   int   | 10 |   0    |    Y     |  N   |       | 是否已读：0-未读，1-已读  |

**表名：** <a id="user">user</a>

**说明：** 用户表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | user_name |   varchar   | 255 |   0    |    Y     |  N   |       | 用户名  |
|  3   | account |   varchar   | 255 |   0    |    Y     |  N   |       | 账号名  |
|  4   | password |   varchar   | 255 |   0    |    Y     |  N   |       | 密码，密文存储  |
|  5   | phone |   varchar   | 255 |   0    |    Y     |  N   |       | 手机号  |
|  6   | sex |   int   | 10 |   0    |    Y     |  N   |       | 性别  |
|  7   | point |   int   | 10 |   0    |    Y     |  N   |       | 积分  |
|  8   | total_share_num |   int   | 10 |   0    |    Y     |  N   |       | 总分享次数  |
|  9   | total_use_num |   int   | 10 |   0    |    Y     |  N   |       | 总借阅次数  |
|  10   | avatar_url |   varchar   | 255 |   0    |    Y     |  N   |       | 头像url  |
|  11   | openid |   varchar   | 255 |   0    |    Y     |  N   |       | 微信小程序openid号  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  13   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  14   | auth_id |   int   | 10 |   0    |    Y     |  N   |       | 管理的认证记录id  |
