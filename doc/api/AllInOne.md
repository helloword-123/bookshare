# bookshare接口文档
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
v2023-02-19 11:01:45|2023-02-19 11:01:45|auto|@yang|Created by smart-doc



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
authId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/campus-staff-auth/getAuthImgList/927
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
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

### 添加校园认证记录
**URL:** http://120.77.76.39:8090/campus-staff-auth/add

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 添加校园认证记录

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|No comments found.|false|-
number|string|No comments found.|false|-
realName|string|No comments found.|false|-
phone|string|No comments found.|false|-
email|string|No comments found.|false|-
fileList|array|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/campus-staff-auth/add --data '{
  "userId": 311,
  "number": "wpm923",
  "realName": "otto.bergstrom",
  "phone": "313-616-1384",
  "email": "elfreda.langworth@yahoo.com",
  "fileList": [
    "w3f34c"
  ]
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 685,
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
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/campus-staff-auth/getAuthInfo/790
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 147,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 960,
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
id|int32|No comments found.|false|-
checkerId|int32|No comments found.|false|-
description|string|No comments found.|false|-
status|int32|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/campus-staff-auth/check --data '{
  "id": 769,
  "checkerId": 227,
  "description": "gvlihl",
  "status": 869
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 53,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 825,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 773,
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
categoryId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-category/getCategoryFullName/353
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 573,
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
file|file|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://120.77.76.39:8090/common/uploadFile
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 196,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 626,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
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
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/message/getUnReadMessagesSize/156
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 714,
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
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/message/getAllMessages/94
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 786,
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
userId|int32|No comments found.|true|-
msgId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/message/readMessage/391/966
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 535,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 737,
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
isbn|string|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book/isDrifting/i5gmxc
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 449,
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
categoryId|int32|No comments found.|false|-
sortColumn|string|No comments found.|false|-
sortOrder|string|No comments found.|false|-
keyword|string|No comments found.|false|-
latitude|double|No comments found.|false|-
longitude|double|No comments found.|false|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book/getListWithCondition?sortColumn=u0fkar&longitude=83.88&categoryId=929&latitude=25.67&sortOrder=shai9w&keyword=qn7edm
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 916,
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
userId|int32|No comments found.|false|-
content|string|No comments found.|false|-
contact|string|No comments found.|false|-
fileList|array|No comments found.|false|-
star|double|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/advice/add --data '{
  "userId": 682,
  "content": "x4wmc0",
  "contact": "eoe830",
  "fileList": [
    "dg2rrg"
  ],
  "star": 75.62
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 814,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 316,
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
id|int32|No comments found.|false|-
checkerId|int32|No comments found.|false|-
checkerReply|string|No comments found.|false|-
status|int32|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/book-drift/checkBook --data '{
  "id": 221,
  "checkerId": 187,
  "checkerReply": "hti9xp",
  "status": 77
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 919,
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
bookId|int32|No comments found.|true|-
status|int32|No comments found.|true|-

**Request-example:**
```
curl -X PUT -i http://120.77.76.39:8090/book-drift/checkBook/664/211
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 825,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 908,
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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 70,
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
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getDriftingById/631
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 98,
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
borrowId|int32|No comments found.|false|-
driftId|int32|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/book-drift/borrow --data '{
  "borrowId": 176,
  "driftId": 95
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
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

### 根据用户id获取他的共享和借阅记录
**URL:** http://120.77.76.39:8090/book-drift/getShareBorrowBookList/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据用户id获取他的共享和借阅记录

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getShareBorrowBookList/698
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 271,
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
bookId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-drift/getBookDriftSeries/940
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 718,
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
bookId|int32|No comments found.|true|-
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-collect/update/674/276
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 838,
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
bookId|int32|No comments found.|true|-
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-collect/getBookCollectByIds/404/676
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 357,
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
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/book-collect/getCollectedBooks/763
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 170,
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
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/user/getUserRoles/570
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 29,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 
**URL:** http://120.77.76.39:8090/user/updateUserInfo

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|false|-
nickName|string|No comments found.|false|-
avatarUrl|string|No comments found.|false|-
phone|string|No comments found.|false|-
authId|int32|No comments found.|false|-
roles|array|No comments found.|false|-
isAuth|boolean|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/user/updateUserInfo --data '{
  "id": 72,
  "nickName": "julius.lubowitz",
  "avatarUrl": "www.celina-hessel.us",
  "phone": "313-616-1384",
  "authId": 616,
  "roles": [
    "mimwar"
  ],
  "isAuth": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 379,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 
**URL:** http://120.77.76.39:8090/user/logout/{userId}

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
userId|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://120.77.76.39:8090/user/logout/179
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 945,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 
**URL:** http://120.77.76.39:8090/user/login

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
username|string|No comments found.|false|-
password|string|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://120.77.76.39:8090/user/login --data '{
  "username": "otto.bergstrom",
  "password": "m30jal"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 101,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 
**URL:** http://120.77.76.39:8090/user/wxLogin

**Type:** POST

**Author:** wuhaojie

**Content-Type:** application/json; charset=utf-8

**Description:** 

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
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 477,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

### 
**URL:** http://120.77.76.39:8090/user/checkToken

**Type:** GET

**Author:** wuhaojie

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
token|string|null|true|-


**Request-example:**
```
curl -X GET -H 'token' -i http://120.77.76.39:8090/user/checkToken
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 18,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```


