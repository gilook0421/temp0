/*
fix for deprecated method in chrome 37.
@ 전자정부프레임에서 오픈소스로 지원하는 기능.
*/

this.otherParameters = new Array();
this.showModalDialogSupported = true;
this.callbackMethod = null;

if(!window.showModalDialog){
    showModalDialogSupported = false;

    window.showModalDialog = function(arg1, arg2, arg3, callback){
        var w;
        var h;
        var resizable = "no";
        var scroll = "no";
        var status = "no";

        // get the modal specs
        var mdattrs = arg3.split(";");
        for(i = 0;i < mdattrs.length ; i++){
            var mdattr = mdattrs[i].split(":");

            var n = mdattr[0];
            var v = mdattr[1];
            if(n){
                n = n.trim().toLowerCase();
            }
            if(v){
                v = v.trim().toLowerCase();
            }
            if(n == "dialogheight"){
                h = v.replace("px", "");
            }
            else if( n == "dialogwidth"){
                w = v.replace("px", "");
            }
            else if(n == "resizable"){
                resizable = v;
            }
            else if(n == "scroll"){
                scroll = v;
            }
            else if(n == "status"){
                ststus = v;
            }
            else{
                // no-op
            }
        }

        var left = window.screenX + (window.outerWidth / 2 ) - (w / 2);
        var top = window.screenY + (window.outerHeight / 2 ) - (h / 2);
        var targetWin = window.open(arg1, "ShowModalDialog" + arg1,
                        'toolbar=no, location=no, directories=no, status=' + status + ', menubar=no, scrollbars=' + scroll + ', resizable=' + resizable
                        + ', copyhistory=no, width=' + w + ', heigth=' + h + ', top=' + top + ', left=' + left);

        dialogArguments = arg2;

        if(callbak != null){
            callbackMethod = callback;
        }
        else{
            callbackMethod = null;
        }

        targetWin.focus();
    };

    window.getDialogArgumentsInner = function(){
        return dialogArguments;
    }

    window.getCallbackMethodName = function(){
        return callbackMethod;
    }
}