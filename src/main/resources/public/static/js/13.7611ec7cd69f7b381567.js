webpackJsonp([13],{HDeQ:function(n,e,t){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=t("mvHQ"),o=t.n(i),s=t("Dd8w"),a=t.n(s),c=t("xV2B"),r=t("NYxO"),d={computed:a()({},Object(r.b)(["name"])),data:function(){return{leftSpan:8,simpleMode:!1,remotedebugDesc:"开启远程调试后，你可以使用adb connect来连接调试设备",adbkitPort:-1,adbKitWs:null,remoteAdbBtnType:"primary",remoteAdbText:"开启远程调试",adbkitMsg:"",isConnectedDebug:!1,installBtnLoading:!1,installBtnText:"安装APP",deviceId:this.$route.query.deviceId,agentIp:this.$route.query.agentIp,choosedFile:null,deviceInfo:[],canvas:null,minicapWebsocket:null,minitouchWebsocket:null,isMouseDown:!1,menu:{udid:this.deviceId,operation:"menu"},home:{udid:this.deviceId,operation:"g"},back:{udid:this.deviceId,operation:"b"},power:{udid:this.deviceId,operation:"p"},touchDown:{udid:this.deviceId,operation:"d",index:0,pX:.5,pY:.5,pressure:50},touchMove:{udid:this.deviceId,operation:"m",index:0,pX:.5,pY:.5,pressure:50},touchUp:{udid:this.deviceId,operation:"u",index:0}}},methods:{changeSimpleMode:function(){this.simpleMode?(this.leftSpan=8,window.resizeTo(1920,1080)):(this.leftSpan=24,window.resizeTo(550,950),window.moveTo(window.screen.width,0)),this.simpleMode=!this.simpleMode},dumpCurrentPage:function(){this.$router.push({name:"AndroidDump",query:{deviceId:this.deviceId,agentIp:this.agentIp}})},startRemoteAdb:function(){var n=this;this.isConnectedDebug?null!=this.adbKitWs&&(this.adbKitWs.send("关闭webkit socket"),this.isConnectedDebug=!1):(this.adbKitWs=new WebSocket("ws://"+this.agentIp+":10002/remotedebug/"+this.deviceId),this.adbKitWs.onopen=function(){this.adbkitMsg="已连接adbkit websocket"},this.adbKitWs.onmessage=function(e){e.data.startsWith("开启手机远程调试成功")&&(n.adbkitPort=e.data.split(":")[1].split(",")[0],n.remoteAdbText="关闭远程调试",n.isConnectedDebug=!0,n.remoteAdbBtnType="danger",n.remotedebugDesc="你现在可以使用adb connect "+n.agentIp+":"+n.adbkitPort+"来远程调试设备"),n.adbkitMsg=e.data},this.adbKitWs.onclose=function(){n.adbkitMsg="adbkit websocket closed",n.remoteAdbText="开启远程调试",n.remoteAdbBtnType="primary",n.remotedebugDesc="开启远程调试后，你可以使用adb connect来连接调试设备"})},onChange:function(n){this.choosedFile=n},installApp:function(){var n=this;if(null!=this.choosedFile){var e=this.choosedFile.raw;if(e.name.endsWith(".apk")){this.installBtnText="安装中...",this.installBtnLoading=!0;var t=new WebSocket("ws://"+this.agentIp+":10002/upload/"+this.deviceId);t.onopen=function(){for(var n=0,i=0,o=Math.ceil(e.size/209715200),s=1;s<=o;s++){i=s===o?e.size:n+209715200;var a=new FileReader;console.log(n+"->"+i);var c=e.slice(n,i);a.readAsArrayBuffer(c),a.onload=function(n){console.log(n);var e=n.target.result;t.send(e)},n+=209715200}},t.onmessage=function(e){n.$notify.info(e.data)},t.onclose=function(){n.installBtnText="安装APP",n.installBtnLoading=!1,n.$notify.info("apk socket close")}}else this.$notify.error("请选择apk文件")}else this.$notify.error("请选择一个app")},goMenu:function(){this.minitouchWebsocket.send(o()(this.menu))},goHome:function(){this.minitouchWebsocket.send(o()(this.home))},goBack:function(){this.minitouchWebsocket.send(o()(this.back))},goPower:function(){this.minitouchWebsocket.send(o()(this.power))},getX:function(n){return n.offsetX/this.canvas.offsetWidth},getY:function(n){return n.offsetY/this.canvas.offsetHeight}},created:function(){var n=this;Object(c.b)(this.deviceId).then(function(e){n.deviceInfo.push(e.data)})},mounted:function(){var n=this,e=this.$loading({fullscreen:!0}),t=document.getElementById("canvas1"),i=document.getElementById("minitouch_msg"),s=document.getElementById("minicap_msg"),a=document.getElementById("uiautomator2_msg");this.canvas=t;var c=t.getContext("2d"),r=new WebSocket("ws://"+this.agentIp+":10002/uiautomator2server/"+this.deviceId);r.onmessage=function(n){if(-1!=n.data.indexOf("开启uiautomator2 server成功")){var e=n.data,t=e.substring(e.indexOf("端口为:")+4,e.indexOf(",sessionid"));localStorage.setItem("uiautomator_server_port",t)}a.innerHTML=n.data},r.onclose=function(){a.innerHTML="uiautomator2 websocket closed"};var d=new WebSocket("ws://"+this.agentIp+":10002/minicap/"+this.deviceId+"/"+this.name);this.minicapWebsocket=d,d.binaryType="blob",d.onclose=function(){s.innerHTML="minicap websocket closed",e.close()},d.onerror=function(){s.innerHTML="minicap websocket error"},d.onmessage=function(i){if("string"==typeof i.data)s.innerHTML=i.data,i.data.startsWith("minicap图片数据处理中")?e.close():-1!==i.data.indexOf("稍后重试")&&(e.close(),n.$alert(i.data,"warning",{showConfirmButton:!1,showClose:!1}));else{var o=new Blob([i.data],{type:"image/jpeg"}),a=window.URL||window.webkitURL,r=new Image;r.onload=function(){t.width=r.width,t.height=r.height,c.drawImage(r,0,0),r.onload=null,r.src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==",r=null,d=null,o=null};var d=a.createObjectURL(o);r.src=d}},d.onopen=function(){s.innerHTML="minicap onopen"};var l=new WebSocket("ws://"+this.agentIp+":10002/minitouch/"+this.deviceId);this.minitouchWebsocket=l,l.onmessage=function(n){i.innerHTML=n.data},l.onclose=function(){i.innerHTML="minitouch websocket closed"},l.onerror=function(){i.innerHTML="minitouch websocket error"},l.onopen=function(){i.innerHTML="minitouch onopen"},this.isMouseDown=!1;var u=this.touchDown;t.onmouseleave=function(){n.isMouseDown&&(l.send(o()(n.touchUp)),n.isMouseDown=!1)},t.onmousedown=function(e){n.isMouseDown=!0,u.pX=n.getX(e),u.pY=n.getY(e),l.send(o()(u))},t.onmouseup=function(){n.isMouseDown=!1,l.send(o()(n.touchUp))};var p=this.touchMove;t.onmousemove=function(e){n.isMouseDown&&(p.pX=n.getX(e),p.pY=n.getY(e),l.send(o()(p)))}}},l={render:function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("div",{staticStyle:{padding:"20px"}},[t("el-card",[t("el-row",[t("el-col",{attrs:{span:n.leftSpan}},[t("el-row",[t("el-row",[t("div",{attrs:{align:"center"}},[t("canvas",{attrs:{id:"canvas1"}})])]),n._v(" "),t("el-row",[t("div",{staticStyle:{"margin-top":"10px"},attrs:{align:"center"}},[t("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(e){n.goMenu()}}},[n._v("Menu")]),n._v(" "),t("el-button",{attrs:{type:"danger",size:"mini"},on:{click:function(e){n.goHome()}}},[n._v("Home")]),n._v(" "),t("el-button",{attrs:{type:"success",size:"mini"},on:{click:function(e){n.goBack()}}},[n._v("Back")]),n._v(" "),t("el-button",{attrs:{type:"danger",size:"mini"},on:{click:function(e){n.goPower()}}},[n._v("Power")]),n._v(" "),t("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-search"},on:{click:function(e){n.dumpCurrentPage()}}}),n._v(" "),t("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(e){n.changeSimpleMode()}}},[n._v("精简")])],1)])],1)],1),n._v(" "),n.simpleMode?n._e():t("el-col",{attrs:{span:16}},[t("el-row",[t("el-card",[t("el-table",{staticStyle:{width:"100%"},attrs:{data:n.deviceInfo,border:""}},[t("el-table-column",{attrs:{prop:"deviceId",label:"序列号",align:"center"}}),n._v(" "),t("el-table-column",{attrs:{prop:"deviceName",label:"设备名称",align:"center"}}),n._v(" "),t("el-table-column",{attrs:{prop:"memSize",label:"内存大小",align:"center"}}),n._v(" "),t("el-table-column",{attrs:{prop:"resolution",label:"分辨率",align:"center"}}),n._v(" "),t("el-table-column",{attrs:{prop:"systemVersion",label:"安卓版本",align:"center"}}),n._v(" "),t("el-table-column",{attrs:{prop:"phoneIp",label:"手机ip",align:"center"}}),n._v(" "),t("el-table-column",{attrs:{prop:"agentIp",label:"主机ip",align:"center"}})],1)],1)],1),n._v(" "),t("el-row",{staticStyle:{"margin-top":"20px"}},[t("el-card",[t("div",{staticStyle:{"margin-bottom":"10px"}},[t("el-tag",{attrs:{type:"success"}},[n._v("minitouch")]),t("span",{attrs:{id:"minitouch_msg"}})],1),n._v(" "),t("div",{staticStyle:{"margin-bottom":"10px"}},[t("el-tag",{attrs:{type:"warning"}},[n._v("minicap")]),t("span",{attrs:{id:"minicap_msg"}})],1),n._v(" "),t("div",{staticStyle:{"margin-bottom":"10px"}},[t("el-tag",{attrs:{type:"danger"}},[n._v("uiautomator2 server")]),t("span",{attrs:{id:"uiautomator2_msg"}})],1),n._v(" "),t("div",[t("el-tag",[n._v("adbkit")]),t("span",[n._v(n._s(n.adbkitMsg))])],1)])],1),n._v(" "),t("el-row",{staticStyle:{"margin-top":"20px"}},[t("el-card",[t("el-upload",{attrs:{drag:"",action:"/","on-change":n.onChange,multiple:!1,"auto-upload":!1}},[t("i",{staticClass:"el-icon-upload"}),n._v(" "),t("div",[n._v("将apk拖到此处，或"),t("em",[n._v("点击选择apk")])])]),n._v(" "),t("el-button",{staticStyle:{"margin-top":"15px"},attrs:{type:"primary",loading:n.installBtnLoading},on:{click:function(e){n.installApp()}}},[n._v(n._s(n.installBtnText))])],1)],1),n._v(" "),t("el-row",{staticStyle:{"margin-top":"20px"}},[t("el-card",[t("el-button",{attrs:{type:n.remoteAdbBtnType},on:{click:n.startRemoteAdb}},[n._v(n._s(n.remoteAdbText))]),n._v(" "),t("el-tag",{staticStyle:{"margin-left":"15px"},attrs:{type:"success"}},[n._v(n._s(n.remotedebugDesc))])],1)],1)],1)],1)],1)],1)},staticRenderFns:[]};var u=t("VU/8")(d,l,!1,function(n){t("YEqB")},"data-v-0153a6db",null);e.default=u.exports},O8As:function(n,e,t){(n.exports=t("FZ+f")(!1)).push([n.i,"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n",""])},YEqB:function(n,e,t){var i=t("O8As");"string"==typeof i&&(i=[[n.i,i,""]]),i.locals&&(n.exports=i.locals);t("rjj0")("7704f716",i,!0)}});