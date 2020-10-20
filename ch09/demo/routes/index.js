var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

//请求页面获取数据
router.get('/getdata', function(req, res) {
  res.render('getdata');
});

router.get('/show-data', function(req, res) {
  var data = [
	{"type":"UNA","times":32},
	{"type":"Autos & Vehicles","times":77},
	{"type":"Comedy","times":414},
	{"type":"Education","times":65},
	{"type":"Entertainment","times":908},
	{"type":"Film & Animation","times":260},
	{"type":"Howto & Style","times":137},
	{"type":"Music","times":862},
	{"type":"News & Politics","times":333},
	{"type":"Nonprofits & Activism","times":42},
	{"type":"People & Blogs","times":398},
	{"type":"Pets & Animals","times":94},
	{"type":"Science & Technology","times":80},
	{"type":"Sports","times":251},
	{"type":"Travel & Events","times":112}
	];
	res.json(data);
});

//折线图页面
router.get('/getdata2', function(req, res) {
  res.render('getdata2');
});

router.get('/show-data2', function(req, res) {
  var data = [
	{"age":"0-200","sum":0,"sum2":0,"sum3":0},
	{"age":"200-400","sum":9,"sum2":55,"sum3":123},
	{"age":"400-600","sum":47,"sum2":166,"sum3":388},
	{"age":"600-800","sum":134,"sum2":289,"sum3":555},
	{"age":"800-1000","sum":190,"sum2":522,"sum3":698},
	{"age":"1000-1200","sum":529,"sum2":699,"sum3":999}
	];
	res.json(data);
});

	//饼状图页面
	router.get('/getdata3', function(req, res) {
  res.render('getdata3');
});

router.get('/show-data3', function(req, res) {
   var data = [
	 	{"value":13206, "name":"UNA"},
      	{"value":2534, "name":"Autos & Vehicles"},
        {"value":318602, "name":"Comedy"},
        {"value":4724, "name":"Education"},
        {"value":737023, "name":"Entertainment"},
        {"value":101346, "name":"Film & Animation"},
      	{"value":125948, "name":"Howto & Style"},
        {"value":1222444, "name":"Music"},
        {"value":141840, "name":"News & Politics"},
        {"value":3660, "name":"Nonprofits & Activism"},
        {"value":110439, "name":"Pets & Animals"},
        {"value":220878, "name":"People & Blogs"},
        {"value":73580, "name":"Science & Technology"},
        {"value":86272, "name":"Sports"},
        {"value":51534, "name":"Travel & Events"}
	];
	res.json(data);
});

//
//折线图页面
router.get('/getdata4', function(req, res) {
  res.render('getdata4');
});

router.get('/show-data4', function(req, res) {
  var data = [
    //评论数 得分数 观看数 类型
    [845,512,1762368,'Autos & Vehicles',1990],
    [75004,30666,11007201,'Comedy',1990],
    [1994,585,1958000,'Education',1990],
    [39189,85508,65341925,'Film & Animation',1990],
    [122129,259683,18235463,'Entertainment',1990],
    [22030,29786,6101232,'Howto & Style',1990],  
    [83514,129200,16841569,'Music',1990],  
    [5116,6403,2674096,'News & Politics',1990],  
    [235,345,77313,'Nonprofits & Activism',1990],
    [33172,17602,5766247,'People & Blogs',1990],  
    [58850,24004,27721690,'Pets & Animals',1990], 
    [8788,6093,3234852,'Science & Technology',1990],
    [13898,14602,5360384,'Sports',1990], 
    [37247,19461,3010296,'Travel & Events',1990],
    [1536,1328,686450,'UNA',1990],                     
];
	res.json(data);
});

//热力图页面
	router.get('/getdata5', function(req, res) {
  res.render('getdata5');
});

router.get('/show-data5', function(req, res) {
   var data = [[[0,0,487],[0,1,1328],[0,2,648],
            [1,0,845],[1,1,414],[1,2,333],
            [2,0,30666],[2,1,6938],[2,2,29160],
            [3,0,816],[3,1,1289],[3,2,257],
            [4,0,259683],[4,1,14274],[4,2,22567],
            [5,0,5508],[5,1,4235],[5,2,572],
            [6,0,29786],[6,1,6605],[6,2,5281],
            [7,0,50036],[7,1,17731],[7,2,129200],
            [8,0,1646],[8,1,4164],[8,2,6403],
            [9,0,149],[9,1,345],[9,2,51],
            [10,0,17602],[10,1,881],[10,2,3983],
            [11,0,24004],[11,1,39418],[11,2,12034],
            [12,0,6093],[12,1,2598],[12,2,185],
            [13,0,14602],[13,1,1041],[13,2,232],
            [14,0,19461],[14,1,1852],[14,2,2993]],
            ["UNA", "Autos & Vehicles", "Comedy", "Education", "Entertainment", "Film & Animation", "Howto & Style","Music","News & Politics","Nonprofits & Activism","People & Blogs","Pets & Animals","Science & Technology","Sports","Travel & Events"],
            ["观看量第一名", "观看量第二名", "观看量第三名"]
            ];
	res.json(data);
});

//
//热力图页面
	router.get('/getdata6', function(req, res) {
  res.render('getdata6');
});

router.get('/show-data6', function(req, res) {
  
     var data = [{"name": "数据集结构"}, 
     			{"name": "视频ID"},
     			{"name": "上传者ID"},
     			{"name": "上传时间间隔"},
     			{"name": "视频类型"},
     			{"name": "视频长度"},
     			{"name": "观看数"},
     			{"name": "率"},
     			{"name": "评分"},
     			{"name": "评论数"},
     			{"name": "相关视频ID"}];

	res.json(data);
});

//雷达图页面
	router.get('/getdata7', function(req, res) {
  res.render('getdata7');
});

router.get('/show-data7', function(req, res) {
  
     var data = [
            {
                "value" : [935, 192, 211654, 3.5, 931, 769],
                "name" : "Comedy"
            },
             {
                "value" : [1068, 253, 29498, 3.34, 58, 71],
                "name" : "Education"
            },
             {
                "value" : [960, 231, 157593, 3.66, 717, 813],
                "name" : "Entertainment"
            }
        ];

	res.json(data);
});

//箱线图页面
	router.get('/getdata8', function(req, res) {
  res.render('getdata8');
});

router.get('/show-data8', function(req, res) {
  
     var data =  [4.96,2.33,2,5,5,2.5,0,4.75,0,4.7,0,0,3.58,0,0,3,0,4.33,4.78,4.6,4.52,4.5,5,5,5,4.75,4.37,5,5,4.8,4.2,1,1,5,4.38,3.5,3.4,3,3.67,3,2.6,3.67,4.43];

	res.json(data);
});

//树状图页面
	router.get('/getdata9', function(req, res) {
  res.render('getdata9');
});

router.get('/show-data9', function(req, res) {
  
     var data =  [1,2,3];

	res.json(data);
});

module.exports = router;
