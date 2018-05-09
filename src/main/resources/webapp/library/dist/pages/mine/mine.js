Page({
    data: {
        bookList_now: [],
        bookList_later: [],
        bookList_ago: [],
        bookList_like: [],
        showList: [],
        id: '',
        name: '',
        style1: 'select_style',
        style2: 'nomal',
        style3: 'nomal',
        style4: 'nomal'

    },
    onLoad: function(options) {

    },
    onReady: function() {
        // 生命周期函数--监听页面初次渲染完成

    },
    onShow: function() {
        // 生命周期函数--监听页面显示
        // 生命周期函数--监听页面加 String2
        if (!wx.getStorageSync('openId')) {
            // wx.navigateTo({
            //     url: '../login/login'
            // })
            this.setData({

                name: "点击头像登陆"
            })

        } else {
            this.setData({
                id: wx.getStorageSync('openId'),
                name: wx.getStorageSync('name')
            })
        }
        this.mybook_now();
        this.likeList();


    },
    onHide: function() {
        // 生命周期函数--监听页面隐藏

    },
    checkAction: function(e) {
        var _self = this;
        console.log(e.currentTarget.dataset.code)
        var code = e.currentTarget.dataset.code;
        if (code == '1') {
            _self.setData({
                style1: 'select_style',
                style2: 'nomal',
                style3: 'nomal',
                style4: 'nomal',
                showList: _self.data.bookList_now
            })
        } else if (code == '2') {
            _self.setData({
                style1: 'nomal',
                style2: 'select_style',
                style3: 'nomal',
                style4: 'nomal',
                showList: _self.data.bookList_later
            })
        } else if (code == '3') {
            _self.setData({
                style1: 'nomal',
                style2: 'nomal',
                style3: 'select_style',
                style4: 'nomal',
                showList: _self.data.bookList_ago
            })
        } else if (code == '4') {
            _self.setData({
                style1: 'nomal',
                style2: 'nomal',
                style3: 'nomal',
                style4: 'select_style',
                showList: _self.data.bookList_like
            })
        }
    },
    mybook_now: function() {
        var _self = this;
        wx.showLoading({
            title: '加载中',
        })

        wx.request({
            url: getApp().globalData.baseUrl + '/rest/borrow/queryList',
            data: {
                userId: _self.data.id

            },

            success: function(res) {
                wx.hideLoading()
                if (res.data.code == 200) {
                    var arrNow = new Array();
                    var arrLater = new Array();
                    var arrAgo = new Array();
                    for (var i = 0; i < res.data.obj.length; i++) {
                        console.log(res.data.obj[i].status)
                        if (res.data.obj[i].status == 10) {
                            arrNow.push(res.data.obj[i]);
                        } else if (res.data.obj[i].status == 20) {
                            arrAgo.push(res.data.obj[i]);
                        } else if (res.data.obj[i].status == 30) {
                            arrLater.push(res.data.obj[i]);
                        }
                    }
                    _self.setData({
                        style1: 'select_style',
                        style2: 'nomal',
                        style3: 'nomal',
                        style4: 'nomal',
                        bookList_now: arrNow,
                        bookList_later: arrLater,
                        bookList_ago: arrAgo,
                        showList: arrNow
                    })

                } else {

                }
            }
        })
    },
    likeList: function() {
        wx.showLoading({
            title: '加载中',
        })
        var _self = this;
        wx.request({
            url: getApp().globalData.baseUrl + '/rest/user/collect/selectPageList?userId=' + _self.data.id,
            method: 'post',
            // data: {
            //     userId: _self.data.id

            // },

            success: function(res) {
                wx.hideLoading()
                if (res.data.code == 200) {
                    console.log(res.data.obj.list)
                    _self.setData({
                        bookList_like: res.data.obj.list
                    })
                } else {

                }
            }
        })
    },

})