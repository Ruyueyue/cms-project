/*为闭包定义一个变量$,传输jQuery*/
(function ($) {
    $.ajaxCheck=function(data){
        if (data.result) return true;
        else {
            alert(data.msg);
            return false;
        }
    }
    $.fn.mytree = function(opts) {
        var setting = $.extend({
            data:{
                simpleData:{
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: -1
                }
            },
            view: {
                dblClickExpand: false,
                selectedMulti: false
            },
            async: {
                enable: true,
                url: opts?(opts.url||"treeAll"):"treeAll"

            },
            mine: {
                listChild:1,
                srcElement:"#cc"
            },
            callback:{
                onAsyncSuccess:function(){
                    if(opts.mine.expandAll)
                        t.expandAll(true);
                }
            }
        },opts||{});
        var _mine = setting.mine;
        var t = $.fn.zTree.init($(this), setting);
        t.getCheckParentNodes = getCheckParentNodes;
        t.getCheckChildNodes = getCheckChildNodes;
        if(_mine.listChild) {
            t.setting.callback.onClick = listChild;
        }

        function listChild(event,treeId,treeNode) {
            $(_mine.srcElement).attr("src","channels/"+treeNode.id);
        }

        function getCheckParentNodes(treeNode,checked) {
            var ps = new Array();
            var pn;
            while((pn=treeNode.getParentNode())) {
                if(pn.checked==checked) ps.push(pn);
                treeNode = pn;
            }
            return ps;
        }
        //第三个参数存储所有子节点
        function getCheckChildNodes(treeNode,checked,cs) {
            var ccs;
            if((ccs=treeNode.children)) {
                for(var i=0;i<ccs.length;i++) {
                    var c = ccs[i];
                    if(c.checked==checked) {
                        cs.push(c);
                    }
                    getCheckChildNodes(c,checked,cs);
                }
            }
        }
        return t;
    }
    $.fn.myaccordion=function (opts) {
        /*需要覆盖原有的opts*/
        var settings = $.extend({
            selectedClz: "navSelected",
            titleTgaName:"h3"
        }, opts || {});  /*替换*/
        var titleNode=$(this).find("ul>"+settings.titleTagName);//标题节点
        var selectedNode=$(this).find("ul."+settings.selectedClz+">"+settings.titleTgaName);
        titleNode.css("cursor","pointer");
        titleNode.nextAll().css("display","none");
        selectedNode.nextAll().css("display","block");
        titleNode.click(function () {
            //判断当前是否被选中
            var checked=$(this).parent().hasClass(settings.selectedClz);
            if (checked){
                $(this).parent().removeClass(settings.selectedClz);
                $(this).nextAll().slideUp();
            }else {
                $(this).parent().addClass(settings.selectedClz);
                $(this).nextAll().slideDown();
            }
        });
    };

    $.fn.trColorChange=function (opts) {
        var settings=$.extend({
            overClz:"trMouseover",
            evenClz:"trEvenColor"
        },opts||{});
        $(this).find("tbody tr:even").addClass(settings.evenClz);/*绑定时间*/
        $(this).find("tbody tr").on("mouseenter mouseleave",function () {
            $(this).toggleAttribute(settings.overClz);
        });
    };

    $.fn.confirmOperator=function (opts) {
        var settings=$.extend({
            msg:"该操作不可逆,确定进行该操作吗?",
            eventName:"click"
        },opts||{});
        $(this).on(settings.eventName,function (event) {
            if (!confirm(settings.msg)){
                event.preventDefault();
            }
        });
    };


})(jQuery)