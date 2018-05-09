App({

	onHide: function() {

	},
	onShow: function() {},
	globalData: {


		baseUrl: '小程序服务器地址',
		imageUrl: '静态资源地址',
		header: {
			'Cookie': '', //公用请求头
			'content-type': 'application/json'
		},
		common: {
			
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
