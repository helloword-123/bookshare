# bookshare接口文档
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
v2023-04-09 21:17:15|2023-04-09 21:17:15|auto|@yang|Created by smart-doc



## 认证Controller
### 根据authId查询所有认证图片
**URL:** http://120.77.76.39:8090/campus-staff-auth/getAuthImgList/{authId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据authId查询所有认证图片

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
authId|int32|认证记录id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/campus-staff-auth/getAuthImgList/724
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 634,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 添加校园认证记录
**URL:** http://120.77.76.39:8090/campus-staff-auth/add

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 添加校园认证记录

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|No comments found.|true|-
number|string|No comments found.|true|-
realName|string|No comments found.|true|-
phone|string|No comments found.|true|-
email|string|No comments found.|true|-
fileList|array|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/campus-staff-auth/add --data '{
  "userId": 101,
  "number": "l9k8rz",
  "realName": "tatum.pollich",
  "phone": "559.620.0375",
  "email": "duncan.goodwin@yahoo.com",
  "fileList": [
    "l7s8zy"
  ]
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 128,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 根据userId获取用户的认证信息
**URL:** http://120.77.76.39:8090/campus-staff-auth/getAuthInfo/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据userId获取用户的认证信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/campus-staff-auth/getAuthInfo/595
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 671,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 获取所有用户的认证记录（多次认证只返回最后一条）
**URL:** http://120.77.76.39:8090/campus-staff-auth/getAuthList

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取所有用户的认证记录（多次认证只返回最后一条）

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/campus-staff-auth/getAuthList
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 720,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 审核
**URL:** http://120.77.76.39:8090/campus-staff-auth/check

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 审核

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-
checkerId|int32|No comments found.|true|-
description|string|No comments found.|false|-
status|int32|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/campus-staff-auth/check --data '{
  "id": 873,
  "checkerId": 447,
  "description": "d2x6ma",
  "status": 47
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 330,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 图书目录Controller
### ~~获取所有一级目录~~
**URL:** http://120.77.76.39:8090/book-category/getTopCategories

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取所有一级目录

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-category/getTopCategories
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 843,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 获取一二级图书分类，以级联方式返回
**URL:** http://120.77.76.39:8090/book-category/getCategoryCascader

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取一二级图书分类，以级联方式返回

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-category/getCategoryCascader
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 366,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 根据id获取分类全名（包括父分类，以"/"分隔）
**URL:** http://120.77.76.39:8090/book-category/getCategoryFullName/{categoryId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据id获取分类全名（包括父分类，以"/"分隔）

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
categoryId|int32|目录id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-category/getCategoryFullName/335
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 424,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 公共Controller
### 上传图片接口
**URL:** http://120.77.76.39:8090/common/uploadFile

**Type:** POST

**Author:** wuhaojie

**Content-Type:** multipart/form-data

