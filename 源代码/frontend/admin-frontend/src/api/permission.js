import request from '@/utils/request'

// 获取全部权限列表
export function getAllPermissions() {
  return request({
    url: '/admin/permissions',
    method: 'get'
  })
}
