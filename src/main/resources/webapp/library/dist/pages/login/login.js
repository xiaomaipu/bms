Page({
    data: {
        userName: '',
        passWord: ''

    },
    onLoad: function(options) {
        // 生命周期函数--监听页面加 String2
    },
    onReady: function() {
        // 生命周期函数--监听页面初次渲染完成

    },
    onShow: function() {
        // 生命周期函数--监听页面显示



    },
    onHide: function() {
        // 生命周期函数--监听页面隐藏

    },
    loginAction: function() {
        console.log(this.data.userName)
        console.log(this.data.passWord)
        var _self = this;
        wx.request({
            url: getApp().globalData.baseUrl + '/rest/user/userInfo',
            data: {
                userName: this.data.userName,
                passWord: this.data.passWord
            },
            success: function(res) {
                if (res.data.code == 200) {
                    console.log(res.data.obj.id)
                    wx.setStorageSync('openId', res.data.obj.id);
                    wx.setStorageSync('name', res.data.obj.nickName);
                    wx.switchTab({
                        url: '../mine/mine'
                    })

                }else{
                    wx.showToast({
                        title: res.data.des,
                        icon: 'none',
                        duration: 2000
                      })
                }


            }
        })
    },
    CodeInput1: function(e) {
        this.setData({
            userName: e.detail.value
        })
    },
    CodeInput2: function(e) {
        this.setData({
            passWord: e.detail.value
        })
    },

})