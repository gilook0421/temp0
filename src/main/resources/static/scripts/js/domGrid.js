/*
Document Object Model, Grid
@ 한 화면에서 n건이 사용될때, 처리방안 고민필요
*/

var tableId = "";
var tdAlign;
var domRow = 0;
var domRowCnt = 0;
var domColCnt = 0;
var choiceRowId = "";
var choiceRowBg = "";
var domAfterRun;

var nameClass = ["disp_none", "left_tran", "cent_tran", "righ_tran", "cent_tran", "disp_none"];

$(function(){
    $("#data02").scroll(function(){
        //var off_pos = $("#" + choiceRowId).position();
        //$("#data02").animate({scrollTop : off_pos.top}, 0);
    })

    $("#data02").keydown(function(event){
        var crow = parseInt("0" + choiceRowId.replace("row", ""));

        if(event.keyCode == 38){//keyUp
            $("#row"+String(crow-1)).trigger("focus");
        }
        else if(event.keyCode == 40){//keyDown
            $("#row" + String(crow+1)).trigger("focus");
        }
        else if(event.keyCode == 13){//Enter
            $("#" + choiceRowId).trigger("dbclick");
        }
        else{
            return;
        }
    })

    $("#data02").scroll(function(){
        $("#data01").scrollTop($("#data02").scrollTop());
        $("#title02").scrollLeft($("#data02").scrollLeft());
    })

    // grid 초기화
    // 0:disp_none, 1:left_tran, 2:righ_tran, 3:righ_tran, 4:cent_trn, 5:disp_none
    function domGrid_Set(table_id, td_align, fnAfterRun){
        tableId = table_id;
        tdAlign = td_align;
        domAfterRun = fnAfterRun;
        domColCnt = tdAlign.length;
        domRowCnt = 0;
        domRow = 0;
        $(".domrow").remove();

        choiceRowId = "row1";
        $("#" + tableId).focus();
    }
    function domGrid_Clear(){
        domRowCnt = 0;
        domRow = 0;
        $(".domrow").remove();
    }

    //mouse
    function domGridMouseOver(gridCell){
        if(choiceRowId == gridCell.id && gridCell.id != "") return;
        gridCell.style.background = "#c5e6e8";
    }
    function domGridMouseOut(gridCell, orgBg){
        if(choceRowId == gridCell.id && gridCell.id != "") return;
        gridCell.style.background = orgBg;
    }

    //click
    function domGridFocus(gridCell, orgBg){
        choiceRowBg(gridCell, orgBg);
    }
    function domGridClick(gridCell, orgBg){
        choiceRowBg(gridCell, orgBg);
        domChoice("click");
    }
    function domGridDblClick(gridCell, orgBg){
        choiceRowBg(gridCell, orgBg);
        domChoice("dblclick");
    }
    function choiceRow(gridCell, orgBg){
        gridCell.style.background = "#f6e4dd";

        if(choiceRowId != gridCell.id && choiceRowId != "" && gridCell.id != ""){
            $(("#" + choiceRowId)).css("background-color", choiceRowBg);
        }

        choiceRowId = gridCell.id;
        choiceRowBg = orgBg;

        domRow = parseInt("0" + choiceRowId.replace("row", ""));
    }

    //특정row 선책
    function domSelect(row){
        var selRow = $("#row" + row);
        var off_pos = selRow.position();

        selRow.trigger("focus");
        $("#data02").animate({scrollTop : off_pos.top}, 0);
    }

    //선택된 row의 값을 본page의 function domChoice를 호출하여 전달한다.
    function domChoice(choiceType){
        if(isObject(domAfterRun)){
            if(choiceRowId == "" || domRowCnt == 0){
                alert("선택이 없습니다.");
                return;
            }

            var choiceRowValue = new Array(domColCnt);
            for(col = 0 ; col < domColCnt; col++){
                choiceRowValue[col] = $("#" + choiceRowId + "col" + col).text();
            }
            
            domAfterRun(choiceRowValue, choiceType);
        }
    }

    //add row
    function domGrid_Add(td_value, oth_value){
        var tbody = document.getElementById(tableId).getElementsByTagName("TBODY")[0];
        var trRow = document.createElement("TR");

        var backcolor = "#ffffff";
        if(domRowCnt % 2 != 0){
            backcolor = "#f9f9f9";
        }
        trRow.style.background = backcolor;
        trRow.style.height = "22px";
        trRow.style.maxheight = "22px";
        trRow.style.minheight = "22px";
        trRow.className = "domrow";
        trRow.id = "row" + domRowCnt;
        //trRow.style.cursor = "pointer";

        trRow.onmouseover = function(){ domGridMouseOver(this);}
        trRow.onmouseout = function(){ domGridMouseOut(this, backcolor);}
        trRow.onclick = function(){ domGridClick(this, backcolor);}
        trRow.ondblclick = function(){ domGridDblClick(this, backcolor);}
        trRow.onfocus = function(){ domGridFocus(thsi, backcolor);}

        for(col = 0 ; col < domColCnt ; col++){
            var tdCol = document.createElement("TD");
            var nrow = "" + domRowCnt;
            var ncol = "" + col;

            if(tdAlign[col] == 91){
                var chkbox = document.createElement("input");
                chkbox.type = "checkbox";
                chkbox.id = "checkbox" + col;
                chkbox.checked = td_value[col];
                chkbox.onclick = function(){ domSelect(nrow); domChoice("click"); domCheckBox_onclick(nrow, ncol);}

                tdCol.className = namaClass[2];
                tdCol.id = trRow.id + "col" + col;
                tdCol.appendChild(chkbox);
            }
            else if(tdAlign[col] == 92){
                var btn = document.createElement("input");
                btn.type = "button";
                btn.id = "button" + col;
                btn.onclick = function(){ domSelect(nrow); domChoice("click"); domButton_onclick(nrow, ncol);}
                btn.style.width = "100%";
                btn.value = td_value[col];

                tdCol.className = nameClass[2];
                tdCol.id = trRow.id + "col" + col;
                tdCol.appendChild(btn);
            }
            else if(tdAlign[col] == 93 && td_vlaue[col] != ""){
                tdCol.className = nameClass[2];
                tdCol.id = trRow.id + "col" + col;
                var ahref = "<a href='#none' style='text-decoration:underline' onclick='domAhref" + col + "(" + nrow + "," + td_value[col] + ")'>" + td_value[col] + "</a>";

                tdCol.insertAdjacentHTML('beforeEnd', ahref);
            }
            else if(tdAlign[col] == 94 && td_value[col] != ""){
                tdCol.className = nameClass[2];
                tdCol.id = trRow.id + "col" + col;
                var img = "<img src='/tsimsa/comm/images/icon_pdf.png' border='0' style='cursor:pointer' onclick='domImage" + col + "(" + nrow + ")'>";

                tdCol.insertAdjacentHTML('beforeEnd', td_value[col]);
            }

            trRow.appendChild(tdCol);

        }

        tbody.appendChild(trRow);
        domRowCnt++;

        if(domRowCnt == 1){
            choiceRow(trRow, backcolor);
            domChoice("first");
        }
    }

    function domAddCB(){
        var addCB = false;
        var addBT = false;

        if(domRowCnt == 1){
            for(col = 0; col < domColCnt; col++){
                if(tdAlign[col] == 91){
                    addCB = true;
                }
                if(tdAlign[col] == 92){
                    addBT = true;
                }
            }
        }

        if(!addCB && !addBT) return;

        var tbody = document.getElementById(tableId).getElementsByTagName("TBODU")[0];
        var trRow = document.createElement("TR");

        trRow.style.display = "none";
        trRow.className = "domrow";

        for(col = 0 ; col < domColCnt ; col++){
            var tdCol = document.createElement("TD");

            if(tdAlign[col] == 91){
                var chkbox = document.createElement("input");
                chkbox.type = "checkbox";
                chkbox.id = "checkbox" + col;

                tdCol.appendChild(chkbox);
            }
            if(tdAlign[col] == 92){
                var btn = document.createElement("input");
                btn.type = "button";
                btn.id = "button" + col;

                tdCol.appendChild(btn);
            }

            trRow.appendChild(tdCol);
        }

        tbody.appendChild(trRow);
    }


    // style
    function domCellStyle(row, col, color){
        var cell = document.getElementById("row" + row + "col" + col);
        if(color != ""){
            cell.style.color = color;
        }
    }

    // innerText
    function domCellSet(row, col, vals){
        (document.getElementById("row" + row + "col" + col)).innerText = vals;
    }


})

