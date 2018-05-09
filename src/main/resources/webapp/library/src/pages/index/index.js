const fetch=require("../../utils/fetch");
Page({
    data: {
        inputShowed: false,
        inputVal: "",
        searching:false,
        showSearchCon:false,
        bookList:[],
        searchList:[],
        pageNum:1,
        loadingBook:false,
        isLastBook:false,
        defaultImg:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522893287&di=3880e2a14ac8a9635394d4575f5655ba&imgtype=jpg&er=1&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160830%2F102-160S022003WP.jpg'
    },
    onLoad:function(){
        this.getBookList(1)
       
    },
    onShow:function(){
        wx.getStorage({
            key: 'openId',
            success: function(res) {
                console.log("fff",res)
               if(!res){
                wx.reLaunch({
                    url: '../login/login'
                  })
               }
            },
            fail:function(){
                wx.reLaunch({
                    url: '../login/login'
                  })
            }
          })
    },
    showInput: function () {
        this.setData({
            inputShowed: true
        });
    },
    hideInput: function () {
        this.setData({
            inputVal: "",
            inputShowed: false,
            showSearchCon:false,
            searchList:[],

        });
    },
    clearInput: function () {
        this.setData({
            inputVal: ""
        });
    },
    inputTyping: function (e) {
        this.setData({
            inputVal: e.detail.value
        });
	},
	toSearch:function(){
        console.log(this.data.inputVal)
        if(!this.data.inputVal){
            return
        }
        this.setData({
            searching:true,
            showSearchCon:true,
            searchList:[]
        })

       fetch.searchBook(this.data.inputVal.replace(/ /g,'')).then(response=>{
           const res=response.data;
        this.setData({
            searchList:res.obj.list,
            searching:false
        })
       }).catch(error=>{
        this.setData({
            searching:false
        })

       })
    },
    getBookList:function(pageNum){
        this.setData({
            loadingBook:true
        })
        fetch.showLoading();
        fetch.getBookLists(pageNum).then((response)=>{
            console.log("response",response)
            fetch.hideLoading();
            const res=response.data;
            this.setData({
                bookList:this.data.bookList.concat(res.obj.list) ,
                pageNum:this.data.pageNum+1,
                loadingBook:false,
                isLastBook:res.obj.isLastPage
                
            })
        }).catch((err)=>{
            fetch.hideLoading();
            this.setData({
                loadingBook:false
                
            })
        })
    },
    onReachBottom() {
        if(this.data.loadingBook || this.data.isLastBook){
            return
        }
        this.getBookList(this.data.pageNum)
        console.log('bo')
    },
});