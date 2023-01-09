
# 
## 
**URL:** `http://127.0.0.1:8090/common/uploadFile`

**Type:** `POST`


**Content-Type:** `multipart/form-data`

**Description:** 



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-


**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://127.0.0.1:8090/common/uploadFile
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
  "code": 643,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

## 
**URL:** `http://127.0.0.1:8090/common/test/getToken`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 





**Request-example:**
```
curl -X GET -i http://127.0.0.1:8090/common/test/getToken
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
  "code": 323,
  "message": "success",
  "data": {
    "mapKey": {}
  }
}
```

