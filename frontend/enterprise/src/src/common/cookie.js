export function setCookie(key, value, expireDays) {
  var d = new Date()
  d.setTime(d.getTime() + (expireDays * 24 * 60 * 60 * 1000))
  var expires = "expires=" + d.toUTCString()
  console.info(key + "=" + value + "; " + expires)
  document.cookie = key + "=" + value + "; " + expireDays
  console.info(document.cookie)
}


export function getCookie(key) {
  var name = key + "="
  var ca = document.cookie.split(';')
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i]
    while (c.charAt(0) == ' ') {
      c = c.substring(1)
    }
    if (c.indexOf(name) != -1) {
      return c.substring(name.length, c.length)
    }
  }
  return ""
}