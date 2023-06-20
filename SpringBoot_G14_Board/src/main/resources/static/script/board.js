function idCheck(){
	if( document.frm.userid.value==""){
		alert('아이디를 입력하여 주십시오.');
		document.frm.userid.focus();
		return;
	}
	var k = document.frm.userid.value
	var opt = "toolbar=no,menubar=no,resizable=no,width=450,height=200";
	window.open("idcheck?userid=" + k, "id check", opt);
} 


function idok(id){
	opener.frm.userid.value = id;
	opener.frm.re_id.value = id;
	self.close();
}


function boardCheck(){
	if(document.frm.title.value==''){
		alert("제목을 입력하세요");
		document.frm.title.focus();
		return false;		
	}else if(document.frm.content.value==''){
		alert("내용을 입력하세요");
		document.frm.content.focus();
		return false;		
	}else{
		return true;
	}
}




function selectimg(){
	var opt = "toolbar=no,menubar=no,resizable=no,width=450,height=200";
	window.open( 'selectimg' , 'selectimg',  opt);
}




function selectedimage(){
	document.frm.submit();
}



function reply_check(){
	if( document.frm2.content.value=="") {
		alert("댓글 내용을 입력하세요");
		return false;
	}
	return true;
}



function open_win(url, name) {
	window.open( url , name , "toolbar=no, menubar=no, scrollbars=no, "
				+ " resizable=no, width=500, height=230");
}

