**Description:** 上传图片接口

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|上传的图片文件|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://120.77.76.39:8090/common/uploadFile
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 770,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 测试：直接获取token
**URL:** http://120.77.76.39:8090/common/test/getToken

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 测试：直接获取token

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/common/test/getToken
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 913,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 
**URL:** http://120.77.76.39:8090/common/test

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
username|string|No comments found.|true|-
password|string|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/common/test --data '{
  "username": "tatum.pollich",
  "password": "lmh9bk"
}'
```

**Response-example:**
```
string
```

## 消息Controller
### 获取未读消息长度
**URL:** http://120.77.76.39:8090/message/getUnReadMessagesSize/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取未读消息长度

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/message/getUnReadMessagesSize/485
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 978,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 根据userId获取其已读和未读消息
**URL:** http://120.77.76.39:8090/message/getAllMessages/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据userId获取其已读和未读消息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/message/getAllMessages/186
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 751,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 已读消息
**URL:** http://120.77.76.39:8090/message/readMessage/{userId}/{msgId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 已读消息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-
msgId|int32| 消息id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/message/readMessage/362/125
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 448,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 图书Controller
### 小程序首页获取图书数据，根据分类id聚合返回
**URL:** http://120.77.76.39:8090/book/getListWithCategory

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 小程序首页获取图书数据，根据分类id聚合返回

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book/getListWithCategory
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 806,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 判断改isbn号的图书是否已经在漂流中
**URL:** http://120.77.76.39:8090/book/isDrifting/{isbn}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 判断改isbn号的图书是否已经在漂流中

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
isbn|string|图书ISBN码|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book/isDrifting/g7xm9r
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 183,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 根据筛选条件返回图书列表
**URL:** http://120.77.76.39:8090/book/getListWithCondition

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据筛选条件返回图书列表

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
categoryId|int32|图书分类id|false|-
sortColumn|string|排序字段|false|-
sortOrder|string| 升序/降序|false|-
keyword|string|   模糊查询关键词|false|-
latitude|double|No comments found.|false|-
longitude|double|No comments found.|false|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book/getListWithCondition?sortColumn=jzofo3&latitude=27.42&categoryId=252&keyword=y9g093&sortOrder=lyk5ru&longitude=13.89
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 524,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 评价Controller
### 添加建议
**URL:** http://120.77.76.39:8090/advice/add

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 添加建议

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|No comments found.|true|-
content|string|No comments found.|true|-
contact|string|No comments found.|true|-
fileList|array|No comments found.|false|-
star|double|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/advice/add --data '{
  "userId": 455,
  "content": "ft3nnd",
  "contact": "9cqe4w",
  "fileList": [
    "r7kzre"
  ],
  "star": 79.04
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 243,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 图书漂流Controller
### 获取未审核的图书
**URL:** http://120.77.76.39:8090/book-drift/getNotCheckedBooks

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取未审核的图书

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getNotCheckedBooks
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 700,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 审核
**URL:** http://120.77.76.39:8090/book-drift/checkBook

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 审核

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-
checkerId|int32|No comments found.|true|-
checkerReply|string|No comments found.|false|-
status|int32|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/book-drift/checkBook --data '{
  "id": 946,
  "checkerId": 627,
  "checkerReply": "fadxh7",
  "status": 272
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 144,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 修改图书漂流状态
**URL:** http://120.77.76.39:8090/book-drift/checkBook/{bookId}/{status}

**Type:** PUT

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 修改图书漂流状态

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
bookId|int32|图书id|true|-
status|int32|修改状态|true|-

**Request-example:**
```
curl -X PUT -i http://120.77.76.39:8090/book-drift/checkBook/463/436
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 280,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 图书共享，保存信息
**URL:** http://120.77.76.39:8090/book-drift/shareBook

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 图书共享，保存信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
any object|object|any object.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/book-drift/shareBook --data '{
  "mapKey": {
    "waring": "You may use java.util.Object for Map value; smart-doc can't be handle."
  }
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 502,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 地图搜索页获取正在漂流的图书信息
**URL:** http://120.77.76.39:8090/book-drift/getDriftingBooks

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 地图搜索页获取正在漂流的图书信息

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getDriftingBooks
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 731,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 根据id获取正在漂流的信息
**URL:** http://120.77.76.39:8090/book-drift/getDriftingById/{id}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据id获取正在漂流的信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|图书漂流id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getDriftingById/461
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 742,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 借书
**URL:** http://120.77.76.39:8090/book-drift/borrow

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 借书

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
borrowId|int32|No comments found.|true|-
driftId|int32|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/book-drift/borrow --data '{
  "borrowId": 263,
  "driftId": 760
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 969,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 根据用户id获取他的共享和借阅记录
**URL:** http://120.77.76.39:8090/book-drift/getShareBorrowBookList/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据用户id获取他的共享和借阅记录

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getShareBorrowBookList/144
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 622,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 根据图书id获取其漂流记录（顺序连起来）
**URL:** http://120.77.76.39:8090/book-drift/getBookDriftSeries/{bookId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据图书id获取其漂流记录（顺序连起来）

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
bookId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getBookDriftSeries/543
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 92,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 图书收藏Controller
### 更新收藏信息
**URL:** http://120.77.76.39:8090/book-collect/update/{bookId}/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 更新收藏信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
bookId|int32|图书id|true|-
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-collect/update/872/223
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 861,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 获取图书收藏状态
**URL:** http://120.77.76.39:8090/book-collect/getBookCollectByIds/{bookId}/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取图书收藏状态

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
bookId|int32|图书id|true|-
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-collect/getBookCollectByIds/818/301
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 341,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 获取图书收藏列表
**URL:** http://120.77.76.39:8090/book-collect/getCollectedBooks/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取图书收藏列表

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-collect/getCollectedBooks/377
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 522,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 用户Controller
### 获取用户角色
**URL:** http://120.77.76.39:8090/user/getUserRoles/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取用户角色

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/user/getUserRoles/949
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 986,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 微信登录后，修改用户头像和昵称
**URL:** http://120.77.76.39:8090/user/updateUserInfo

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 微信登录后，修改用户头像和昵称

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-
nickName|string|No comments found.|true|-
phone|string|No comments found.|true|-
avatarUrl|string|No comments found.|false|-
authId|int32|No comments found.|false|-
roles|array|No comments found.|false|-
isAuth|boolean|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/user/updateUserInfo --data '{
  "id": 283,
  "nickName": "clyde.nicolas",
  "phone": "559.620.0375",
  "avatarUrl": "www.mitchell-koelpin.net",
  "authId": 90,
  "roles": [
    "kxrgtt"
  ],
  "isAuth": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 555,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 管理员退出登录
**URL:** http://120.77.76.39:8090/user/logout/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 管理员退出登录

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|用户id|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/user/logout/330
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 666,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### ~~后台账号登录~~
**URL:** http://120.77.76.39:8090/user/login

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 后台账号登录

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
username|string|No comments found.|true|-
password|string|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/user/login --data '{
  "username": "tatum.pollich",
  "password": "7mkhlh"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 284,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 小程序登录根据code获取token，并且请求openid与本地账号系统绑定，最后返回用户信息和token
**URL:** http://120.77.76.39:8090/user/wxLogin

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 小程序登录根据code获取token，并且请求openid与本地账号系统绑定，最后返回用户信息和token

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
any object|object|any object.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/user/wxLogin --data '{
  "mapKey": {
    "waring": "You may use java.util.Object for Map value; smart-doc can't be handle."
  }
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 794,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 验证token有效性
**URL:** http://120.77.76.39:8090/user/checkToken

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 验证token有效性

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
CommonConstant.TOKEN|string|token值|true|-


**Request-example:**
```
curl -X GET -H 'CommonConstant.TOKEN' -i http://120.77.76.39:8090/user/checkToken
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|是否成功|-
code|int32|返回码|-
message|string|返回消息|-
data|map|返回数据|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 578,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```


