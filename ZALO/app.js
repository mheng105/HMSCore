var app = require("express")();
var server = require("http").createServer(app);
var io = require("socket.io")(server);
var manguser=["user1","user2","user3","user4"];
var uservao=[];
var cauhoi=[];
var listview=[];
var pos=2;
var dem=2;
var solan=0;
console.log('dag chay');
//Client Connect to server
io.on('connection', (socket)=>{
    console.log(socket.un+'-Co nguoi vao ne:'+socket.id);

    socket.on("choi",(data,maphong)=>{
        socket.un=data;
        console.log("gui yeu cau choi"+data);
        socket.join(maphong);
        console.log(socket.un+'-Co nguoi vao ne:'+socket.id);
        console.log(socket.un+'-vao phong:'+maphong);
                    dem=2;
                    pos=2;
                    cauhoi=[];
                    var so1=Math.floor(Math.random() * 1000) + 1;
                    var so2=Math.floor(Math.random() * 1000) + 1;
                    var listOperator=["+","-","*"];
                    var ketqua=0;
                    var operator=listOperator[Math.floor(Math.random()*3)];
                    switch(operator){
                        case "+":
                            ketqua=so1+so2;
                            break;
                        case "-":
                            ketqua=so1-so2;
                            break;
                        case "*":
                            ketqua=so1*so2;
                            break;
                        default: break;
                    }

                    cauhoi.push(so1);
                    cauhoi.push(operator);
                    cauhoi.push(so2);
                    cauhoi.push(1);
                    var dapan=[(ketqua).toString(),(ketqua+Math.floor(Math.random() * 10)+1).toString(),(ketqua+Math.floor(Math.random() * 10)-11).toString(),(ketqua+Math.floor(Math.random() * 10)+11).toString()]
                    var mixdapan=dapan.sort(() => Math.random() - 0.5);
                    cauhoi.push(...mixdapan);
                io.to(maphong).emit("cau-hoi",{question:cauhoi});
    })
    //Server xu ly yeu cau moi choi
    socket.on("gui-yeu-cau-choi",(idReceive,nameReceive,name)=>{
        io.to(idReceive).emit("loi-moi",{loimoi:socket.id+","+name});
    })
    
    //Insert user enter waiting room to listUser
    socket.on("xemphong",(data)=>{
        console.log(data+" enter");
        socket.un=data;
        var object=socket.id+","+data;
        listview.push(object);
        io.emit("hiendanhsach",{danhsach:listview});
    })

//Response request traloi of client
    socket.on("traloi",(id,user_name,namex,accept)=>{
        console.log(user_name+namex);
        if(accept==false){
            io.to(id).emit("Phan-hoi",{nguoichoi:user_name,phanhoi:"false"});
        }
        else
        {
            io.to(id).emit("Phan-hoi",{nguoichoi:user_name,phanhoi:"true"});
            
            listview=listview.filter(function(element){
                return element.split(",")[1]!=user_name;
            });
            for(var i=0;i<listview.length;i++){
                console.log(i+":"+listview[i]);
            }
            listview=listview.filter(function(element){
                return element.split(",")[1]!=namex;
            });
            console.log(user_name+namex);
            for(var i=0;i<listview.length;i++){
                console.log(i+":"+listview[i]);
            }
            io.emit("hiendanhsach",{danhsach:listview});
        }
    });
    socket.on("client-gui", (data)=>{
        var  ketqua=false;
        if(manguser.indexOf(data)>-1)
        {
            if(uservao.indexOf(data)>-1)
                ketqua=false;
            else{
                uservao.push(data);
                ketqua=true;
                socket.un=data;
            }
        }
        else
            ketqua=false;
        socket.emit("ketqua-dangnhap",{noidung:ketqua});
        
    })
    
        socket.on("dap-an",function(check,socau,idP){
            var  diemcong;
            if(check=="true")
            {
                console.log(socket.un+" dung!");
                diemcong=[socket.un,pos.toString(),"true"];
                pos--;
            }
            else
            {
                diemcong=[socket.un,pos.toString(),"false"];
                console.log(socket.un+" sai!");
            }
            dem--;
            io.to(idP).emit("cong-diem",{congdiem:diemcong});
            if(dem==0){
                if(socau==5)
                    io.to(idP).emit("ket-thuc",{ketthuc:socau});
                else{
                    console.log(pos+" "+dem);
                    dem=2;
                    pos=2;
                    cauhoi=[];
                    var so1=Math.floor(Math.random() * 1000) + 1;
                    var so2=Math.floor(Math.random() * 1000) + 1;
                    //var caudo=so1+"+"+so2+"=?"+(so1+so2);
                    var listOperator=["+","-","*"];
                    var ketqua=0;
                        var operator=listOperator[Math.floor(Math.random()*3)];
                        switch(operator){
                            case "+":
                                ketqua=so1+so2;
                                break;
                            case "-":
                                ketqua=so1-so2;
                                break;
                            case "*":
                                ketqua=so1*so2;
                                break;
                            default: break;
                        }
                        
                        cauhoi.push(so1);
                        cauhoi.push(operator);
                        cauhoi.push(so2);
                        cauhoi.push(socau+1);
                        var dapan=[(ketqua).toString(),(ketqua+Math.floor(Math.random() * 10)+1).toString(),(ketqua+Math.floor(Math.random() * 10)-11).toString(),(ketqua+Math.floor(Math.random() * 10)+11).toString()]
                        var mixdapan=dapan.sort(() => Math.random() - 0.5);
                        cauhoi.push(...mixdapan);
                    io.to(idP).emit("cau-hoi",{question:cauhoi});
                }
            }
        });
});
server.listen(3000);