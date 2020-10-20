function Ajax(dataType, bool){
			//1.定义空对象
			var ajax = new Object();

			//2.创建XMLHttpRequest请求对象
			ajax.xhr = new XMLHttpRequest();

			//3.设置返回数据类型JSON/HTML(字符串)
			ajax.type = dataType == undefined? 'HTML':dataType;

			//4.设置回调函数
			ajax.callback;

			//5.设置同步或异步(默认为异步)
			ajax.bool = bool ==undefined?true:bool;
			// console.log(ajax.bool);

			//将监听事件单独封装
			ajax.change = function(){
				if(ajax.xhr.readyState == 4 && ajax.xhr.status == 200){
					//要求返回数据是HTML
					if(ajax.type == 'HTML'){
						ajax.callback(ajax.xhr.responseText);
					}else if(ajax.type == 'JSON'){
						// console.log(ajax.xhr.responseText);
						var obj = JSON.parse(ajax.xhr.responseText);
						ajax.callback(obj);
					}
				}
			}

			//**********2.定义get请求***********
			ajax.get = function(url,callback){
				//2.1创建XMLHttpRequest对象
    		// 给全局的ajax.callback赋值函数
    		ajax.callback = callback;
    		//当状态值改变时
    		ajax.xhr.onreadystatechange = ajax.change;
    		//2.2配置发送信息
    		ajax.xhr.open('GET',url, ajax.bool);
    		//2.3发送信息
    		ajax.xhr.send();
			}

			//3.定义post请求
			ajax.post = function(url, data, callback){
				//给全局的ajax.callback赋值函数
				ajax.callback = callback;

				//当状态改变的时候
				ajax.xhr.onreadystatechange = ajax.change;

				//配置
				ajax.xhr.open('POST', url, ajax.bool);

				//配置头信息
				ajax.xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
				//发送
				ajax.xhr.send(data);
			}
			//4.让Ajax()函数返回一个能继续调用get post方法的对象
			return ajax;		
		}