function idCheck(){
	if(document.frm.userid.value==""){
		alert('아이디를 입력하여 주십시요.');
		document.frm.userid.focus();
		return;
	}
var k = document.frm.userid.value
var opt = "toolbar=no, menubar=no, resizable=no, width=450, height=200";
window.open("idcheck?userid="+k, "id check", opt);	
}


function idok(id){
	opener.frm.userid.value=id;
	opener.frm.re_id.value=id;
	self.close();
}


function boardCheck(){
	if(document.frm.title.value==""){
		alert("제목을 입력하세요")
		document.frm.title.focus();
		return false;
	}else if(document.frm.content.value==""){
		alert("내용을 입력하세요")
		document.frm.content.focus();
		return false;
	}else{
		return true;
	}
}