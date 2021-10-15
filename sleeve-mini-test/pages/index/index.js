import {
  Base64
} from 'js-base64'

Page({
  onGetToken() {
    // code
    wx.login({
      success: (res) => {
        if (res.code) {
          wx.request({
            url: 'http://localhost:8081/v1/token',
            method: 'POST',
            data: {
              account: res.code,
              type: 0
            },
            success: (res) => {
              console.log(res.data)
              const code = res.statusCode.toString()
              if (code.startsWith('2')) {
                wx.setStorageSync('token', res.data.token)
              }
            }
          })
        }
      }
    })
  },

  onVerifyToken() {
    wx.request({
      url: 'http://localhost:8081/v1/token/verify',
      method: 'POST',
      data: {
        token: wx.getStorageSync('token')
      },
      success: res => {
        console.log(res.data)
      }
    })
  },

  onTestInterceptor(){
    wx.request({
      url: 'http://localhost:8081/v1/banner/name/b-1',
      method: 'GET',
      header:{
        'Authorization': this._getBearerToken()
      },
      success: res => {
        console.log(res.data)
      }
    })
  },

  onCollectCoupon() {
    wx.request({
      url: 'http://localhost:8081/v1/coupon/collect/4',
      method: 'POST',
      success: res => {
        console.log(res.data)
      },
      header: {
        'Authorization': this._getBearerToken()
      }
    })
  },

  onGetMyCoupons() {
    wx.request({
      url: 'http://localhost:8081/v1/coupon/myself/by/status/1',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        'Authorization': this._getBearerToken()
      }
    })
  },

  onGetCouponAvailable() {
    wx.request({
  url:'http://localhost:8081/v1/coupon/myself/available/with_category',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },

  onPlaceOrder1() {
    wx.request({
      url: 'http://localhost:8081/v1/order',
      method: 'POST',
      success: res => {
        console.log(res.data)
      },
      data: { "total_price": 1, "final_total_price": 1, "coupon_id": null, "sku_info_list": [{ "id": 15, "count": "5" }], "address": { "user_name": "张三", "national_code": "510000", "postal_code": "510000", "city": "广州市", "province": "广东省", "county": "海珠区", "detail": "397号", "mobile": "020-811" } },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },

  onPlaceOrder2() {
    wx.request({
      url: 'http://localhost:8081/v1/order',
      method: 'POST',
      success: res => {
        console.log(res.data)
      },
      data: { "total_price": 2798, "final_total_price": 2568, "coupon_id": 7, "sku_info_list": [{ "id": 30, "count": 2 }], "address": { "user_name": "张三", "national_code": "510000", "postal_code": "510000", "city": "广州市", "province": "广东省", "county": "海珠区", "detail": "397号", "mobile": "020-8118" } },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },

  onGetUnpaidOrders() {
    wx.request({
      url: 'http://localhost:8081/v1/order/status/unpaid',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },


  onGetAllOrders() {
    wx.request({
      url: 'http://localhost:8081/v1/order/by/status/0',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },

  onGetOrderDetail() {
    wx.request({
      url: 'http://localhost:8081/v1/order/detail/278',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },

  onPrepayOrder() {
    wx.request({
      url: 'http://localhost:8081/v1/payment/pay/order/304',
      method: 'POST',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },

  onPay() {
    wx.request({
      url: 'http://localhost:8081/v1/payment/pay/order/305',
      method: 'POST',
      success: res => {
        wx.requestPayment(res.data)
      },
      header: {
        Authorization: this._getBearerToken()
      }
    })
  },

  onGetBookDetail(){
    wx.request({
      url: 'http://localhost:3000/v1/book/1120/detail',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._encode()
      }
    })
  },

  onBookSearch() {
    wx.request({
      url: 'http://localhost:3000/v1/book/search',
      method: 'GET',
      data:{
        q:'韩寒',
        count:5
      },
      // like key%
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._encode()
      }
    })
  },

  onGetMyFavorsBookCount(){
    wx.request({
      url: 'http://localhost:3000/v1/book/favor/count',
      method: 'GET',
      // like key%
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._encode()
      }
    })
  },

  onGetBookFavor() {
    wx.request({
      url: 'http://localhost:3000/v1/book/1120/favor',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._encode()
      }
    })
  },

  onGetComments() {
    wx.request({
      url: 'http://localhost:3000/v1/book/1120/short_comment',
      method: 'GET',
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._encode()
      }
    })
  },

  onAddShortComment() {
    wx.request({
      url: 'http://localhost:3000/v1/book/add/short_comment',
      method: 'POST',
      data: {
        content:'春风十里不如有你春风十里不如有你',
        book_id:1120
      },
      // like key%
      success: res => {
        console.log(res.data)
      },
      header: {
        Authorization: this._encode()
      }
    })
  },

  _encode() {
    // account:password
    // token
    // token:
    const token = wx.getStorageSync('token')
    const base64 = Base64.encode(token + ':')
    // Authorization:Basic base64(account:password)
    return 'Basic ' + base64
  },

  _getBearerToken(){
    const token = wx.getStorageSync('token')
    return 'Bearer ' + token
  }
})