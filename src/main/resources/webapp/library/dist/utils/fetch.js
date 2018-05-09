let util = require("./util")
let app = getApp();
let getApi = util.wxPromisify(wx.request,"request"),
    getStorageSync = util.wxPromisify(wx.getStorageSync),
    showLoading = util.wxPromisify(wx.showLoading),
    hideLoading = util.wxPromisify(wx.hideLoading),
    redirectTo = util.wxPromisify(wx.redirectTo),
    requestPayment = util.wxPromisify(wx.requestPayment),
    switchTab = util.wxPromisify(wx.switchTab),
    showToast = util.wxPromisify(wx.showToast),
    NetworkType = util.wxPromisify(wx.getNetworkType),
    showNavigationBarLoading=util.wxPromisify(wx.showNavigationBarLoading),
    hideNavigationBarLoading=util.wxPromisify(wx.hideNavigationBarLoading),
    chooseImage=util.wxPromisify(wx.chooseImage),
    uploadFile=util.wxPromisify(wx.uploadFile,"upfile"),
    showModal=util.wxPromisify(wx.showModal),
    getSystemInfo = util.wxPromisify(wx.getSystemInfo);
//带session的header
const HEADERSESSION = app.globalData.header;
//请求的地址
const BASEURL = app.globalData.baseUrl;
/**
 * 查看购物车商品信息
 */
const getBookLists = (pageNum) => {
        return getApi({
            url: BASEURL + '/rest/book/queryList',
            header: HEADERSESSION,
            data: {
                pageNum:pageNum
            }
        })
    }
    const searchBook = (bookName) => {
        return getApi({
            url: BASEURL + '/rest/book/queryList',
            header: HEADERSESSION,
            data: {
                bookName:bookName
            }
        })
    }
const getEbook=(pageNum,kind=1,pageSize=15)=>{
    return getApi({
        url: BASEURL + '/rest/book/simplePageInfo',
            header: HEADERSESSION,
            data: {
                pageNum,
                kind,
                pageSize
            }
    })
}

    /**
     * 检查API的在当前的微信版本是否可用
     * @param {*需要检测的API} API 
     */
const compatibilityHandler = (API) => {
    if (API) {
        API()
    } else {
        // 如果希望用户在最新版本的客户端上体验您的小程序，可以这样子提示
        wx.showModal({
            title: '提示',
            content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
        })
    }
}

const handleFetchError = (des) => {
        wx.showToast({
            title: des,
            image: '../../image/warn.png',
            duration: 3000
        })
    }
    /**
     * 
     * @param {*当前的调用者} self 
     * @param {* 提示的文案} my_toast_tip 
     * @param {*选择的模板 } template 
     * @param {*模板自动消失的时间} time 
     */
const showMyToast = (self, my_toast_tip, time = 2000, template = 'my_toast') => {
    self.setData({
        template: template, //my_toast
        my_toast_tip: my_toast_tip
    })
    setTimeout(() => {
        self.setData({
            template: '', //my_toast
            my_toast_tip: ''
        })
    }, time)
}
const loadingText=(config={ title: '加载中' })=>{
    showLoading(config);
    showNavigationBarLoading()

}
const hideLoadingText=()=>{
    hideLoading();
    hideNavigationBarLoading()
}

module.exports = {
    showLoading,
    hideLoading,
    
    redirectTo,
    switchTab,
    
    showToast,
   
    compatibilityHandler,
    handleFetchError,
    getEbook,
    showMyToast,
    NetworkType,
    loadingText,
    hideLoadingText,
    getBookLists,
    searchBook
}