(function()
{
    return function()
    {
        if (!this._is_form)
            return;
        
        var obj = null;
        
        this.on_create = function()
        {
            this.set_name("manager");
            this.set_titletext("manager");
            if (Form == this.constructor)
            {
                this._setFormPosition(1550,850);
            }
            
            // Object(Dataset, ExcelExportObject) Initialize
            obj = new Dataset("Dataset00", this);
            obj._setContents("<ColumnInfo><Column id=\"memberId\" type=\"STRING\" size=\"100\"/><Column id=\"memberName\" type=\"STRING\" size=\"100\"/><Column id=\"memberAddress\" type=\"STRING\" size=\"100\"/><Column id=\"memberTel\" type=\"STRING\" size=\"100\"/><Column id=\"memberEmail\" type=\"STRING\" size=\"100\"/></ColumnInfo>");
            this.addChild(obj.name, obj);


            obj = new Dataset("Dataset01", this);
            obj._setContents("<ColumnInfo><Column id=\"code\" type=\"STRING\" size=\"256\"/><Column id=\"data\" type=\"STRING\" size=\"256\"/></ColumnInfo><Rows><Row><Col id=\"code\">0</Col><Col id=\"data\">아이디</Col></Row><Row><Col id=\"code\">1</Col><Col id=\"data\">이름</Col></Row><Row><Col id=\"code\">2</Col><Col id=\"data\">주소</Col></Row></Rows>");
            this.addChild(obj.name, obj);


            obj = new Dataset("manager", this);
            obj._setContents("<ColumnInfo><Column id=\"managerId\" type=\"STRING\" size=\"256\"/><Column id=\"managerDept\" type=\"STRING\" size=\"256\"/><Column id=\"managerHireDate\" type=\"STRING\" size=\"256\"/><Column id=\"managerName\" type=\"STRING\" size=\"256\"/><Column id=\"managerTel\" type=\"STRING\" size=\"256\"/></ColumnInfo>");
            this.addChild(obj.name, obj);


            obj = new Dataset("dept", this);
            obj._setContents("<ColumnInfo><Column id=\"code\" type=\"STRING\" size=\"256\"/><Column id=\"data\" type=\"STRING\" size=\"256\"/></ColumnInfo><Rows><Row><Col id=\"code\">0</Col><Col id=\"data\">회원관리</Col></Row><Row><Col id=\"code\">1</Col><Col id=\"data\">커뮤니티</Col></Row><Row><Col id=\"code\">2</Col><Col id=\"data\">고객문의</Col></Row><Row><Col id=\"code\">3</Col><Col id=\"data\">반려동물</Col></Row><Row><Col id=\"code\">4</Col><Col id=\"data\">스토어</Col></Row></Rows>");
            this.addChild(obj.name, obj);


            obj = new Dataset("manager00", this);
            obj._setContents("<ColumnInfo><Column id=\"managerId\" type=\"STRING\" size=\"256\"/><Column id=\"managerDept\" type=\"STRING\" size=\"256\"/><Column id=\"managerHireDate\" type=\"STRING\" size=\"256\"/><Column id=\"managerName\" type=\"STRING\" size=\"256\"/><Column id=\"managerTel\" type=\"STRING\" size=\"256\"/></ColumnInfo>");
            this.addChild(obj.name, obj);
            
            // UI Components Initialize
            obj = new Grid("Grid03","821","130","699","700",null,null,null,null,null,null,this);
            obj.set_taborder("7");
            obj.set_border("1px solid black");
            obj._setContents("");
            this.addChild(obj.name, obj);

            obj = new Grid("Grid01","10","40","790","790",null,null,null,null,null,null,this);
            obj.set_taborder("6");
            obj.set_border("1px solid black");
            obj._setContents("");
            this.addChild(obj.name, obj);

            obj = new Grid("Grid00","30","60","750","740",null,null,null,null,null,null,this);
            obj.set_taborder("0");
            obj.set_binddataset("manager");
            obj.set_color("transparent");
            obj.set_border("1px solid black");
            obj.set_cursor("pointer");
            obj._setContents("<Formats><Format id=\"default\"><Columns><Column size=\"125\"/><Column size=\"134\"/><Column size=\"165\"/><Column size=\"130\"/><Column size=\"192\"/></Columns><Rows><Row size=\"37\" band=\"head\"/><Row size=\"42\"/></Rows><Band id=\"head\"><Cell text=\"아이디\" font=\"normal 700 24px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"1\" text=\"부서명\" font=\"normal 700 24px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"2\" text=\"입사일\" font=\"normal 700 24px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"3\" text=\"이름\" font=\"normal 700 24px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"4\" text=\"연락처\" font=\"normal 700 24px/normal &quot;나눔스퀘어 Bold&quot;\"/></Band><Band id=\"body\"><Cell text=\"bind:managerId\" font=\"25px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"1\" text=\"bind:managerDept\" font=\"25px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"2\" text=\"bind:managerHireDate\" font=\"25px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"3\" text=\"bind:managerName\" font=\"25px/normal &quot;나눔스퀘어 Bold&quot;\"/><Cell col=\"4\" text=\"bind:managerTel\" font=\"25px/normal &quot;나눔스퀘어 Bold&quot;\"/></Band></Format></Formats>");
            this.addChild(obj.name, obj);

            obj = new Static("Static00","867","220","90","40",null,null,null,null,null,null,this);
            obj.set_taborder("1");
            obj.set_text("아이디");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            this.addChild(obj.name, obj);

            obj = new Static("Static02","870","350","90","50",null,null,null,null,null,null,this);
            obj.set_taborder("2");
            obj.set_text("부서명");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            this.addChild(obj.name, obj);

            obj = new Static("Static03","870","490","90","44",null,null,null,null,null,null,this);
            obj.set_taborder("3");
            obj.set_text("입사일");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            this.addChild(obj.name, obj);

            obj = new Button("managerDelete","1360","740","120","50",null,null,null,null,null,null,this);
            obj.set_taborder("5");
            obj.set_text("삭제");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            obj.set_border("1px solid black");
            this.addChild(obj.name, obj);

            obj = new Edit("managerId","1000","216","470","47",null,null,null,null,null,null,this);
            obj.set_taborder("4");
            obj.set_border("1px solid darkgray");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            this.addChild(obj.name, obj);

            obj = new Button("Button00","10","10","117","30",null,null,null,null,null,null,this);
            obj.set_taborder("8");
            obj.set_text("회원관리");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            this.addChild(obj.name, obj);

            obj = new Button("Button00_00","140","10","117","30",null,null,null,null,null,null,this);
            obj.set_taborder("9");
            obj.set_text("직원관리");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            this.addChild(obj.name, obj);

            obj = new Button("managerSelect","825","40","115","60",null,null,null,null,null,null,this);
            obj.set_taborder("10");
            obj.set_text("조회");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            obj.set_border("1px solid black");
            obj.set_visible("true");
            obj.set_enableevent("true");
            this.addChild(obj.name, obj);

            obj = new Button("managerInsert","1190","740","120","50",null,null,null,null,null,null,this);
            obj.set_taborder("11");
            obj.set_text("등록");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            obj.set_border("1px solid black");
            this.addChild(obj.name, obj);

            obj = new Combo("managerDept","999","353","471","53",null,null,null,null,null,null,this);
            obj.set_taborder("12");
            obj.set_innerdataset("dept");
            obj.set_codecolumn("code");
            obj.set_datacolumn("data");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            obj.set_text("Combo00");
            this.addChild(obj.name, obj);

            obj = new Calendar("managerHireDate","1000","486","472","52",null,null,null,null,null,null,this);
            obj.set_taborder("13");
            obj.set_font("25px/normal \"나눔스퀘어 Bold\"");
            this.addChild(obj.name, obj);

            // Layout Functions
            //-- Default Layout : this
            obj = new Layout("default","Desktop_screen",1550,850,this,
            	//-- Layout function
            	function(p)
            	{
                var rootobj = p;
                p = rootobj;
                p.set_titletext("manager");

                p.Grid03.set_taborder("7");
                p.Grid03.set_border("1px solid black");
                p.Grid03.move("821","130","699","700",null,null);

                p.Grid01.set_taborder("6");
                p.Grid01.set_border("1px solid black");
                p.Grid01.move("10","40","790","790",null,null);

                p.Grid00.set_taborder("0");
                p.Grid00.set_binddataset("manager");
                p.Grid00.set_color("transparent");
                p.Grid00.set_border("1px solid black");
                p.Grid00.set_cursor("pointer");
                p.Grid00.move("30","60","750","740",null,null);

                p.Static00.set_taborder("1");
                p.Static00.set_text("아이디");
                p.Static00.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.Static00.move("867","220","90","40",null,null);

                p.Static02.set_taborder("2");
                p.Static02.set_text("부서명");
                p.Static02.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.Static02.move("870","350","90","50",null,null);

                p.Static03.set_taborder("3");
                p.Static03.set_text("입사일");
                p.Static03.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.Static03.move("870","490","90","44",null,null);

                p.managerDelete.set_taborder("5");
                p.managerDelete.set_text("삭제");
                p.managerDelete.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.managerDelete.set_border("1px solid black");
                p.managerDelete.move("1360","740","120","50",null,null);

                p.managerId.set_taborder("4");
                p.managerId.set_border("1px solid darkgray");
                p.managerId.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.managerId.move("1000","216","470","47",null,null);

                p.Button00.set_taborder("8");
                p.Button00.set_text("회원관리");
                p.Button00.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.Button00.move("10","10","117","30",null,null);

                p.Button00_00.set_taborder("9");
                p.Button00_00.set_text("직원관리");
                p.Button00_00.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.Button00_00.move("140","10","117","30",null,null);

                p.managerSelect.set_taborder("10");
                p.managerSelect.set_text("조회");
                p.managerSelect.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.managerSelect.set_border("1px solid black");
                p.managerSelect.set_visible("true");
                p.managerSelect.set_enableevent("true");
                p.managerSelect.move("825","40","115","60",null,null);

                p.managerInsert.set_taborder("11");
                p.managerInsert.set_text("등록");
                p.managerInsert.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.managerInsert.set_border("1px solid black");
                p.managerInsert.move("1190","740","120","50",null,null);

                p.managerDept.set_taborder("12");
                p.managerDept.set_innerdataset("dept");
                p.managerDept.set_codecolumn("code");
                p.managerDept.set_datacolumn("data");
                p.managerDept.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.managerDept.set_text("Combo00");
                p.managerDept.move("999","353","471","53",null,null);

                p.managerHireDate.set_taborder("13");
                p.managerHireDate.set_font("25px/normal \"나눔스퀘어 Bold\"");
                p.managerHireDate.move("1000","486","472","52",null,null);
            	}
            );
            this.addLayout(obj.name, obj);

            //-- Normal Layout : this
            obj = new Layout("Layout0","",1280,720,this,
            	//-- Layout function
            	function(p)
            	{
                var rootobj = p;
                p = rootobj;

            	}
            );
            obj.set_mobileorientation("landscape");
            this.addLayout(obj.name, obj);
            
            // BindItem Information
            obj = new BindItem("item0","managerId","value","manager","managerId");
            this.addChild(obj.name, obj);
            obj.bind();

            obj = new BindItem("item2","managerHireDate","value","manager","managerHireDate");
            this.addChild(obj.name, obj);
            obj.bind();

            obj = new BindItem("item3","managerDept","text","manager","managerDept");
            this.addChild(obj.name, obj);
            obj.bind();
        };
        
        this.loadPreloadList = function()
        {

        };
        
        // User Script
        this.registerScript("Form_Work_copy0.xfdl", function() {


        this.Button00_onclick = function(obj,e)
        {
        	this.go("FrameBase::Form_Work.xfdl")
        };

        this.Button00_00_onclick = function(obj,e)
        {
        	this.go("FrameBase::Form_Work_copy0.xfdl")
        };


        //매니저 부서 등록하기
        this.managerInsertBtn_onclick = function(obj,e){
        	alert("수정/등록 되었습니다");
        	var Id = encodeURI(this.managerId.value,"UTF-8");
        	var Dept = this.managerDept.text;
        	var managerHire = encodeURI(this.managerHireDate.value,"UTF-8");

         	this.transaction(
         			"urlTest03",
        			"strURL::managerInsert.do?id="+Id+"&dept="+Dept+"&hireDate="+managerHire,
         			"",
         			"manager=ar",
         			"",
         			"fn_callback"
         		);

         	this.fn_callback = function(svcID, errCD, errMSG){
         		let ret = (errMSG=="FAILED" || svcID != "urlTest03") ? "error" : "success";
         		if(ret=="error")
         			this.alert("[폼이름_fn_callback] "+ret + " : " + svcID + ", " + errCD + ", " + errMSG);
        	};

        };



        // manager 삭제
        this.deleteBtn_onclick = function(obj,e)
        {
        	alert("삭제 되었습니다");
        	var Id = encodeURI(this.managerId.value,"UTF-8");

         	this.transaction(
         			"urlTest03",
        			"strURL::managerdelete.do?id="+Id,
         			"",
         			"manager=ar",
         			"",
         			"fn_callback"
         		);

         	this.fn_callback = function(svcID, errCD, errMSG){
         		let ret = (errMSG=="FAILED" || svcID != "urlTest03") ? "error" : "success";
         		if(ret=="error")
         			this.alert("[폼이름_fn_callback] "+ret + " : " + svcID + ", " + errCD + ", " + errMSG);
        	};
        };

        // 매니저 조회버튼
        this.managerSelectBtn_onclick = function(obj,e)
        {

        	this.transaction(
         			"urlTest03",
        			"strURL::managerselect.do",
         			"",
         			"manager=ar",
         			"",
         			"fn_callback"
         		);

        	this.fn_callback = function(svcID, errCD, errMSG){
        		let ret = (errMSG=="FAILED" || svcID != "urlTest03") ? "error" : "success";
        		if(ret=="error")
        			this.alert("[폼이름_fn_callback] "+ret + " : " + svcID + ", " + errCD + ", " + errMSG);
        	}
        	this.managerSelect.set_enable(false);

        };

        });
        
        // Regist UI Components Event
        this.on_initEvent = function()
        {
            this.managerDelete.addEventHandler("onclick",this.deleteBtn_onclick,this);
            this.managerId.addEventHandler("onchanged",this.Edit00_onchanged,this);
            this.Button00.addEventHandler("onclick",this.Button00_onclick,this);
            this.Button00_00.addEventHandler("onclick",this.Button00_00_onclick,this);
            this.managerSelect.addEventHandler("onclick",this.managerSelectBtn_onclick,this);
            this.managerInsert.addEventHandler("onclick",this.managerInsertBtn_onclick,this);
        };

        this.loadIncludeScript("Form_Work_copy0.xfdl");
        this.loadPreloadList();
        
        // Remove Reference
        obj = null;
    };
}
)();
