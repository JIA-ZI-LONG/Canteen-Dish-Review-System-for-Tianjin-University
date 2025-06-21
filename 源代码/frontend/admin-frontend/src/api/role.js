import request from '@/utils/request'

// 获取角色列表 (支持分页和名称搜索)
export function getRoleList(params) {
  return request({
    url: '/admin/roles',
    method: 'get',
    params
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/admin/roles',
    method: 'post',
    data
  })
}

// 更新角色
export function updateRole(data) {
  return request({
    url: '/admin/roles',
    method: 'put',
    data
  })
}

// 删除角色
export function deleteRole(id) {
  return request({
    url: '/admin/roles/' + id,
    method: 'delete'
  })
}

// 修改角色状态
export function changeRoleStatus(id, status) {
  var data = {
    id: id,
    status: status
  }
  return request({
    url: '/admin/roles/status',
    method: 'put',
    data: data
  })
}

// ================= 权限相关 =================
// 获取角色已分配的权限ID列表
export function getRolePermissions(id) {
  return request({
    url: `/admin/roles/${id}/permissions`,
    method: 'get'
  })
}

// 更新角色的权限ID列表
export function updateRolePermissions(id, permissionIds) {
  return request({
    url: `/admin/roles/${id}/permissions`,
    method: 'put',
    data: permissionIds
  })
}
