function delFruit(fid){
    if( confirm('是否确认删除？')){
        window.location.href='fruit.do?fid=' + fid + '&operate=del';
        //location 是当前window的 url;This suggests that the URL is expecting a parameter fid to be passed
    }
}
function page(pageNo){
    window.location.href = "fruit.do?pageNo="+pageNo ;
}