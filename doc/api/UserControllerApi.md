
# &lt;p&gt;前端控制器&lt;/p&gt;
## 
**URL:** `http://127.0.0.1:8090/user/updateAvatarAndName`

**Type:** `POST`

**Author:** wuhaojie

**Content-Type:** `application/json; charset=utf-8`

**Description:** 




**Body-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|false|-
nickName|string|No comments found.|false|-
avatarUrl|string|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:8090/user/updateAvatarAndName --data '{
  "id": 236,
  "nickName": "chance.langworth",
  "avatarUrl": "www.corazon-adams.info"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
MQMessage|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 414,
  "MQMessage": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 
**URL:** `http://127.0.0.1:8090/user/adminLogout`

**Type:** `GET`

**Author:** wuhaojie

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 





**Request-example:**
```
curl -X GET -i http://127.0.0.1:8090/user/adminLogout
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
MQMessage|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 862,
  "MQMessage": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 
**URL:** `http://127.0.0.1:8090/user/login`

**Type:** `POST`

**Author:** wuhaojie

**Content-Type:** `application/json; charset=utf-8`

**Description:** 




**Body-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
username|string|No comments found.|false|-
password|string|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:8090/user/login --data '{
  "username": "clarissa.schroeder",
  "password": "wrccw8"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
MQMessage|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 640,
  "MQMessage": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 
**URL:** `http://127.0.0.1:8090/user/wxLogin`

**Type:** `POST`

**Author:** wuhaojie

**Content-Type:** `application/json; charset=utf-8`

**Description:** 




**Body-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
any object|object|any object.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:8090/user/wxLogin --data '{
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
MQMessage|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 58,
  "MQMessage": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 
**URL:** `http://127.0.0.1:8090/user/checkToken`

**Type:** `GET`

**Author:** wuhaojie

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
token|string|null|true|-





**Request-example:**
```
curl -X GET -H 'token' -i http://127.0.0.1:8090/user/checkToken
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
MQMessage|string|No comments found.|-
data|map|No comments found.|-
└─any object|object|any object.|-

**Response-example:**
```
{
  "success": true,
  "code": 378,
  "MQMessage": "success",
  "data": {
    "mapKey": {}
  }
}
```

