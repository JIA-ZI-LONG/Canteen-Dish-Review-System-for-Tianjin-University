import request from './Request'

// 综合搜索
export function search(params) {
  return request({
    url: '/api/search',
    method: 'get',
    params
  })
}

// 最近 10 条搜索历史
export function getHistory() {
  return request({
    url: '/api/search/history',
    method: 'get'
  })
}

// 删除单条历史
export function deleteHistory(id) {
  return request({
    url: `/api/search/history/${id}`,
    method: 'delete'
  })
}

// 清空历史
export function clearHistory() {
  return request({
    url: '/api/search/history',
    method: 'delete'
  })
}
