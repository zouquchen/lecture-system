import CryptoJS from 'crypto-js'

// 需要和后端一致
const KEY = CryptoJS.enc.Utf8.parse('IOT80YCbsM8ijmBV')
const IV = CryptoJS.enc.Utf8.parse('WKcJeGjIKGTy3VNW')

export default {

  /**
   * 加密
   * @param {*} word
   * @param {*} keyStr
   * @param {*} ivStr
   */
  encrypt(word, keyStr, ivStr) {
    let key = KEY
    let iv = IV
    if (keyStr) {
      key = CryptoJS.enc.Utf8.parse(keyStr)
      iv = CryptoJS.enc.Utf8.parse(ivStr)
    }
    const srcs = CryptoJS.enc.Utf8.parse(word)
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {
      iv: iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.ZeroPadding
    })
    return CryptoJS.enc.Base64.stringify(encrypted.ciphertext)
  },

  /**
   * 解密
   * @param {*} word
   * @param {*} keyStr
   * @param {*} ivStr
   */
  decrypt(word, keyStr, ivStr) {
    let key = KEY
    let iv = IV

    if (keyStr) {
      key = CryptoJS.enc.Utf8.parse(keyStr)
      iv = CryptoJS.enc.Utf8.parse(ivStr)
    }

    const base64 = CryptoJS.enc.Base64.parse(word)
    const src = CryptoJS.enc.Base64.stringify(base64)

    const decrypt = CryptoJS.AES.decrypt(src, key, {
      iv: iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.ZeroPadding
    })

    const decryptedStr = decrypt.toString(CryptoJS.enc.Utf8)
    return decryptedStr.toString()
  }
}

