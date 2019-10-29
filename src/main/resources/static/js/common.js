Date.prototype.format = function(format){ 
    var o =  { 
    "M+" : this.getMonth()+1, //month 
    "d+" : this.getDate(), //day 
    "h+" : this.getHours(), //hour 
    "m+" : this.getMinutes(), //minute 
    "s+" : this.getSeconds(), //second 
    "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
    "S" : this.getMilliseconds() //millisecond 
    };
    if(/(y+)/.test(format)){ 
    	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
    for(var k in o)  { 
	    if(new RegExp("("+ k +")").test(format)){ 
	    	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	    } 
    } 
    return format; 
};

var E3 = {
	// 编辑器参数
	kingEditorParams : {
		//指定上传文件参数名称
		filePostName  : "uploadFile",
		//指定上传文件请求的url。
		uploadJson : '/pic/upload',
		//上传类型，分别为image、flash、media、file
		dir : "image"
	},
	// 格式化时间
	formatDateTime : function(val,row){
		var now = new Date(val);
    	return now.format("yyyy-MM-dd hh:mm:ss");
	},
	// 格式化生日
	formatBirthday : function(val,row){
		if(val == null){
			return "未填写"
		}
		var now = new Date(val);
		return now.format("yyyy-MM-dd");
	},
	// 格式化连接
	formatUrl : function(val,row){
		if(val){
			return "<a href='"+val+"' target='_blank'>查看</a>";			
		}
		return "";
	},
	// 格式化价格
	formatPrice : function(val,row){
		return (val/100).toFixed(2);
	},
	formatPriceList : function(val,row){
		return (val).toFixed(2);
	},
	// 格式化商品的状态
	formatItemStatus : function formatStatus(val,row){
        if (val == 1){
            return '正常';
        } else if(val == 2){
        	return '<span style="color:red;">下架</span>';
        } else {
        	return '未知';
        }
    },
	// 格式化是否状态
	formatIsOrNotStatus : function (val,row){
		if (val == 1){
			return '是';
		} else if(val == 0){
			return '<span style="color:red;">否</span>';
		} else {
			return '未知';
		}
	},
	// 格式化是课程状态
	formatEffectStatus : function(val,row){
		if (val == 1){
			return '上架';
		} else if(val == 2){
			return '<span style="color:red;">下架</span>';
		} else {
			return '未知';
		}
	},// 格式化是用户状态
	formatIsAdminStatus : function(val,row){
		if (val == 1){
			return '是';
		} else if(val == 2){
			return '名师';
		} else {
			return '否';
		}
	},
	formatSex : function(val,row){
		if (val == 1){
			return '男';
		} else if(val == 0){
			return '女';
		} else {
			return '未填写';
		}
	},
	// 格式化是订单状态
	formatOrderStatus : function (val,row){
		if (val == 1){
			return '<span style="color:red;">已下单(未付款)</span>';
		} else if(val == 2){
			return '已付款(待评价)';
		} else if(val == 3){
			return '已评价(已完成)';
		} else if(val == 4){
			return '待审核(退款中)';
		} else if(val == 5){
			return '已退款';
		} else {
			return '已失效';
		}
	},
	// 格式化是支付方式
	formatPayment : function (val,row){
		if (val == 1){
			return '支付宝';
		} else if(val == 2){
			return '微信';
		} else if(val == 3){
			return '金币';
		}
	},
	// 格式化是订单类型
	formatOrderType : function (val,row){
		if (val == 1){
			return '课程购买';
		} else if(val == 2){
			return '测算服务';
		}
	},
	formatApproveStatus : function(val,row){
		if (val == 0){
			return '待审批';
		} else if(val == 2){
			return '已审批';
		} else if(val == 3){
			return '已驳回';
		}
	},
	formatSettledApproveStatus : function(val,row){
		if (val == 1){
			return '待审批';
		} else if(val == 2){
			return '已审批';
		} else if(val == 3){
			return '已驳回';
		}
	},
	//1.订单评论,2.资讯评论,3,.课程评论
	formatCommentType : function(val,row){
		if (val == 1){
			return '订单评论';
		} else if(val == 2){
			return '资讯评论';
		} else if(val == 3){
			return '课程评论';
		}
	},
	//订单状态：0,全部订单，1已下单，2已付款，3已评价，4待审核，5已退款，6已失效,7已驳回
	orderApproveStatus : function(val,row){
		if (val == 4){
			return '待审批';
		} else if(val == 5){
			return '已退款';
		} else if(val == 7){
			return '已驳回';
		}
	},
	formatProfession : function(val,row){
		if (val == 1){
			return '全职名师';
		} else if(val == 2){
			return '兼职名师';
		}
	},
    
    init : function(data){
    	// 初始化图片上传组件
    	this.initPicUpload(data);
    	// 初始化选择类目组件（课程分类）
    	this.initItemCat(data);
		// 初始化选择类目组件（资讯分类）
		this.selectInfoType(data)
		// 初始化选择类目组件（课程级别）
		this.selectCourseLevel(data);
		// 初始化选择类目组件（授课方式）
		this.selectTeachMethods(data)
		// 初始化选择类目组件（测算服务）
		this.initItemService(data)
    },
    // 初始化图片上传组件
    initPicUpload : function(data){
    	$(".picFileUpload").each(function(i,e){
    		var _ele = $(e);
    		_ele.siblings("div.pics").remove();
    		_ele.after('\
    			<div class="pics">\
        			<ul></ul>\
        		</div>');
    		// 回显图片
        	if(data && data.pics){
        		var imgs = data.pics.split(",");
        		for(var i in imgs){
        			if($.trim(imgs[i]).length > 0){
        				_ele.siblings(".pics").find("ul").append("<li><a href='"+imgs[i]+"' target='_blank'><img src='"+imgs[i]+"' width='80' height='50' /></a></li>");
        			}
        		}
        	}
        	//给“上传图片按钮”绑定click事件
        	$(e).click(function(){
        		var form = $(this).parentsUntil("form").parent("form");
        		//打开图片上传窗口
        		KindEditor.editor(E3.kingEditorParams).loadPlugin('multiimage',function(){
        			var editor = this;
        			editor.plugin.multiImageDialog({
						clickFn : function(urlList) {
							var imgArray = [];
							KindEditor.each(urlList, function(i, data) {
								imgArray.push(data.url);
								form.find(".pics ul").append("<li><a href='"+data.url+"' target='_blank'><img src='"+data.url+"' width='80' height='50' /></a></li>");
							});
							form.find("[name=image]").val(imgArray.join(","));
							editor.hideDialog();
						}
					});
        		});
        	});
    	});
    },
    
    // 初始化选择类目组件(课程分类)
    initItemCat : function(data){
    	$(".selectItemCat").each(function(i,e){
    		var _ele = $(e);
    		if(data && data.courseType){
    			_ele.after("<span style='margin-left:10px;'>"+data.courseType+"</span>");
				_ele.parent().find("[name=courseType]").val(data.remark1);

    		}else{
    			_ele.after("<span style='margin-left:10px;'></span>");
    		}
    		_ele.unbind('click').click(function(){
    			$("<div>").css({padding:"5px"}).html("<ul>")
    			.window({
    				width:'500',
    			    height:"250",
    			    modal:true,
    			    closed:true,
    			    iconCls:'icon-save',
    			    title:'选择类目',
    			    onOpen : function(){
    			    	var _win = this;
    			    	$("ul",_win).tree({
    			    		url:'/qianyi/category/getCourseList',
    			    		animate:true,
    			    		onClick : function(node){
    			    			if($(this).tree("isLeaf",node.target)){
    			    				// 填写到cid中
    			    				_ele.parent().find("[name=courseType]").val(node.id);
    			    				_ele.next().text(node.text).attr("courseType",node.id);
    			    				$(_win).window('close');
    			    				if(data && data.fun){
    			    					data.fun.call(this,node);
    			    				}
    			    			}
    			    		}
    			    	});
    			    },
    			    onClose : function(){
    			    	$(this).window("destroy");
    			    }
    			}).window('open');
    		});
    	});
    },
	// 初始化选择类目组件(服务名称)
	initItemService : function(data){
		$(".selectItemService").each(function(i,e){
			var _ele = $(e);
			if(data && data.serviceName){
				_ele.after("<span style='margin-left:10px;'>"+data.serviceName+"</span>");
				_ele.parent().find("[name=serviceName]").val(data.remark1);

			}else{
				_ele.after("<span style='margin-left:10px;'></span>");
			}
			_ele.unbind('click').click(function(){
				$("<div>").css({padding:"5px"}).html("<ul>")
					.window({
						width:'500',
						height:"250",
						modal:true,
						closed:true,
						iconCls:'icon-save',
						title:'选择类目',
						onOpen : function(){
							var _win = this;
							$("ul",_win).tree({
								url:'/qianyi/category/getServiceList',
								animate:true,
								onClick : function(node){
									if($(this).tree("isLeaf",node.target)){
										// 填写到cid中
										_ele.parent().find("[name=serviceName]").val(node.id);
										_ele.next().text(node.text).attr("serviceName",node.id);
										$(_win).window('close');
										if(data && data.fun){
											data.fun.call(this,node);
										}
									}
								}
							});
						},
						onClose : function(){
							$(this).window("destroy");
						}
					}).window('open');
			});
		});
	},
	// 初始化选择类目组件(资讯分类)
	selectInfoType : function(data){
		$(".selectInfoType").each(function(i,e){
			var _ele = $(e);
			if(data && data.remark1){
				_ele.after("<span style='margin-left:10px;'>"+data.infoType+"</span>");
				_ele.parent().find("[name=infoType]").val(data.remark1);
			}else{
				_ele.after("<span style='margin-left:10px;'></span>");
			}
			_ele.unbind('click').click(function(){
				$("<div>").css({padding:"5px"}).html("<ul>")
					.window({
						width:'500',
						height:"250",
						modal:true,
						closed:true,
						iconCls:'icon-save',
						title:'选择类目',
						onOpen : function(){
							var _win = this;
							$("ul",_win).tree({
								url:'/qianyi/category/getCourseList',
								animate:true,
								onClick : function(node){
									if($(this).tree("isLeaf",node.target)){
										// 填写到cid中
										_ele.parent().find("[name=infoType]").val(node.id);
										_ele.next().text(node.text).attr("infoType",node.id);
										$(_win).window('close');
										if(data && data.fun){
											data.fun.call(this,node);
										}
									}
								}
							});
						},
						onClose : function(){
							$(this).window("destroy");
						}
					}).window('open');
			});
		});
	},
	//课程级别
	selectCourseLevel : function(data){
		$(".selectCourseLevel").each(function(i,e){
			var _ele = $(e);
			if(data && data.courseLevel){
				_ele.after("<span style='margin-left:10px;'>"+data.courseLevel+"</span>");
				_ele.parent().find("[name=courseLevel]").val(data.remark2);
			}else{
				_ele.after("<span style='margin-left:10px;'></span>");
			}
			_ele.unbind('click').click(function(){
				$("<div>").css({padding:"5px"}).html("<ul>")
					.window({
						width:'500',
						height:"250",
						modal:true,
						closed:true,
						iconCls:'icon-save',
						title:'选择类目',
						onOpen : function(){
							var _win = this;
							$("ul",_win).tree({
								url:'/qianyi/category/getCourseLevelList',
								animate:true,
								onClick : function(node){
									if($(this).tree("isLeaf",node.target)){
										// 填写到cid中
										_ele.parent().find("[name=courseLevel]").val(node.id);
										_ele.next().text(node.text).attr("courseLevel",node.id);
										$(_win).window('close');
										if(data && data.fun){
											data.fun.call(this,node);
										}
									}
								}
							});
						},
						onClose : function(){
							$(this).window("destroy");
						}
					}).window('open');
			});
		});
	},
	//授课方式
	selectTeachMethods : function(data){
		$(".selectTeachMethods").each(function(i,e){
			var _ele = $(e);
			if(data && data.courseIsOnline){
				_ele.after("<span style='margin-left:10px;'>"+data.courseIsOnline+"</span>");
				_ele.parent().find("[name=courseIsOnline]").val(data.remark3);
				if(data.remark3 == "23"){
					$(".online").hide();
					$(".underline").show();
				}
			}else{
				_ele.after("<span style='margin-left:10px;'></span>");
			}
			_ele.unbind('click').click(function(){
				$("<div>").css({padding:"5px"}).html("<ul>")
					.window({
						width:'500',
						height:"250",
						modal:true,
						closed:true,
						iconCls:'icon-save',
						title:'选择类目',
						onOpen : function(){
							var _win = this;
							$("ul",_win).tree({
								url:'/qianyi/category/getTeachMethods',
								animate:true,
								onClick : function(node){
									if($(this).tree("isLeaf",node.target)){
										// 填写到cid中
										_ele.parent().find("[name=courseIsOnline]").val(node.id);
										_ele.next().text(node.text).attr("courseIsOnline",node.id);
										//线上
										if(node.id == 22){
											$(".online").show();
											$(".underline").hide();
										} else {
											//线下
											$(".online").hide();
											$(".underline").show();
										}
										$(_win).window('close');
										if(data && data.fun){
											data.fun.call(this,node);
										}
									}
								}
							});
						},
						onClose : function(){
							$(this).window("destroy");
						}
					}).window('open');
			});
		});
	},
    
    createEditor : function(select){
    	return KindEditor.create(select, E3.kingEditorParams);
    },
    
    /**
     * 创建一个窗口，关闭窗口后销毁该窗口对象。<br/>
     * 
     * 默认：<br/>
     * width : 80% <br/>
     * height : 80% <br/>
     * title : (空字符串) <br/>
     * 
     * 参数：<br/>
     * width : <br/>
     * height : <br/>
     * title : <br/>
     * url : 必填参数 <br/>
     * onLoad : function 加载完窗口内容后执行<br/>
     * 
     * 
     */
    createWindow : function(params){
    	$("<div>").css({padding:"5px"}).window({
    		width : params.width?params.width:"80%",
    		height : params.height?params.height:"80%",
    		modal:true,
    		title : params.title?params.title:" ",
    		href : params.url,
		    onClose : function(){
		    	$(this).window("destroy");
		    },
		    onLoad : function(){
		    	if(params.onLoad){
		    		params.onLoad.call(this);
		    	}
		    }
    	}).window("open");
    },
    
    closeCurrentWindow : function(){
    	$(".panel-tool-close").click();
    },
    
    changeItemParam : function(node,formId){
    	$.getJSON("/item/param/query/itemcatid/" + node.id,function(data){
			  if(data.status == 200 && data.data){
				 $("#"+formId+" .params").show();
				 var paramData = JSON.parse(data.data.paramData);
				 var html = "<ul>";
				 for(var i in paramData){
					 var pd = paramData[i];
					 html+="<li><table>";
					 html+="<tr><td colspan=\"2\" class=\"group\">"+pd.group+"</td></tr>";
					 
					 for(var j in pd.params){
						 var ps = pd.params[j];
						 html+="<tr><td class=\"param\"><span>"+ps+"</span>: </td><td><input autocomplete=\"off\" type=\"text\"/></td></tr>";
					 }
					 
					 html+="</li></table>";
				 }
				 html+= "</ul>";
				 $("#"+formId+" .params td").eq(1).html(html);
			  }else{
				 $("#"+formId+" .params").hide();
				 $("#"+formId+" .params td").eq(1).empty();
			  }
		  });
    },
    getSelectionsIds : function (select){
    	var list = $(select);
    	var sels = list.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    },
    
    /**
     * 初始化单图片上传组件 <br/>
     * 选择器为：.onePicUpload <br/>
     * 上传完成后会设置input内容以及在input后面追加<img> 
     */
    initOnePicUpload : function(){
    	$(".onePicUpload").click(function(){
			var _self = $(this);
			KindEditor.editor(E3.kingEditorParams).loadPlugin('image', function() {
				this.plugin.imageDialog({
					showRemote : false,
					clickFn : function(url, title, width, height, border, align) {
						var input = _self.siblings("input");
						input.parent().find("img").remove();
						input.val(url);
						input.after("<a href='"+url+"' target='_blank'><img src='"+url+"' width='80' height='50'/></a>");
						this.hideDialog();
					}
				});
			});
		});
    }
};
