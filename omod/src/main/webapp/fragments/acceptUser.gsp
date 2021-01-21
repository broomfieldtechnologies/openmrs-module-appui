

<!--US10060 --->
<header>
<script>



jq(function() {

 jq('#accept_button').click(function() {
        jq.getJSON('${ ui.actionLink("appui","acceptUser","acceptUserModel")}');
          dialogButtonClose();       
        });


jq('#logout_button').click(function() {
        jq.getJSON('${ ui.actionLink("appui","acceptUser","logout", ["successUrl": contextPath])}');
             dialogButtonClose(); 
        });



});
        
         function dialogShow(){
   document.getElementById("dialog").showModal();
    }
    
    function dialogButtonClose(){
document.getElementById("dialog").close();
}


</script>
</header>
<body onload="dialogShow()">
 
    
<dialog id="dialog">
   <h3>${dialogMessage}</h3>
    
    <br>
    
    
    <input type="button" value="${ui.message("emr.userAcknowledge.agree")}" id="accept_button" />
    
    <input type="button" value="${ui.message("emr.userAcknowledge.disagree")}" id="logout_button"/>
    </dialog>
    
  
   

   
  
  
  
</body>
