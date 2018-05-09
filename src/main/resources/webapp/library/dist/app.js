App({

	onHide: function() {

	},
	onShow: function() {},
	globalData: {


		// baseUrl: 'https://bms.24xiaomai.com',
		baseUrl: 'http://47.94.52.121:8064',

		imageUrl: 'http://static.24xiaomai.com/go/wxapplet/1.3.0/image',
		header: {
			'Cookie': '', //公用请求头
			'content-type': 'application/json'
		},
		common: {
			isFirst: true,
			shopCode: '', //门店信息
			shopName: '', //门店名字
			souce: '', //来源 1 不开门 0 开门
			selfPay: null, //付款页跳转至『我的』页面时，订单发生了变化，要重新获取订单信息，wx.switchTab: url 不支持 queryString，使用见pay.js
		},
		userInfo: {
			openId: "", //唯一标示
			nickName: "", //名字
			avatarUrl: "", //头像
			gender: "", //性别
			level: "", //会员级别
			isBind: false //是否绑定了手机号

		}
	}
})