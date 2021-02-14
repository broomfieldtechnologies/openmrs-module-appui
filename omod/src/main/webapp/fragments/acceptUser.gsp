

<!--US10060 --->
<header>
<style>
    #dialog{
        width: 500px; 
        height: 600px; 
        overflow: auto;
    }

</style>
<script>

    jq(function() {

        jq('#accept_button').click(function() {
            jq.getJSON('${ ui.actionLink("appui","acceptUser","acceptUser")}');
            dialogClosed();       
        });

        jq('#disagree_button').click(function() {
            jq.getJSON('${ ui.actionLink("appui","acceptUser","disagreeUser",["successUrl": contextPath])}');
            dialogClose(); 
        });
        
        
    jq(document).keydown(function(e) {
        if (e.keyCode == 27) return false;
    });


    });
        
    function dialogShow(){
        document.getElementById("dialog").showModal();
    }
    
    function dialogClosed(){
        document.getElementById("dialog").close();
    }
    
    function dialogClose(){
        document.getElementById("dialog").close();
        window.parent.location.replace("<% contextPath %>")
    }

</script>

</header>

<body onload="dialogShow()">
     
    <dialog id="dialog">
        <h3>${dialogMessage}</h3>
        <br>
        <input type="button" value="${ui.message("emr.userAcknowledge.agree")}" id="accept_button" />
        <input type="button" value="Disagree" id="disagree_button" />
    </dialog>
  
</body>
