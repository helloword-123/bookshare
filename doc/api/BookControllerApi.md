
# &lt;p&gt; 前端控制器&lt;/p&gt;
## 
**URL:** `http://127.0.0.1:8090/book/shareBook`

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
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:8090/book/shareBook --data '{
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
  "code": 929,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 
**URL:** `http://127.0.0.1:8090/book/checkBook/{bookId}/{status}`

**Type:** `GET`

**Author:** wuhaojie

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 


**Path-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
bookId|int32|No comments found.|true|-
status|int32|No comments found.|true|-



**Request-example:**
```
curl -X GET -i http://127.0.0.1:8090/book/checkBook/846/572
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
  "code": 835,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

