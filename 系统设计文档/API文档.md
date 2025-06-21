# TjuFood 项目 API 文档


**简介**:TjuFood 项目 API 文档


**HOST**:http://localhost:8090


**联系人**:


**Version**:v1.0.0


**接口路径**:/v3/api-docs


[TOC]






# 01. 用户功能


## 根据ID查询指定用户的公开信息


**接口地址**:`/api/users/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|用户唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 刷新当前用户的头像URL


**接口地址**:`/api/users/avatar/refresh`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传并更新当前用户的头像


**接口地址**:`/api/users/avatar/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|file|头像图片文件|query|true|file||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 用户登录


**接口地址**:`/api/users/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>用户使用账号密码和滑动验证码登录系统</p>



**请求示例**:


```javascript
{
  "account": "user123@example.com",
  "password": "password123",
  "x": 120,
  "uuid": "550e8400-e29b-41d4-a716-446655440000"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|loginFormDTO|用户登录请求数据|body|true|LoginFormDTO|LoginFormDTO|
|&emsp;&emsp;account|用户账号（用户ID或邮箱）||true|string||
|&emsp;&emsp;password|用户密码||true|string||
|&emsp;&emsp;x|滑动验证码的X坐标距离||true|integer(int32)||
|&emsp;&emsp;uuid|验证码的唯一标识||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 用户退出登录


**接口地址**:`/api/users/logout`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|authorization|用户登录令牌|header|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取当前登录用户的详细信息


**接口地址**:`/api/users/me`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 编辑个人资料


**接口地址**:`/api/users/me`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "nickName": "",
  "icon": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userDTO|UserDTO|body|true|UserDTO|UserDTO|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;nickName|||false|string||
|&emsp;&emsp;icon|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 重置密码


**接口地址**:`/api/users/password`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "email": "",
  "code": "",
  "newPassword": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|passwordResetDTO|PasswordResetDTO|body|true|PasswordResetDTO|PasswordResetDTO|
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;code|||false|string||
|&emsp;&emsp;newPassword|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 用户注册


**接口地址**:`/api/users/register`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "email": "",
  "password": "",
  "confirmPassword": "",
  "code": "",
  "nickname": "",
  "gender": 0,
  "campus": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|registerFormDTO|RegisterFormDTO|body|true|RegisterFormDTO|RegisterFormDTO|
|&emsp;&emsp;email|||true|string||
|&emsp;&emsp;password|||true|string||
|&emsp;&emsp;confirmPassword|||true|string||
|&emsp;&emsp;code|||true|string||
|&emsp;&emsp;nickname|||false|string||
|&emsp;&emsp;gender|||false|integer(int32)||
|&emsp;&emsp;campus|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 02. 食堂管理


## 查询食堂列表


**接口地址**:`/api/canteens`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>支持按校区过滤，支持获取所有食堂</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|campusId||query|false|integer(int64)||
|all||query|false|boolean||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询单个食堂详情


**接口地址**:`/api/canteens/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|食堂的唯一ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 02. 校区信息


## 查询所有校区列表


**接口地址**:`/api/campuses`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据ID查询单个校区


**接口地址**:`/api/campuses/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|校区ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 03. 窗口管理


## 查询单个窗口详情


**接口地址**:`/api/stalls/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|窗口的唯一ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询食堂下的所有窗口


**接口地址**:`/api/stalls/canteen/{canteenId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|canteenId|所属食堂的ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 04. 菜品管理


## 查询单个菜品详情


**接口地址**:`/api/dishes/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|菜品的唯一ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 分页搜索菜品


**接口地址**:`/api/dishes/search`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name|菜品名称关键词|query|false|string||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||
|campusId|校区ID，用于筛选|query|false|integer(int64)||
|canteenId|食堂ID，用于筛选|query|false|integer(int64)||
|sortBy|排序字段 (name=按名字, price=按价格, liked=按点赞)|query|false|string||
|sortOrder|排序方向 (asc=升序, desc=降序)|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询窗口下的所有菜品


**接口地址**:`/api/dishes/stall/{stallId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|stallId|所属窗口的ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 05-1. 举报


## 提交举报


**接口地址**:`/api/reports`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 05. 博客功能


## 分页筛选博客列表


**接口地址**:`/api/blogs`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current||query|false|integer(int32)||
|size||query|false|integer(int32)||
|sortBy||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 发布新博客


**接口地址**:`/api/blogs`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "userId": 0,
  "title": "",
  "content": "",
  "imageUrl": "",
  "rating": 0,
  "price": 0,
  "tags": "",
  "canteenId": 0,
  "stallId": 0,
  "blogType": 0,
  "liked": 0,
  "comments": 0,
  "status": 0,
  "isTop": 0,
  "isQuality": 0,
  "authorName": "",
  "authorIcon": "",
  "isLiked": true,
  "canteenName": "",
  "stallName": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|blogDTO|BlogDTO|body|true|BlogDTO|BlogDTO|
|&emsp;&emsp;id|博客唯一ID||false|integer(int64)||
|&emsp;&emsp;userId|作者的用户ID||false|integer(int64)||
|&emsp;&emsp;title|博客标题||false|string||
|&emsp;&emsp;content|博客正文||false|string||
|&emsp;&emsp;imageUrl|封面图片URL||false|string||
|&emsp;&emsp;rating|评分 (1-5分)||false|number(double)||
|&emsp;&emsp;price|人均价格||false|number(double)||
|&emsp;&emsp;tags|标签 (JSON格式)||false|string||
|&emsp;&emsp;canteenId|关联的食堂ID||false|integer(int64)||
|&emsp;&emsp;stallId|关联的窗口ID||false|integer(int64)||
|&emsp;&emsp;blogType|博客类型 (1=美食评价, 2=普通分享)||false|integer(int32)||
|&emsp;&emsp;liked|博客被点赞的数量||false|integer(int32)||
|&emsp;&emsp;comments|博客被评论的数量||false|integer(int32)||
|&emsp;&emsp;favorites|博客被收藏的数量||false|integer(int32)||
|&emsp;&emsp;status|博客状态 (0=待审核, 1=审核通过, 2=审核拒绝, 3=用户隐藏, 4=管理员删除)||false|integer(int32)||
|&emsp;&emsp;isTop|是否置顶 (1=是, 0=否)||false|integer(int32)||
|&emsp;&emsp;isQuality|是否为优质内容/加精 (1=是, 0=否)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||
|&emsp;&emsp;authorName|||false|string||
|&emsp;&emsp;authorIcon|||false|string||
|&emsp;&emsp;isLiked|||false|boolean||
|&emsp;&emsp;canteenName|||false|string||
|&emsp;&emsp;stallName|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据ID查询博客详情


**接口地址**:`/api/blogs/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|博客唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 分页查询热门博客


**接口地址**:`/api/blogs/hot`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current|当前页码|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 点赞或取消点赞博客


**接口地址**:`/api/blogs/like/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|博客唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询为博客点赞的用户列表


**接口地址**:`/api/blogs/likes/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|博客唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 07. 评论功能


## 发表新评论


**接口地址**:`/api/comments`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "userId": 0,
  "targetId": 0,
  "targetType": 0,
  "parentId": 0,
  "content": "",
  "liked": 0,
  "status": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|comment|评论实体对象|body|true|Comment|Comment|
|&emsp;&emsp;id|评论ID||false|integer(int64)||
|&emsp;&emsp;userId|评论者ID||false|integer(int64)||
|&emsp;&emsp;targetId|被评论对象ID (博客或评论ID)||false|integer(int64)||
|&emsp;&emsp;targetType|被评论对象类型 (1=博客, 2=评论)||false|integer(int32)||
|&emsp;&emsp;parentId|父评论ID (用于楼中楼)||false|integer(int64)||
|&emsp;&emsp;content|评论内容||false|string||
|&emsp;&emsp;liked|被点赞的数量||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=正常, 1=用户隐藏)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询指定目标的评论列表


**接口地址**:`/api/comments/target/{targetId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|targetId|被评论对象的唯一ID|path|true|integer(int64)||
|type|被评论对象类型 (1=博客)|query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询当前登录用户的评论列表


**接口地址**:`/api/comments/user/me`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current||query|false|integer(int32)||
|size||query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 08. 优惠券功能


## 分页条件查询优惠券列表


**接口地址**:`/api/vouchers`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current||query|false|integer(int32)||
|size||query|false|integer(int32)||
|type||query|false|integer(int32)||
|canteenId||query|false|integer(int64)||
|status||query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取单个优惠券详情


**接口地址**:`/api/vouchers/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|优惠券ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 兑换（秒杀）一张优惠券


**接口地址**:`/api/vouchers/{id}/redeem`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|优惠券模板唯一ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询我的优惠券


**接口地址**:`/api/vouchers/my`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询指定窗口可用的优惠券列表


**接口地址**:`/api/vouchers/stalls/{stallId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|stallId|窗口唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 09. 收藏功能


## 收藏或取消收藏


**接口地址**:`/api/favorites`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>这是一个开关接口，如果未收藏则收藏，已收藏则取消</p>



**请求示例**:


```javascript
{
  "userId": 0,
  "favoriteId": 0,
  "type": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|favorite|收藏记录实体|body|true|Favorite|Favorite|
|&emsp;&emsp;id|收藏ID||false|integer(int64)||
|&emsp;&emsp;userId|用户ID||false|integer(int64)||
|&emsp;&emsp;favoriteId|被收藏对象ID||false|integer(int64)||
|&emsp;&emsp;type|被收藏对象类型 (1=博客, 2=窗口)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询我的收藏列表


**接口地址**:`/api/favorites/my`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|type|收藏类型 (1=博客, 2=窗口)|query|true|integer(int32)||
|current|当前页码|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 09. 文件管理


## 删除文件


**接口地址**:`/api/admin/files/{fileId}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|fileId|文件ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 刷新文件URL


**接口地址**:`/api/admin/files/{fileId}/refresh`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|fileId|文件ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传轮播图


**接口地址**:`/api/admin/files/banner/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|file|图片文件|query|true|file||
|description|图片描述|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传校区图片


**接口地址**:`/api/admin/files/campus/{campusId}/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|campusId|校区ID|path|true|integer(int64)||
|file|图片文件|query|true|file||
|description|图片描述|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传食堂图片


**接口地址**:`/api/admin/files/canteen/{canteenId}/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|canteenId|食堂ID|path|true|integer(int64)||
|file|图片文件|query|true|file||
|description|图片描述|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传菜品图片


**接口地址**:`/api/admin/files/dish/{dishId}/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dishId|菜品ID|path|true|integer(int64)||
|file|图片文件|query|true|file||
|description|图片描述|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据实体ID和类型查询文件列表


**接口地址**:`/api/admin/files/entity/{entityId}/type/{typeId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|entityId|实体ID|path|true|integer(int64)||
|typeId|文件类型|path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 批量刷新实体文件URL


**接口地址**:`/api/admin/files/entity/{entityId}/type/{typeId}/refresh`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|entityId|实体ID|path|true|integer(int64)||
|typeId|文件类型|path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传窗口图片


**接口地址**:`/api/admin/files/stall/{stallId}/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|stallId|窗口ID|path|true|integer(int64)||
|file|图片文件|query|true|file||
|description|图片描述|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据类型查询所有文件


**接口地址**:`/api/admin/files/type/{typeId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|typeId|文件类型|path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传单个文件


**接口地址**:`/api/admin/files/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|entityId|关联实体ID|query|true|integer(int64)||
|typeId|文件类型 (0=食堂, 1=校区, 2=窗口, 3=菜品, 4=轮播图)|query|true|integer(int32)||
|file|文件|query|true|file||
|description|文件描述|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 批量上传文件


**接口地址**:`/api/admin/files/upload/batch`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
[]
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|entityId|关联实体ID|query|true|integer(int64)||
|typeId|文件类型|query|true|integer(int32)||
|strings|string|body|true|array||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 09. 综合搜索


## 综合搜索


**接口地址**:`/api/search`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|keyword|关键词|query|true|string||
|type|类型:canteen|dish|blog|stall|all|query|false|string||
|current||query|false|integer(int32)||
|size||query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 11. 文件上传功能


## 上传图片


**接口地址**:`/api/upload/image`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>上传成功后返回图片的访问URL</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|file|要上传的图片文件|query|true|file||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 11. 系统公告


## 获取系统公告列表


**接口地址**:`/api/notices`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current|当前页码|query|false|integer(int32)||
|size|每页大小|query|false|integer(int32)||
|type|公告类型 (0=普通, 1=重要)|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据ID获取公告详情


**接口地址**:`/api/notices/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|公告ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取重要公告


**接口地址**:`/api/notices/important`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取最新公告


**接口地址**:`/api/notices/latest`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|limit|获取数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 12. 排行榜功能


## 获取博客排行榜


**接口地址**:`/api/rankings/blogs`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sortBy|排序方式 (likes=点赞数, comments=评论数, views=浏览量)|query|false|string||
|limit|返回数量|query|false|integer(int32)||
|timeRange|时间范围 (week=本周, month=本月, all=全部)|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取食堂排行榜


**接口地址**:`/api/rankings/canteens`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sortBy|排序方式 (score=评分, likes=点赞数, comments=评论数)|query|false|string||
|limit|返回数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取菜品排行榜


**接口地址**:`/api/rankings/dishes`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sortBy|排序方式 (score=评分, likes=点赞数, sales=销量)|query|false|string||
|limit|返回数量|query|false|integer(int32)||
|canteenId|食堂ID过滤|query|false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取综合排行榜数据


**接口地址**:`/api/rankings/summary`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取用户排行榜


**接口地址**:`/api/rankings/users`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sortBy|排序方式 (credits=积分, blogs=博客数, likes=获赞数)|query|false|string||
|limit|返回数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 12. 验证码服务


## 获取滑动拼图验证码


**接口地址**:`/api/verifications/captcha`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 发送邮箱验证码


**接口地址**:`/api/verifications/code`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "email": "",
  "type": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sendCodeDTO|SendCodeDTO|body|true|SendCodeDTO|SendCodeDTO|
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;type|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# 12. MinIO文件管理


## 删除文件


**接口地址**:`/api/minio/{objectName}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>删除MinIO中指定的文件对象</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|objectName|文件对象名称|path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 检查文件是否存在


**接口地址**:`/api/minio/exists/{objectName}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>检查MinIO中指定的文件对象是否存在</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|objectName|文件对象名称|path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 生成文件访问URL


**接口地址**:`/api/minio/generate-url/{objectName}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>为指定的文件对象生成新的预签名URL</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|objectName|文件对象名称|path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取公共访问URL


**接口地址**:`/api/minio/public-url/{objectName}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>获取文件的公共访问URL（如果bucket是公共的）</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|objectName|文件对象名称|path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 刷新文件访问URL


**接口地址**:`/api/minio/refresh-url`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>刷新即将过期或已过期的MinIO预签名URL</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# A01. 后台管理


## 刷新当前管理员的头像URL


**接口地址**:`/api/admin/avatar/refresh`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传并更新当前管理员的头像


**接口地址**:`/api/admin/avatar/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|file||query|true|file||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 管理员登录


**接口地址**:`/api/admin/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>使用管理员账号和密码登录，成功后返回Token</p>



**请求示例**:


```javascript
{
  "username": "",
  "password": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|adminLoginDTO|AdminLoginDTO|body|true|AdminLoginDTO|AdminLoginDTO|
|&emsp;&emsp;username|||true|string||
|&emsp;&emsp;password|||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 管理员退出登录


**接口地址**:`/api/admin/logout`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|authorization||header|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取当前登录的管理员信息


**接口地址**:`/api/admin/me`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改管理员密码


**接口地址**:`/api/admin/password`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>修改当前登录管理员的密码</p>



**请求示例**:


```javascript
{
  "oldPassword": "当前密码",
  "newPassword": "新密码"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|adminPasswordChangeDTO|管理员修改密码的数据传输对象|body|true|AdminPasswordChangeDTO|AdminPasswordChangeDTO|
|&emsp;&emsp;oldPassword|当前密码||true|string||
|&emsp;&emsp;newPassword|新密码||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新管理员个人信息


**接口地址**:`/api/admin/profile`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>更新当前登录管理员的用户名和姓名</p>



**请求示例**:


```javascript
{
  "username": "admin_user",
  "name": "张三"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|adminProfileUpdateDTO|管理员更新个人信息的数据传输对象|body|true|AdminProfileUpdateDTO|AdminProfileUpdateDTO|
|&emsp;&emsp;username|管理员登录账号||true|string||
|&emsp;&emsp;name|管理员真实姓名||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# B01. 内容管理 - 校区管理


## 查询校区列表


**接口地址**:`/api/admin/campuses`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|all||query|false|boolean||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 创建校区


**接口地址**:`/api/admin/campuses`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "卫津路校区",
  "address": "天津市南开区卫津路92号"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|campus|校区实体对象|body|true|Campus|Campus|
|&emsp;&emsp;id|校区唯一ID||false|integer(int64)||
|&emsp;&emsp;name|校区名称||true|string||
|&emsp;&emsp;address|校区地址||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据ID查询单个校区


**接口地址**:`/api/admin/campuses/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|校区唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新校区信息


**接口地址**:`/api/admin/campuses/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "卫津路校区",
  "address": "天津市南开区卫津路92号"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||
|campus|校区实体对象|body|true|Campus|Campus|
|&emsp;&emsp;id|校区唯一ID||false|integer(int64)||
|&emsp;&emsp;name|校区名称||true|string||
|&emsp;&emsp;address|校区地址||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除校区


**接口地址**:`/api/admin/campuses/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|校区ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# B02. 内容管理 - 食堂管理


## 分页查询食堂列表


**接口地址**:`/api/admin/canteens`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 创建食堂


**接口地址**:`/api/admin/canteens`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "学三食堂",
  "campusId": 1,
  "typeId": 1,
  "address": "青年湖西侧",
  "floor": "共三层",
  "openHours": "07:00-21:00",
  "avgPrice": 25,
  "introduction": "物美价廉，种类繁多",
  "openStatus": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|canteen|食堂实体对象|body|true|Canteen|Canteen|
|&emsp;&emsp;id|食堂唯一ID||false|integer(int64)||
|&emsp;&emsp;name|食堂名称||true|string||
|&emsp;&emsp;campusId|所属校区ID||true|integer(int64)||
|&emsp;&emsp;typeId|食堂类型ID||true|integer(int64)||
|&emsp;&emsp;address|详细地址||false|string||
|&emsp;&emsp;floor|楼层信息||false|string||
|&emsp;&emsp;openHours|营业时间||false|string||
|&emsp;&emsp;avgPrice|人均价格||false|integer(int32)||
|&emsp;&emsp;introduction|食堂介绍||false|string||
|&emsp;&emsp;score|综合评分||false|number(double)||
|&emsp;&emsp;tasteScore|口味评分||false|number(double)||
|&emsp;&emsp;environmentScore|环境评分||false|number(double)||
|&emsp;&emsp;serviceScore|服务评分||false|number(double)||
|&emsp;&emsp;liked|点赞数||false|integer(int32)||
|&emsp;&emsp;comments|评论数||false|integer(int32)||
|&emsp;&emsp;openStatus|营业状态 (0=休息, 1=营业)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据ID查询单个食堂


**接口地址**:`/api/admin/canteens/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|食堂ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新食堂信息


**接口地址**:`/api/admin/canteens/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "学三食堂",
  "campusId": 1,
  "typeId": 1,
  "address": "青年湖西侧",
  "floor": "共三层",
  "openHours": "07:00-21:00",
  "avgPrice": 25,
  "introduction": "物美价廉，种类繁多",
  "openStatus": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||
|canteen|食堂实体对象|body|true|Canteen|Canteen|
|&emsp;&emsp;id|食堂唯一ID||false|integer(int64)||
|&emsp;&emsp;name|食堂名称||true|string||
|&emsp;&emsp;campusId|所属校区ID||true|integer(int64)||
|&emsp;&emsp;typeId|食堂类型ID||true|integer(int64)||
|&emsp;&emsp;address|详细地址||false|string||
|&emsp;&emsp;floor|楼层信息||false|string||
|&emsp;&emsp;openHours|营业时间||false|string||
|&emsp;&emsp;avgPrice|人均价格||false|integer(int32)||
|&emsp;&emsp;introduction|食堂介绍||false|string||
|&emsp;&emsp;score|综合评分||false|number(double)||
|&emsp;&emsp;tasteScore|口味评分||false|number(double)||
|&emsp;&emsp;environmentScore|环境评分||false|number(double)||
|&emsp;&emsp;serviceScore|服务评分||false|number(double)||
|&emsp;&emsp;liked|点赞数||false|integer(int32)||
|&emsp;&emsp;comments|评论数||false|integer(int32)||
|&emsp;&emsp;openStatus|营业状态 (0=休息, 1=营业)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除食堂


**接口地址**:`/api/admin/canteens/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|食堂ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询食堂图片列表


**接口地址**:`/api/admin/canteens/{id}/files`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|食堂ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传食堂图片


**接口地址**:`/api/admin/canteens/{id}/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|食堂ID|path|true|integer(int64)||
|file|图片文件|query|true|file||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询所有食堂列表（不分页）


**接口地址**:`/api/admin/canteens/all`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>用于其他模块的下拉选择</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# B03. 内容管理 - 窗口管理


## 分页查询窗口列表


**接口地址**:`/api/admin/stalls`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 创建窗口


**接口地址**:`/api/admin/stalls`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "特色炒饭窗口",
  "canteenId": 1,
  "typeId": 2,
  "location": "二楼E05",
  "introduction": "提供各种口味的炒饭",
  "openHours": "10:00-20:00",
  "openStatus": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|stall|窗口实体对象|body|true|Stall|Stall|
|&emsp;&emsp;id|窗口唯一ID||false|integer(int64)||
|&emsp;&emsp;name|窗口名称||true|string||
|&emsp;&emsp;canteenId|所属食堂ID||true|integer(int64)||
|&emsp;&emsp;typeId|窗口类型ID||true|integer(int64)||
|&emsp;&emsp;location|窗口位置编号||false|string||
|&emsp;&emsp;introduction|窗口介绍||false|string||
|&emsp;&emsp;openHours|营业时间||false|string||
|&emsp;&emsp;avgPrice|人均价格||false|integer(int32)||
|&emsp;&emsp;score|综合评分||false|number(double)||
|&emsp;&emsp;tasteScore|口味评分||false|number(double)||
|&emsp;&emsp;environmentScore|环境评分||false|number(double)||
|&emsp;&emsp;serviceScore|服务评分||false|number(double)||
|&emsp;&emsp;priceScore|性价比评分||false|number(double)||
|&emsp;&emsp;comments|评论数||false|integer(int32)||
|&emsp;&emsp;openStatus|营业状态 (0=休息, 1=营业)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||
|&emsp;&emsp;totalScore|总评分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalTasteScore|口味总分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalEnvironmentScore|环境总分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalServiceScore|服务总分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalPriceScore|性价比总分（用于增量计算）||false|number(double)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据ID查询单个窗口


**接口地址**:`/api/admin/stalls/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|窗口唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新窗口信息


**接口地址**:`/api/admin/stalls/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "特色炒饭窗口",
  "canteenId": 1,
  "typeId": 2,
  "location": "二楼E05",
  "introduction": "提供各种口味的炒饭",
  "openHours": "10:00-20:00",
  "openStatus": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||
|stall|窗口实体对象|body|true|Stall|Stall|
|&emsp;&emsp;id|窗口唯一ID||false|integer(int64)||
|&emsp;&emsp;name|窗口名称||true|string||
|&emsp;&emsp;canteenId|所属食堂ID||true|integer(int64)||
|&emsp;&emsp;typeId|窗口类型ID||true|integer(int64)||
|&emsp;&emsp;location|窗口位置编号||false|string||
|&emsp;&emsp;introduction|窗口介绍||false|string||
|&emsp;&emsp;openHours|营业时间||false|string||
|&emsp;&emsp;avgPrice|人均价格||false|integer(int32)||
|&emsp;&emsp;score|综合评分||false|number(double)||
|&emsp;&emsp;tasteScore|口味评分||false|number(double)||
|&emsp;&emsp;environmentScore|环境评分||false|number(double)||
|&emsp;&emsp;serviceScore|服务评分||false|number(double)||
|&emsp;&emsp;priceScore|性价比评分||false|number(double)||
|&emsp;&emsp;comments|评论数||false|integer(int32)||
|&emsp;&emsp;openStatus|营业状态 (0=休息, 1=营业)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||
|&emsp;&emsp;totalScore|总评分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalTasteScore|口味总分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalEnvironmentScore|环境总分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalServiceScore|服务总分（用于增量计算）||false|number(double)||
|&emsp;&emsp;totalPriceScore|性价比总分（用于增量计算）||false|number(double)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除窗口


**接口地址**:`/api/admin/stalls/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|窗口ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# B04. 内容管理 - 菜品管理


## 分页查询菜品列表（后台管理）


**接口地址**:`/api/admin/dishes`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name|菜品名称关键字|query|false|string||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增菜品


**接口地址**:`/api/admin/dishes`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "扬州炒饭",
  "stallId": 5,
  "category": "主食",
  "price": 12.5,
  "description": "经典炒饭，配料丰富",
  "status": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dish|菜品实体对象|body|true|Dish|Dish|
|&emsp;&emsp;id|菜品唯一ID||false|integer(int64)||
|&emsp;&emsp;name|菜品名称||true|string||
|&emsp;&emsp;stallId|所属窗口ID||true|integer(int64)||
|&emsp;&emsp;category|菜品分类||false|string||
|&emsp;&emsp;price|价格||true|number||
|&emsp;&emsp;description|菜品描述||false|string||
|&emsp;&emsp;liked|被点赞数||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=下架, 1=上架)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改菜品信息


**接口地址**:`/api/admin/dishes`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "扬州炒饭",
  "stallId": 5,
  "category": "主食",
  "price": 12.5,
  "description": "经典炒饭，配料丰富",
  "status": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dish|菜品实体对象|body|true|Dish|Dish|
|&emsp;&emsp;id|菜品唯一ID||false|integer(int64)||
|&emsp;&emsp;name|菜品名称||true|string||
|&emsp;&emsp;stallId|所属窗口ID||true|integer(int64)||
|&emsp;&emsp;category|菜品分类||false|string||
|&emsp;&emsp;price|价格||true|number||
|&emsp;&emsp;description|菜品描述||false|string||
|&emsp;&emsp;liked|被点赞数||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=下架, 1=上架)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 根据ID查询单个菜品详情


**接口地址**:`/api/admin/dishes/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|菜品ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除菜品


**接口地址**:`/api/admin/dishes/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|菜品ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询菜品图片列表


**接口地址**:`/api/admin/dishes/{id}/files`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|菜品ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 上传菜品图片


**接口地址**:`/api/admin/dishes/{id}/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|菜品ID|path|true|integer(int64)||
|file|图片文件|query|true|file||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# B05. 内容管理 - 窗口类型管理


## 查询所有窗口类型列表


**接口地址**:`/api/admin/stall-types`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增窗口类型


**接口地址**:`/api/admin/stall-types`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "麻辣香锅",
  "icon": "http://example.com/icon_mlxg.png",
  "sort": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|stallType|窗口类型实体对象|body|true|StallType|StallType|
|&emsp;&emsp;id|窗口类型唯一ID||false|integer(int64)||
|&emsp;&emsp;name|类型名称||true|string||
|&emsp;&emsp;icon|类型的图标URL||false|string||
|&emsp;&emsp;sort|排序值 (用于调整显示顺序)||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改窗口类型信息


**接口地址**:`/api/admin/stall-types`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "麻辣香锅",
  "icon": "http://example.com/icon_mlxg.png",
  "sort": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|stallType|窗口类型实体对象|body|true|StallType|StallType|
|&emsp;&emsp;id|窗口类型唯一ID||false|integer(int64)||
|&emsp;&emsp;name|类型名称||true|string||
|&emsp;&emsp;icon|类型的图标URL||false|string||
|&emsp;&emsp;sort|排序值 (用于调整显示顺序)||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除窗口类型


**接口地址**:`/api/admin/stall-types/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|窗口类型ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# B06. 内容管理 - 食堂类型管理


## 查询所有食堂类型列表


**接口地址**:`/api/admin/canteen-types`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增食堂类型


**接口地址**:`/api/admin/canteen-types`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "学生食堂",
  "icon": "http://example.com/icon.png",
  "description": "主打家常菜",
  "sort": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|canteenType|食堂类型实体对象|body|true|CanteenType|CanteenType|
|&emsp;&emsp;id|食堂类型唯一ID||false|integer(int64)||
|&emsp;&emsp;name|类型名称||true|string||
|&emsp;&emsp;icon|类型的图标URL||false|string||
|&emsp;&emsp;description|类型描述||false|string||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;sort|排序值 (用于调整显示顺序)||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改食堂类型信息


**接口地址**:`/api/admin/canteen-types`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "学生食堂",
  "icon": "http://example.com/icon.png",
  "description": "主打家常菜",
  "sort": 1
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|canteenType|食堂类型实体对象|body|true|CanteenType|CanteenType|
|&emsp;&emsp;id|食堂类型唯一ID||false|integer(int64)||
|&emsp;&emsp;name|类型名称||true|string||
|&emsp;&emsp;icon|类型的图标URL||false|string||
|&emsp;&emsp;description|类型描述||false|string||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;sort|排序值 (用于调整显示顺序)||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除食堂类型


**接口地址**:`/api/admin/canteen-types/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|食堂类型ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# C01. 社区管理 - 用户管理


## 分页查询用户列表


**接口地址**:`/api/admin/users`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>支持按用户ID、昵称、邮箱进行筛选</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|用户ID|query|false|integer(int64)||
|nickname|用户昵称关键字|query|false|string||
|email|用户邮箱|query|false|string||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询单个用户详情


**接口地址**:`/api/admin/users/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>获取指定ID用户的详细信息</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|用户ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 部分更新用户信息


**接口地址**:`/api/admin/users/{id}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>支持更新用户状态等字段 (0=正常, 1=禁用)</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|用户ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# C02. 社区管理 - 博客管理


## 分页查询博客列表


**接口地址**:`/api/admin/blogs`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>支持按标题、用户ID、状态进行筛选</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|title|博客标题关键字|query|false|string||
|userId|作者用户ID|query|false|integer(int64)||
|status|博客状态 (0=待审核, 1=通过, 2=拒绝)|query|false|integer(int32)||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 管理员删除博客


**接口地址**:`/api/admin/blogs/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>将博客状态设置为管理员删除，实现逻辑删除</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|博客ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 审核博客


**接口地址**:`/api/admin/blogs/{id}/audit`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>通过或拒绝一篇待审核的博客</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|博客ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 设置博客特殊状态


**接口地址**:`/api/admin/blogs/{id}/specials`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>设置或取消置顶、加精</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|博客ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# C03. 社区管理 - 敏感词管理


## 分页查询敏感词列表


**接口地址**:`/api/admin/sensitive-words`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|word|敏感词内容关键词|query|false|string||
|category|敏感词分类|query|false|string||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增敏感词


**接口地址**:`/api/admin/sensitive-words`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "word": "",
  "category": "",
  "level": 0,
  "status": 0,
  "adminId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sensitiveWord|敏感词实体对象|body|true|SensitiveWord|SensitiveWord|
|&emsp;&emsp;id|敏感词唯一ID||false|integer(int64)||
|&emsp;&emsp;word|敏感词内容||false|string||
|&emsp;&emsp;category|分类 (如: '广告', '政治')||false|string||
|&emsp;&emsp;level|敏感等级 (1-5, 数字越大越敏感)||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=禁用, 1=启用)||false|integer(int32)||
|&emsp;&emsp;adminId|添加该词的管理员ID||false|integer(int64)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改敏感词


**接口地址**:`/api/admin/sensitive-words`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "word": "",
  "category": "",
  "level": 0,
  "status": 0,
  "adminId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sensitiveWord|敏感词实体对象|body|true|SensitiveWord|SensitiveWord|
|&emsp;&emsp;id|敏感词唯一ID||false|integer(int64)||
|&emsp;&emsp;word|敏感词内容||false|string||
|&emsp;&emsp;category|分类 (如: '广告', '政治')||false|string||
|&emsp;&emsp;level|敏感等级 (1-5, 数字越大越敏感)||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=禁用, 1=启用)||false|integer(int32)||
|&emsp;&emsp;adminId|添加该词的管理员ID||false|integer(int64)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除敏感词


**接口地址**:`/api/admin/sensitive-words/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|敏感词ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# C04. 社区管理 - 举报管理


## 分页查询举报列表


**接口地址**:`/api/admin/reports`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|reporterId|举报人ID|query|false|integer(int64)||
|targetType|被举报对象类型|query|false|integer(int32)||
|status|举报状态|query|false|integer(int32)||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除举报记录


**接口地址**:`/api/admin/reports/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|举报ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 处理举报


**接口地址**:`/api/admin/reports/{id}/handle`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|举报ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 批量处理举报


**接口地址**:`/api/admin/reports/batch-handle`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# D01. 运营管理 - 轮播图管理


## 分页查询轮播图列表


**接口地址**:`/api/admin/banners`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增轮播图


**接口地址**:`/api/admin/banners`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "title": "",
  "imageUrl": "",
  "linkUrl": "",
  "sort": 0,
  "status": 0,
  "startTime": "",
  "endTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|banner|轮播图实体对象|body|true|Banner|Banner|
|&emsp;&emsp;id|轮播图唯一ID||false|integer(int64)||
|&emsp;&emsp;title|标题||false|string||
|&emsp;&emsp;imageUrl|图片的URL||false|string||
|&emsp;&emsp;linkUrl|点击后跳转的链接||false|string||
|&emsp;&emsp;sort|排序值 (用于调整显示顺序)||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=禁用, 1=启用)||false|integer(int32)||
|&emsp;&emsp;startTime|展示开始时间 (用于定时上线)||false|string(date-time)||
|&emsp;&emsp;endTime|展示结束时间 (用于定时下线)||false|string(date-time)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改轮播图


**接口地址**:`/api/admin/banners`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "title": "",
  "imageUrl": "",
  "linkUrl": "",
  "sort": 0,
  "status": 0,
  "startTime": "",
  "endTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|banner|轮播图实体对象|body|true|Banner|Banner|
|&emsp;&emsp;id|轮播图唯一ID||false|integer(int64)||
|&emsp;&emsp;title|标题||false|string||
|&emsp;&emsp;imageUrl|图片的URL||false|string||
|&emsp;&emsp;linkUrl|点击后跳转的链接||false|string||
|&emsp;&emsp;sort|排序值 (用于调整显示顺序)||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=禁用, 1=启用)||false|integer(int32)||
|&emsp;&emsp;startTime|展示开始时间 (用于定时上线)||false|string(date-time)||
|&emsp;&emsp;endTime|展示结束时间 (用于定时下线)||false|string(date-time)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除轮播图


**接口地址**:`/api/admin/banners/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|轮播图ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# D02. 运营管理 - 优惠券管理


## 分页查询优惠券模板列表


**接口地址**:`/api/admin/vouchers`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|title|优惠券标题关键词|query|false|string||
|status|优惠券状态 (1=正常, 2=暂停)|query|false|integer(int32)||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 创建优惠券模板


**接口地址**:`/api/admin/vouchers`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "title": "",
  "description": "",
  "type": 0,
  "price": 0,
  "discount": 0,
  "minAmount": 0,
  "canteenId": 0,
  "startTime": "",
  "endTime": "",
  "stock": 0,
  "requiredCredits": 0,
  "status": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|voucher|优惠券模板实体对象|body|true|Voucher|Voucher|
|&emsp;&emsp;id|优惠券模板唯一ID||false|integer(int64)||
|&emsp;&emsp;title|优惠券标题||false|string||
|&emsp;&emsp;description|详细描述||false|string||
|&emsp;&emsp;type|优惠券类型 (0=满减券, 1=折扣券)||false|integer(int32)||
|&emsp;&emsp;price|优惠金额 (用于满减券)||false|number||
|&emsp;&emsp;discount|折扣率 (用于折扣券, 如0.88代表88折)||false|number||
|&emsp;&emsp;minAmount|最低消费金额||false|number||
|&emsp;&emsp;canteenId|限定使用的食堂ID (为NULL则全场通用)||false|integer(int64)||
|&emsp;&emsp;startTime|生效时间||false|string(date-time)||
|&emsp;&emsp;endTime|过期时间||false|string(date-time)||
|&emsp;&emsp;stock|库存数量||false|integer(int32)||
|&emsp;&emsp;requiredCredits|兑换所需积分||false|integer(int32)||
|&emsp;&emsp;status|优惠券状态 (0=未开始, 1=进行中, 2=已结束)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取单个优惠券详情


**接口地址**:`/api/admin/vouchers/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新优惠券模板


**接口地址**:`/api/admin/vouchers/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "title": "",
  "description": "",
  "type": 0,
  "price": 0,
  "discount": 0,
  "minAmount": 0,
  "canteenId": 0,
  "startTime": "",
  "endTime": "",
  "stock": 0,
  "requiredCredits": 0,
  "status": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||
|voucher|优惠券模板实体对象|body|true|Voucher|Voucher|
|&emsp;&emsp;id|优惠券模板唯一ID||false|integer(int64)||
|&emsp;&emsp;title|优惠券标题||false|string||
|&emsp;&emsp;description|详细描述||false|string||
|&emsp;&emsp;type|优惠券类型 (0=满减券, 1=折扣券)||false|integer(int32)||
|&emsp;&emsp;price|优惠金额 (用于满减券)||false|number||
|&emsp;&emsp;discount|折扣率 (用于折扣券, 如0.88代表88折)||false|number||
|&emsp;&emsp;minAmount|最低消费金额||false|number||
|&emsp;&emsp;canteenId|限定使用的食堂ID (为NULL则全场通用)||false|integer(int64)||
|&emsp;&emsp;startTime|生效时间||false|string(date-time)||
|&emsp;&emsp;endTime|过期时间||false|string(date-time)||
|&emsp;&emsp;stock|库存数量||false|integer(int32)||
|&emsp;&emsp;requiredCredits|兑换所需积分||false|integer(int32)||
|&emsp;&emsp;status|优惠券状态 (0=未开始, 1=进行中, 2=已结束)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除优惠券


**接口地址**:`/api/admin/vouchers/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 部分更新优惠券（如状态）


**接口地址**:`/api/admin/vouchers/{id}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# D03. 运营管理 - 系统公告管理


## 分页查询公告列表


**接口地址**:`/api/admin/notices`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|title|公告标题关键字|query|false|string||
|status|公告状态|query|false|integer(int32)||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增或发布公告


**接口地址**:`/api/admin/notices`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "adminId": 0,
  "title": "",
  "content": "",
  "type": 0,
  "status": 0,
  "publishTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|notice|系统公告实体对象|body|true|Notice|Notice|
|&emsp;&emsp;id|公告唯一ID||false|integer(int64)||
|&emsp;&emsp;adminId|发布公告的管理员ID||false|integer(int64)||
|&emsp;&emsp;title|公告标题||false|string||
|&emsp;&emsp;content|公告内容 (支持富文本)||false|string||
|&emsp;&emsp;type|公告类型 (0=普通, 1=重要)||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=草稿, 1=已发布, 2=已撤回)||false|integer(int32)||
|&emsp;&emsp;publishTime|预定发布时间 (用于定时发布)||false|string(date-time)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改公告


**接口地址**:`/api/admin/notices`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "adminId": 0,
  "title": "",
  "content": "",
  "type": 0,
  "status": 0,
  "publishTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|notice|系统公告实体对象|body|true|Notice|Notice|
|&emsp;&emsp;id|公告唯一ID||false|integer(int64)||
|&emsp;&emsp;adminId|发布公告的管理员ID||false|integer(int64)||
|&emsp;&emsp;title|公告标题||false|string||
|&emsp;&emsp;content|公告内容 (支持富文本)||false|string||
|&emsp;&emsp;type|公告类型 (0=普通, 1=重要)||false|integer(int32)||
|&emsp;&emsp;status|状态 (0=草稿, 1=已发布, 2=已撤回)||false|integer(int32)||
|&emsp;&emsp;publishTime|预定发布时间 (用于定时发布)||false|string(date-time)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除公告


**接口地址**:`/api/admin/notices/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|公告ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# D04. 运营管理 - 激励规则管理


## 分页查询激励规则列表


**接口地址**:`/api/admin/incentive-rules`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ruleName|规则名称关键字|query|false|string||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增激励规则


**接口地址**:`/api/admin/incentive-rules`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "actionType": "",
  "credits": 0,
  "dailyLimit": 0,
  "description": "",
  "status": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|incentiveRule|用户激励规则实体对象|body|true|IncentiveRule|IncentiveRule|
|&emsp;&emsp;id|规则唯一ID||false|integer(int64)||
|&emsp;&emsp;actionType|行为类型 (如: 'DAILY_SIGN_IN', 'POST_BLOG')，程序中使用的唯一标识||false|string||
|&emsp;&emsp;credits|完成该行为奖励的积分||false|integer(int32)||
|&emsp;&emsp;dailyLimit|每日可通过该行为获取积分的次数上限||false|integer(int32)||
|&emsp;&emsp;description|规则描述||false|string||
|&emsp;&emsp;status|规则状态 (0=禁用, 1=启用)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改激励规则


**接口地址**:`/api/admin/incentive-rules`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "actionType": "",
  "credits": 0,
  "dailyLimit": 0,
  "description": "",
  "status": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|incentiveRule|用户激励规则实体对象|body|true|IncentiveRule|IncentiveRule|
|&emsp;&emsp;id|规则唯一ID||false|integer(int64)||
|&emsp;&emsp;actionType|行为类型 (如: 'DAILY_SIGN_IN', 'POST_BLOG')，程序中使用的唯一标识||false|string||
|&emsp;&emsp;credits|完成该行为奖励的积分||false|integer(int32)||
|&emsp;&emsp;dailyLimit|每日可通过该行为获取积分的次数上限||false|integer(int32)||
|&emsp;&emsp;description|规则描述||false|string||
|&emsp;&emsp;status|规则状态 (0=禁用, 1=启用)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除激励规则


**接口地址**:`/api/admin/incentive-rules/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>注意：通常不建议删除，而是修改状态为禁用</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 启用-禁用激励规则


**接口地址**:`/api/admin/incentive-rules/{id}/status`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||
|status||query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取激励统计信息


**接口地址**:`/api/admin/incentive-rules/statistics`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# D05. 运营管理 - 日志管理


## 分页查询审计日志


**接口地址**:`/api/admin/logs`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|operatorName|操作人名称|query|false|string||
|module|操作模块|query|false|string||
|ip|IP地址|query|false|string||
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 查询日志详情


**接口地址**:`/api/admin/logs/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|日志ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除日志记录


**接口地址**:`/api/admin/logs/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|日志ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 批量删除日志记录


**接口地址**:`/api/admin/logs/batch`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 清理过期日志


**接口地址**:`/api/admin/logs/cleanup`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|days|保留天数|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# E01. 平台设置 - 权限管理


## 查询所有权限列表


**接口地址**:`/api/admin/permissions`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# E02. 平台设置 - 角色管理


## getRoles


**接口地址**:`/api/admin/roles`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current||query|false|integer(int32)||
|size||query|false|integer(int32)||
|name||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## addRole


**接口地址**:`/api/admin/roles`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "内容管理员",
  "description": "负责管理食堂、窗口、菜品等核心基础数据"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|role|角色实体对象|body|true|Role|Role|
|&emsp;&emsp;id|角色唯一ID||false|integer(int64)||
|&emsp;&emsp;name|角色名称||false|string||
|&emsp;&emsp;description|角色描述||false|string||
|&emsp;&emsp;status|角色状态 (0=正常, 1=禁用)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## updateRole


**接口地址**:`/api/admin/roles`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "name": "内容管理员",
  "description": "负责管理食堂、窗口、菜品等核心基础数据"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|role|角色实体对象|body|true|Role|Role|
|&emsp;&emsp;id|角色唯一ID||false|integer(int64)||
|&emsp;&emsp;name|角色名称||false|string||
|&emsp;&emsp;description|角色描述||false|string||
|&emsp;&emsp;status|角色状态 (0=正常, 1=禁用)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取角色权限ID列表


**接口地址**:`/api/admin/roles/{id}/permissions`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新角色权限


**接口地址**:`/api/admin/roles/{id}/permissions`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
[]
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||
|integers|integer|body|true|array||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# E02. 平台设置 - 人员管理


## 分页查询管理员列表


**接口地址**:`/api/admin/admins`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|username|管理员用户名关键词|query|false|string||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 新增管理员


**接口地址**:`/api/admin/admins`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>创建一个新的管理员账号，密码默认为&#39;123456&#39;</p>



**请求示例**:


```javascript
{
  "username": "",
  "name": "",
  "roleId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|adminCreateDTO|创建管理员时使用的数据传输对象|body|true|AdminCreateDTO|AdminCreateDTO|
|&emsp;&emsp;username|管理员登录账号||true|string||
|&emsp;&emsp;name|管理员真实姓名或职位||true|string||
|&emsp;&emsp;roleId|要分配给新管理员的角色ID||true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 修改管理员信息


**接口地址**:`/api/admin/admins`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>只能修改姓名、状态等基本信息，不能修改密码</p>



**请求示例**:


```javascript
{
  "username": "admin_content",
  "avatar": "",
  "password": "",
  "name": "张三",
  "roleId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|admin|管理员实体对象|body|true|Admin|Admin|
|&emsp;&emsp;id|管理员唯一ID||false|integer(int64)||
|&emsp;&emsp;username|管理员登录账号||true|string||
|&emsp;&emsp;avatar|管理员头像URL||false|string||
|&emsp;&emsp;password|管理员密码||false|string||
|&emsp;&emsp;name|管理员真实姓名或职位||false|string||
|&emsp;&emsp;roleId|所属角色ID||false|integer(int64)||
|&emsp;&emsp;status|管理员状态 (0=正常, 1=禁用)||false|integer(int32)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除管理员


**接口地址**:`/api/admin/admins/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|管理员唯一标识ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 重置管理员密码


**接口地址**:`/api/admin/admins/{id}/reset-password`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|管理员ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# F01. 工具箱 - 统计面板


## 获取食堂分布统计


**接口地址**:`/api/admin/statistics/canteen-distribution`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取系统概览统计


**接口地址**:`/api/admin/statistics/overview`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取热门菜品排行


**接口地址**:`/api/admin/statistics/popular-dishes`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取用户增长趋势


**接口地址**:`/api/admin/statistics/user-growth`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# F02. 工具箱 - 任务管理


## 分页查询任务列表


**接口地址**:`/api/admin/tasks`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|taskName|任务名称|query|false|string||
|status|任务状态|query|false|integer(int32)||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 创建新任务


**接口地址**:`/api/admin/tasks`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "taskName": "",
  "taskGroup": "",
  "cronExpression": "",
  "status": 0,
  "description": "",
  "lastRunTime": "",
  "nextRunTime": "",
  "createTime": "",
  "updateTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|scheduledTaskDTO|定时任务数据传输对象|body|true|ScheduledTaskDTO|ScheduledTaskDTO|
|&emsp;&emsp;id|任务ID||false|integer(int64)||
|&emsp;&emsp;taskName|任务名称||true|string||
|&emsp;&emsp;taskGroup|任务分组||true|string||
|&emsp;&emsp;cronExpression|CRON表达式||true|string||
|&emsp;&emsp;status|任务状态 (0=停止, 1=运行中, 2=暂停)||true|integer(int32)||
|&emsp;&emsp;description|任务描述||false|string||
|&emsp;&emsp;lastRunTime|上次执行时间||false|string(date-time)||
|&emsp;&emsp;nextRunTime|下次计划执行时间||false|string(date-time)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新任务


**接口地址**:`/api/admin/tasks`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "taskName": "",
  "taskGroup": "",
  "cronExpression": "",
  "status": 0,
  "description": "",
  "lastRunTime": "",
  "nextRunTime": "",
  "createTime": "",
  "updateTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|scheduledTaskDTO|定时任务数据传输对象|body|true|ScheduledTaskDTO|ScheduledTaskDTO|
|&emsp;&emsp;id|任务ID||false|integer(int64)||
|&emsp;&emsp;taskName|任务名称||true|string||
|&emsp;&emsp;taskGroup|任务分组||true|string||
|&emsp;&emsp;cronExpression|CRON表达式||true|string||
|&emsp;&emsp;status|任务状态 (0=停止, 1=运行中, 2=暂停)||true|integer(int32)||
|&emsp;&emsp;description|任务描述||false|string||
|&emsp;&emsp;lastRunTime|上次执行时间||false|string(date-time)||
|&emsp;&emsp;nextRunTime|下次计划执行时间||false|string(date-time)||
|&emsp;&emsp;createTime|创建时间||false|string(date-time)||
|&emsp;&emsp;updateTime|更新时间||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除任务


**接口地址**:`/api/admin/tasks/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|任务ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 立即执行任务


**接口地址**:`/api/admin/tasks/{id}/execute`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|任务ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取任务执行日志


**接口地址**:`/api/admin/tasks/{id}/logs`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|任务ID|path|true|integer(int64)||
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 暂停任务


**接口地址**:`/api/admin/tasks/{id}/pause`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|任务ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 恢复任务


**接口地址**:`/api/admin/tasks/{id}/resume`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|任务ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 启动任务


**接口地址**:`/api/admin/tasks/{id}/start`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|任务ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 停止任务


**接口地址**:`/api/admin/tasks/{id}/stop`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|任务ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 验证CRON表达式


**接口地址**:`/api/admin/tasks/validate-cron`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|cronExpression||query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# F03. 工具箱 - API文档管理


## 获取API文档配置


**接口地址**:`/api/admin/api-docs/config`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 更新API文档配置


**接口地址**:`/api/admin/api-docs/config`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 检查API服务健康状态


**接口地址**:`/api/admin/api-docs/health`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|environment|环境标识|query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API性能监控数据


**接口地址**:`/api/admin/api-docs/performance`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取热门API排行


**接口地址**:`/api/admin/api-docs/popular`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 生成API文档快照


**接口地址**:`/api/admin/api-docs/snapshot`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name|快照名称|query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API统计信息


**接口地址**:`/api/admin/api-docs/statistics`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API版本信息


**接口地址**:`/api/admin/api-docs/versions`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# F04. 工具箱 - 审计日志


## 分页查询审计日志


**接口地址**:`/api/admin/audit-logs`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||
|logType|日志类型 (1=操作日志, 2=登录日志, 3=安全日志)|query|false|integer(int32)||
|startTime|开始时间 (格式: yyyy-MM-dd)|query|false|string||
|endTime|结束时间 (格式: yyyy-MM-dd)|query|false|string||
|keyword|关键词搜索|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 删除单条日志记录


**接口地址**:`/api/admin/audit-logs/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|日志ID|path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 清理指定天数前的日志


**接口地址**:`/api/admin/audit-logs/cleanup`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|days|保留最近的天数|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 导出审计日志为Excel


**接口地址**:`/api/admin/audit-logs/export`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|logType|日志类型 (1=操作日志, 2=登录日志, 3=安全日志)|query|false|integer(int32)||
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||
|keyword|关键词搜索|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取登录统计


**接口地址**:`/api/admin/audit-logs/login-stats`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取操作统计


**接口地址**:`/api/admin/audit-logs/operation-stats`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取审计统计信息


**接口地址**:`/api/admin/audit-logs/statistics`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# F05. 工具箱 - API管理


## 分页查询API访问日志


**接口地址**:`/api/admin/api-management/access-logs`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|current|当前页码|query|false|integer(int32)||
|size|每页数量|query|false|integer(int32)||
|apiPath|API路径|query|false|string||
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||
|status|状态码|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API调用趋势


**接口地址**:`/api/admin/api-management/call-trends`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||
|granularity|时间粒度 (hour/day/month)|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API错误率统计


**接口地址**:`/api/admin/api-management/error-rates`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API错误统计


**接口地址**:`/api/admin/api-management/error-stats`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 导出API访问日志


**接口地址**:`/api/admin/api-management/export`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||
|apiPath|API路径|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API性能统计


**接口地址**:`/api/admin/api-management/performance-stats`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|startTime|开始时间|query|false|string||
|endTime|结束时间|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取热门API排行


**接口地址**:`/api/admin/api-management/popular-apis`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|limit|返回数量|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取慢查询API


**接口地址**:`/api/admin/api-management/slow-apis`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|limit|返回数量|query|false|integer(int32)||
|threshold|响应时间阈值(毫秒)|query|false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


## 获取API统计信息


**接口地址**:`/api/admin/api-management/statistics`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


# F06. 工具箱 - 数据导出


## 导出数据库备份


**接口地址**:`/api/admin/toolbox/export-db`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|400|Bad Request|Result|
|500|Internal Server Error|Result|


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-400**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```


**响应状态码-500**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|errorMsg||string||
|data||object||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"success": true,
	"errorMsg": "",
	"data": {},
	"total": 0
}
```