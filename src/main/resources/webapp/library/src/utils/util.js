const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
const nameFit = name => {
  if (name) {
    return name + "读书郎"
  } else {
    return '读书郎'
  }
}
const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function wxPromisify(fn, type = '') {
  return function(obj = {}) {
    return new Promise((resolve, reject) => {
      obj.success = function(res) {
        if (type == 'request') {
          if (res.data.code == 200) {
            resolve(res)
          } else {
            reject(res)
          }
        } else if (type == 'upfile') {
          if (JSON.parse(res.data).code == 200) {
            resolve(res)
          } else {
            reject(res)
          }
        } else {
          resolve()
        }

      }
      obj.fail = function(res) {
        reject(res)
      }

      fn(obj)
    })
  }
}


module.exports = {
  formatTime: formatTime,
  wxPromisify: wxPromisify
}