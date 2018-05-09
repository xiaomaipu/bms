const fetch=require("../../utils/fetch");
Page({
    data: {
        
        bookList:[],
        defaultImg:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522893287&di=3880e2a14ac8a9635394d4575f5655ba&imgtype=jpg&er=1&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160830%2F102-160S022003WP.jpg',       
        pageNum:1,
        loadingBook:false,
        isLastBook:false
    },
    onLoad:function(){
        this.getBookList(1)
       
    },
    getBookList:function(pageNum){
        this.setData({
            loadingBook:true
        })
        fetch.showLoading();
        fetch.getEbook(pageNum).then((response)=>{
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