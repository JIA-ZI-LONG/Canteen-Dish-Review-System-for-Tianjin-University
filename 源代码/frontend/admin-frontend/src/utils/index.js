/**
 * 工具方法集合
 * 目前仅提供 parseTime，如后续有更多公共方法可在此处扩展
 */

/**
 * 将时间转换为指定格式的字符串
 * @param {Date | number | string} time - 要解析的时间
 * @param {string} cFormat - 输出格式，默认 '{y}-{m}-{d} {h}:{i}:{s}'
 * @returns {string} 格式化后的时间字符串
 */
export function parseTime(time, cFormat = '{y}-{m}-{d} {h}:{i}:{s}') {
  if (!time) {
    return ''
  }

  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^\d+$/.test(time))) {
      // support "1548221490638"
      time = parseInt(time)
    }
    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000
    }
    date = new Date(time)
  }

  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = cFormat.replace(/\{([ymdhisa])+\}/g, (result, key) => {
    const value = formatObj[key]
    // Note: Sunday (0) -> 7
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
    return value.toString().padStart(2, '0')
  })
  return time_str
}
