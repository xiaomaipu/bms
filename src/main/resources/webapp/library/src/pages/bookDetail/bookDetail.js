Page({
    data: {
        bookmsg: {},
        msg: '',
        talkMsg: '',
        col_style: 'collection',
        col_msg: '',
        bookid: '',
        booktime: '',
        colList: []

    },
    onLoad: function(options) {
        // 生命周期函数--监听页面加 String2
        this.getComAction(options.id);
        console.log(options.id)
        this.setData({
            bookid: options.id,
            booktime: options.time

        })
        if (options.souce == 1) {
            this.setData({
                msg: '距离还书日还有:'
            })
            this.bookInfo(options.id, options.time);
        } else {
            this.setData({
                msg: '此书可借读:'
            })
            this.bookInfo(options.id, options.time);
        }

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

    bookInfo: function(id, time) {
        wx.showLoading({
            title: '加载中',
        })
        var _self = this;

        wx.request({
            url: getApp().globalData.baseUrl + '/rest/book/detail',
            method: 'get',
            data: {
                id: id,
                borrowDate: time,
                userId: wx.getStorageSync('openId')
            },
            success: function(res) {
                wx.hideLoading()
                if (res.data.code == 200) {
                    _self.setData({
                        bookmsg: res.data.obj
                    })
                    console.log(res.data.obj)
                    if (res.data.obj.isColl) {
                        _self.setData({
                            col_style: 'collection',
                            col_msg: "已收藏"
                        })
                    } else {
                        console.log("xasdasdasdasd")
                        _self.setData({
                            col_style: 'noCollection',
                            col_msg: "收藏"
                        })
                    }
                } else {

                }
            }
        })

    },
    checkAction: function(e) {
        wx.showLoading({
            title: '加载中',
        })
        var code = e.currentTarget.dataset.code;
        var type = 0;
        if (e.currentTarget.dataset.type) {
            type = 0
        } else {
            type = 1
        }
        var _self = this;
        wx.request({
            url: getApp().globalData.baseUrl + '/rest/user/collect/saveInfo?userId=' + wx.getStorageSync('openId') + '&bookId=' + code + "&type=" + type,
            method: 'post',
            success: function(res) {
                console.log(res)
                wx.hideLoading()
                if (res.data.code == 200) {
                    _self.bookInfo(_self.data.bookid, _self.data.booktime);
                } else {


                }
            }
        })



    },
    bindTextAreaBlur: function(e) {
        console.log(e.detail.value)
        if (e.detail.value) {
            this.setData({
                talkMsg: e.detail.value
            })

        }

    },
    talkAction: function() {

        var con = this.data.talkMsg;
        if (con.length > 0) {
            wx.showLoading({
                title: '加载中',
            })
            var _self = this;
            wx.request({
                url: getApp().globalData.baseUrl + '/rest/book/discuss',
                data: {
                    userId: wx.getStorageSync('openId'),
                    bookId: _self.data.bookmsg.id,
                    content: con
                },
                success: function(res) {
                    wx.hideLoading()
                    console.log(res)
                    if (res.data.code == 200) {
                        _self.getComAction(_self.data.bookmsg.id);
                        _self.setData({
                            talkMsg: ''
                        })
                    } else {


                    }
                }
            })
        }



    },
    getComAction: function(id) {
        wx.showLoading({
            title: '加载中',
        })
        var _self = this;
        wx.request({
            url: getApp().globalData.baseUrl + '/rest/book/selectDiscussPageInfo',
            data: {
                pageNum: 1,
                bookId: id,
                pageSize: 200


            },
            success: function(res) {
                console.log(res)
                wx.hideLoading()
                if (res.data.code == 200) {
                    _self.setData({

                        colList: res.data.obj.list
                    })

                } else {


                }
            }
        })



    },
    m: function(e) {
        console.log(123)
    }



})